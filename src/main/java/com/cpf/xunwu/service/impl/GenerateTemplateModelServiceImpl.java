package com.cpf.xunwu.service.impl;

import com.alibaba.excel.util.DateUtils;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.cpf.xunwu.base.ApiResponse;
import com.cpf.xunwu.base.BusinessException;
import com.cpf.xunwu.base.QiNiuPurRet;
import com.cpf.xunwu.constants.GenerateTemplateModelConstants;
import com.cpf.xunwu.dto.GenerateTemplateModelDto;
import com.cpf.xunwu.service.GenerateTemplateModelService;
import com.cpf.xunwu.service.QNService;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author caopengflying
 * @time 2020/1/31
 */
@Service
public class GenerateTemplateModelServiceImpl implements GenerateTemplateModelService {
    /**
     * 下划线正则表达式
     */
    private static Pattern pattern = Pattern.compile("([A-Za-z/d]+)(_)?");
    private static Pattern blankPattern = Pattern.compile("/s*|\t|\r|\n");

    @javax.annotation.Resource
    private QNService qnService;
    @javax.annotation.Resource
    private Gson gson;
    @Value("${qiniu.cdn.prefix}")
    private String cdnPrefix;

    @Override
    public ApiResponse generateTemplateModel(GenerateTemplateModelDto generateTemplateModelDto) {
        Map<String, String> resultMap = Maps.newHashMap();
        settingDefaultValue(generateTemplateModelDto);
        initTable(generateTemplateModelDto);
        String columnsContent = "";
        if (GenerateTemplateModelConstants.TemplateTypeEnum.MYBATIS_PLUS_BASIC.getCode().equals(generateTemplateModelDto.getTemplateType())) {
            Map<String, String> stringStringMap = generatePlusEntityInfo(generateTemplateModelDto);
            for (Map.Entry<String, String> stringStringEntry : stringStringMap.entrySet()) {
                if (!stringStringEntry.getKey().equals("xmlContent")) {
                    resultMap.put("facade/facade-service-api/src/main/java/com/mclon/facade/service/api/" + generateTemplateModelDto.getModuleName() + "/model/" + stringStringEntry.getKey() + ".java", stringStringEntry.getValue());
                } else {
                    columnsContent = stringStringEntry.getValue();

                }
            }
        } else {
            Map<String, String> stringStringMap = generateEntityInfo(generateTemplateModelDto);
            for (Map.Entry<String, String> stringStringEntry : stringStringMap.entrySet()) {
                if (!stringStringEntry.getKey().equals("columnsContent")) {
                    resultMap.put("facade/facade-service-api/src/main/java/com/mclon/facade/service/api/" + generateTemplateModelDto.getModuleName() + "/model/" + stringStringEntry.getKey() + ".java", stringStringEntry.getValue());
                } else {
                    columnsContent = stringStringEntry.getValue();
                }
            }
        }
        if (null == generateTemplateModelDto.getOnlyModelFlag() || !generateTemplateModelDto.getOnlyModelFlag()) {
            try {
                readTemplateAndReplace(resultMap, generateTemplateModelDto, columnsContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //mybatis xml
            String templateUrl = getSourceUrl(generateTemplateModelDto);
            //扩展类
            try {
                Resource resource = new ClassPathResource(templateUrl + "TemplateNameMapper.xml");
                String extTemplateNameOrigin = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
                String extTemplateNameContent = replaceContent(extTemplateNameOrigin, generateTemplateModelDto.getEntityName(), generateTemplateModelDto.getAuth(), generateTemplateModelDto.getDesc(), generateTemplateModelDto.getModuleName());
                if (StringUtils.isNotBlank(columnsContent)) {
                    extTemplateNameContent = extTemplateNameContent.replaceAll("__REPLACE_CONTENT", columnsContent);
                }
                resultMap.put("kernel/kernel-support-" + generateTemplateModelDto.getModuleName() + "/src/main/resources/META-INF/" + generateTemplateModelDto.getModuleName() + "/" + generateTemplateModelDto.getEntityName() + "Mapper.xml", extTemplateNameContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return writeFile(resultMap, generateTemplateModelDto);
    }

    /**
     * 设置默认值
     *
     * @param generateTemplateModelDto
     */
    private void settingDefaultValue(GenerateTemplateModelDto generateTemplateModelDto) {
        if (StringUtils.isEmpty(generateTemplateModelDto.getPort())) {
            generateTemplateModelDto.setPort("3306");
        }
        if (StringUtils.isEmpty(generateTemplateModelDto.getKeyId())) {
            generateTemplateModelDto.setKeyId(underline2Camel(generateTemplateModelDto.getTableName()) + "Id");
        }
        if (StringUtils.isEmpty(generateTemplateModelDto.getEntityName())) {
            generateTemplateModelDto.setEntityName(underline2Camel(generateTemplateModelDto.getTableName()).substring(0, 1).toUpperCase() + underline2Camel(generateTemplateModelDto.getTableName()).substring(1));
        }
        generateTemplateModelDto.setDatabase(generateTemplateModelDto.getProjectName() + "_" + generateTemplateModelDto.getDatabase());
    }

    /**
     * 读取模板，并替换文件
     *
     * @param resultMap
     * @param generateTemplateModelDto
     * @param columnsContent
     */
    public void readTemplateAndReplace(Map<String, String> resultMap, GenerateTemplateModelDto generateTemplateModelDto, String columnsContent) throws IOException {
        String templateUrl = getSourceUrl(generateTemplateModelDto);
        //扩展类
//        static/template.tkmapper/basic/ExtTemplateName.java
        Resource resource = new ClassPathResource(templateUrl + "ExtTemplateName.java");
        String extTemplateNameOrigin = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
        String extTemplateNameContent = replaceContent(extTemplateNameOrigin, generateTemplateModelDto.getEntityName(), generateTemplateModelDto.getAuth(), generateTemplateModelDto.getDesc(), generateTemplateModelDto.getModuleName());
        resultMap.put("facade/facade-service-api/src/main/java/com/mclon/facade/service/api/" + generateTemplateModelDto.getModuleName() + "/extmodel/Ext" + generateTemplateModelDto.getEntityName() + ".java", extTemplateNameContent);
        //BizService
        resource = new ClassPathResource(templateUrl + "TemplateNameBizService.java");
        extTemplateNameOrigin = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
        extTemplateNameContent = replaceContent(extTemplateNameOrigin, generateTemplateModelDto.getEntityName(), generateTemplateModelDto.getAuth(), generateTemplateModelDto.getDesc(), generateTemplateModelDto.getModuleName());
        if (Lists.newArrayList(GenerateTemplateModelConstants.TemplateTypeEnum.TKMAPPER_FORM.getCode(), GenerateTemplateModelConstants.TemplateTypeEnum.TKMAPPER_BASIC.getCode()).contains(generateTemplateModelDto.getTemplateType())) {
            resultMap.put("bomc/bomc-support-" + generateTemplateModelDto.getModuleName() + "/src/main/java/com/mclon/bomc/support/" + generateTemplateModelDto.getModuleName() + "/service/" + generateTemplateModelDto.getEntityName() + "BizService.java", extTemplateNameContent);
        } else {
            resultMap.put("bomc/bomc-support-platform/src/main/java/com/mclon/bomc/support/" + generateTemplateModelDto.getModuleName() + "/service/" + generateTemplateModelDto.getEntityName() + "BizService.java", extTemplateNameContent);
        }
        //BizServiceImpl
        resource = new ClassPathResource(templateUrl + "TemplateNameBizServiceImpl.java");
        extTemplateNameOrigin = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
        extTemplateNameContent = replaceContent(extTemplateNameOrigin, generateTemplateModelDto.getEntityName(), generateTemplateModelDto.getAuth(), generateTemplateModelDto.getDesc(), generateTemplateModelDto.getModuleName());
        if (Lists.newArrayList(GenerateTemplateModelConstants.TemplateTypeEnum.TKMAPPER_FORM.getCode(), GenerateTemplateModelConstants.TemplateTypeEnum.TKMAPPER_BASIC.getCode()).contains(generateTemplateModelDto.getTemplateType())) {
            resultMap.put("bomc/bomc-support-" + generateTemplateModelDto.getModuleName() + "/src/main/java/com/mclon/bomc/support/" + generateTemplateModelDto.getModuleName() + "/service/implement/" + generateTemplateModelDto.getEntityName() + "BizServiceImpl.java", extTemplateNameContent);
        } else {
            resultMap.put("bomc/bomc-support-platform/src/main/java/com/mclon/bomc/support/" + generateTemplateModelDto.getModuleName() + "/service/implement/" + generateTemplateModelDto.getEntityName() + "BizServiceImpl.java", extTemplateNameContent);
        }
        //Constants
        resource = new ClassPathResource(templateUrl + "TemplateNameConstants.java");
        extTemplateNameOrigin = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
        extTemplateNameContent = replaceContent(extTemplateNameOrigin, generateTemplateModelDto.getEntityName(), generateTemplateModelDto.getAuth(), generateTemplateModelDto.getDesc(), generateTemplateModelDto.getModuleName());
        resultMap.put("facade/facade-service-api/src/main/java/com/mclon/facade/service/api/" + generateTemplateModelDto.getModuleName() + "/constants/" + generateTemplateModelDto.getEntityName() + "Constants.java", extTemplateNameContent);

        //Controller
        resource = new ClassPathResource(templateUrl + "TemplateNameController.java");
        extTemplateNameOrigin = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
        extTemplateNameContent = replaceContent(extTemplateNameOrigin, generateTemplateModelDto.getEntityName(), generateTemplateModelDto.getAuth(), generateTemplateModelDto.getDesc(), generateTemplateModelDto.getModuleName());
        if (Lists.newArrayList(GenerateTemplateModelConstants.TemplateTypeEnum.TKMAPPER_FORM.getCode(), GenerateTemplateModelConstants.TemplateTypeEnum.TKMAPPER_BASIC.getCode()).contains(generateTemplateModelDto.getTemplateType())) {
            resultMap.put("bomc/bomc-support-" + generateTemplateModelDto.getModuleName() + "/src/main/java/com/mclon/bomc/support/" + generateTemplateModelDto.getModuleName() + "/web/" + generateTemplateModelDto.getEntityName() + "Controller.java", extTemplateNameContent);
        } else {
            resultMap.put("bomc/bomc-support-platform/src/main/java/com/mclon/bomc/support/" + generateTemplateModelDto.getModuleName() + "/web/" + generateTemplateModelDto.getEntityName() + "Controller.java", extTemplateNameContent);
        }

        //Enum
        resource = new ClassPathResource(templateUrl + "TemplateNameEnum.java");
        extTemplateNameOrigin = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
        extTemplateNameContent = replaceContent(extTemplateNameOrigin, generateTemplateModelDto.getEntityName(), generateTemplateModelDto.getAuth(), generateTemplateModelDto.getDesc(), generateTemplateModelDto.getModuleName());
        if (StringUtils.isNotBlank(columnsContent)) {
            if (Lists.newArrayList(GenerateTemplateModelConstants.TemplateTypeEnum.TKMAPPER_FORM.getCode(), GenerateTemplateModelConstants.TemplateTypeEnum.TKMAPPER_BASIC.getCode()).contains(generateTemplateModelDto.getTemplateType())) {
                extTemplateNameContent = extTemplateNameContent.replaceAll("__REPLACE_CONTENT", columnsContent);
            }
        }
        resultMap.put("facade/facade-service-api/src/main/java/com/mclon/facade/service/api/" + generateTemplateModelDto.getModuleName() + "/enums/" + generateTemplateModelDto.getEntityName() + "Enum.java", extTemplateNameContent);

        // Handle
        resource = new ClassPathResource(templateUrl + "TemplateNameHandle.java");
        extTemplateNameOrigin = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
        extTemplateNameContent = replaceContent(extTemplateNameOrigin, generateTemplateModelDto.getEntityName(), generateTemplateModelDto.getAuth(), generateTemplateModelDto.getDesc(), generateTemplateModelDto.getModuleName());
        resultMap.put("kernel/kernel-support-" + generateTemplateModelDto.getModuleName() + "/src/main/java/com/mclon/kernel/support/" + generateTemplateModelDto.getModuleName() + "/handle/" + generateTemplateModelDto.getEntityName() + "Handle.java", extTemplateNameContent);

        // mapper
        resource = new ClassPathResource(templateUrl + "TemplateNameMapper.java");
        extTemplateNameOrigin = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
        extTemplateNameContent = replaceContent(extTemplateNameOrigin, generateTemplateModelDto.getEntityName(), generateTemplateModelDto.getAuth(), generateTemplateModelDto.getDesc(), generateTemplateModelDto.getModuleName());
        if (Lists.newArrayList(GenerateTemplateModelConstants.TemplateTypeEnum.TKMAPPER_FORM.getCode(), GenerateTemplateModelConstants.TemplateTypeEnum.TKMAPPER_BASIC.getCode()).contains(generateTemplateModelDto.getTemplateType())) {
            resultMap.put("kernel/kernel-support-" + generateTemplateModelDto.getModuleName() + "/src/main/java/com/mclon/kernel/support/" + generateTemplateModelDto.getModuleName() + "/repository/" + generateTemplateModelDto.getEntityName() + "Mapper.java", extTemplateNameContent);
        } else {
            resultMap.put("kernel/kernel-support-" + generateTemplateModelDto.getModuleName() + "/src/main/java/com/mclon/kernel/support/" + generateTemplateModelDto.getModuleName() + "/mapper/" + generateTemplateModelDto.getEntityName() + "Mapper.java", extTemplateNameContent);
        }
        // provider
        resource = new ClassPathResource(templateUrl + "TemplateNameProvider.java");
        extTemplateNameOrigin = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
        extTemplateNameContent = replaceContent(extTemplateNameOrigin, generateTemplateModelDto.getEntityName(), generateTemplateModelDto.getAuth(), generateTemplateModelDto.getDesc(), generateTemplateModelDto.getModuleName());
        resultMap.put("facade/facade-service-api/src/main/java/com/mclon/facade/service/api/" + generateTemplateModelDto.getModuleName() + "/" + generateTemplateModelDto.getEntityName() + "Provider.java", extTemplateNameContent);

        // providerImpl
        resource = new ClassPathResource(templateUrl + "TemplateNameProviderImpl.java");
        extTemplateNameOrigin = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
        extTemplateNameContent = replaceContent(extTemplateNameOrigin, generateTemplateModelDto.getEntityName(), generateTemplateModelDto.getAuth(), generateTemplateModelDto.getDesc(), generateTemplateModelDto.getModuleName());
        resultMap.put("kernel/kernel-support-" + generateTemplateModelDto.getModuleName() + "/src/main/java/com/mclon/kernel/support/" + generateTemplateModelDto.getModuleName() + "/provider/" + generateTemplateModelDto.getEntityName() + "ProviderImpl.java", extTemplateNameContent);

        // service
        resource = new ClassPathResource(templateUrl + "TemplateNameService.java");
        extTemplateNameOrigin = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
        extTemplateNameContent = replaceContent(extTemplateNameOrigin, generateTemplateModelDto.getEntityName(), generateTemplateModelDto.getAuth(), generateTemplateModelDto.getDesc(), generateTemplateModelDto.getModuleName());
        resultMap.put("kernel/kernel-support-" + generateTemplateModelDto.getModuleName() + "/src/main/java/com/mclon/kernel/support/" + generateTemplateModelDto.getModuleName() + "/service/" + generateTemplateModelDto.getEntityName() + "Service.java", extTemplateNameContent);

        // serviceImpl
        resource = new ClassPathResource(templateUrl + "TemplateNameServiceImpl.java");
        extTemplateNameOrigin = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
        extTemplateNameContent = replaceContent(extTemplateNameOrigin, generateTemplateModelDto.getEntityName(), generateTemplateModelDto.getAuth(), generateTemplateModelDto.getDesc(), generateTemplateModelDto.getModuleName());
        resultMap.put("kernel/kernel-support-" + generateTemplateModelDto.getModuleName() + "/src/main/java/com/mclon/kernel/support/" + generateTemplateModelDto.getModuleName() + "/service/impl/" + generateTemplateModelDto.getEntityName() + "ServiceImpl.java", extTemplateNameContent);
        //mybatis xml
        if (Lists.newArrayList(GenerateTemplateModelConstants.TemplateTypeEnum.MYBATIS_PLUS_FORM.getCode(), GenerateTemplateModelConstants.TemplateTypeEnum.MYBATIS_PLUS_BASIC.getCode()).contains(generateTemplateModelDto.getTemplateType())) {
            resource = new ClassPathResource(templateUrl + "TemplateNameMapper.xml");
            extTemplateNameOrigin = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
            extTemplateNameContent = replaceContent(extTemplateNameOrigin, generateTemplateModelDto.getEntityName(), generateTemplateModelDto.getAuth(), generateTemplateModelDto.getDesc(), generateTemplateModelDto.getModuleName());
            if (StringUtils.isNotBlank(columnsContent)) {
                extTemplateNameContent = extTemplateNameContent.replaceAll("__REPLACE_CONTENT", columnsContent);
            }
            resultMap.put("kernel/kernel-support-" + generateTemplateModelDto.getModuleName() + "/src/main/resources/META-INF/" + generateTemplateModelDto.getModuleName() + "/" + generateTemplateModelDto.getEntityName() + "Mapper.xml", extTemplateNameContent);

            resource = new ClassPathResource(templateUrl + "TemplateNameMapperExt.xml");
            extTemplateNameOrigin = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
            extTemplateNameContent = replaceContent(extTemplateNameOrigin, generateTemplateModelDto.getEntityName(), generateTemplateModelDto.getAuth(), generateTemplateModelDto.getDesc(), generateTemplateModelDto.getModuleName());
            resultMap.put("kernel/kernel-support-" + generateTemplateModelDto.getModuleName() + "/src/main/resources/META-INF/" + generateTemplateModelDto.getModuleName() + "/" + generateTemplateModelDto.getEntityName() + "MapperExt.xml", extTemplateNameContent);
        }

    }

    /**
     * 获取资源文件路径
     *
     * @param generateTemplateModelDto
     * @return
     */
    private String getSourceUrl(GenerateTemplateModelDto generateTemplateModelDto) {
        String dir;
        String type;
        if (GenerateTemplateModelConstants.TemplateTypeEnum.MYBATIS_PLUS_BASIC.getCode().equals(generateTemplateModelDto.getTemplateType())) {
            dir = "mybatis-plus";
            type = "basic";
        } else if (GenerateTemplateModelConstants.TemplateTypeEnum.MYBATIS_PLUS_FORM.getCode().equals(generateTemplateModelDto.getTemplateType())) {
            dir = "mybatis-plus";
            type = "form";
        } else if (GenerateTemplateModelConstants.TemplateTypeEnum.TKMAPPER_BASIC.getCode().equals(generateTemplateModelDto.getTemplateType())) {
            dir = "tkmapper";
            type = "basic";
        } else if (GenerateTemplateModelConstants.TemplateTypeEnum.TKMAPPER_FORM.getCode().equals(generateTemplateModelDto.getTemplateType())) {
            dir = "tkmapper";
            type = "form";
        } else {
            throw new BusinessException("无效操作");
        }
        return "static/template/" + dir + "/" + type + "/";
    }

    /**
     * 替换原内容
     *
     * @param extTemplateNameOrigin
     * @return
     */
    private String replaceContent(String extTemplateNameOrigin, String entityName, String auth, String desc, String moduleName) {
        Map<String, String> replaceMap = Maps.newLinkedHashMap();
        String templateName = entityName.substring(0, 1).toLowerCase() + entityName.substring(1);
        String moduleNameUpper = moduleName.substring(0, 1).toUpperCase() + moduleName.substring(1);
        replaceMap.put("TemplateName", entityName);
        replaceMap.put("templateName", templateName);
        replaceMap.put("TEMPLATENAMEPROVIDER", entityName.toUpperCase() + "PROVIDER");
        replaceMap.put("TemplateCreateDate", DateUtils.format(new Date(), "yyyy-MM-dd"));
        replaceMap.put("TemplateMainIdStr", entityName + "Id");
        replaceMap.put("templateMainIdStr", templateName + "Id");
        replaceMap.put("TemplateCreate", auth);
        replaceMap.put("TemplateDesc", desc);
        replaceMap.put("templateModule", moduleName);
        replaceMap.put("TemplateModule", moduleNameUpper);
        replaceMap.put("templateDetailMainIdStr", templateName + "DetailId");
        replaceMap.put("TemplateDetailMainIdStr", entityName + "DetailId");
        replaceMap.put("fileUtilVersion", "web 1.0.0");
        for (Map.Entry<String, String> replaceEntry : replaceMap.entrySet()) {
            extTemplateNameOrigin = extTemplateNameOrigin.replaceAll(replaceEntry.getKey(), replaceEntry.getValue());
        }
        return extTemplateNameOrigin;
    }

    /**
     * 生成文件
     *
     * @param resultMap
     * @param generateTemplateModelDto
     */
    private ApiResponse writeFile(Map<String, String> resultMap, GenerateTemplateModelDto generateTemplateModelDto) {
        File zipFile = new File(generateTemplateModelDto.getEntityName() + ".zip");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
            ZipOutputStream zoutput = new ZipOutputStream(fileOutputStream);
            ZipEntry zipEntry = null;
            // 遍历源文件数组
            for (Map.Entry<String, String> stringMapEntry : resultMap.entrySet()) {
                zipEntry = new ZipEntry(stringMapEntry.getKey());
                zoutput.putNextEntry(zipEntry);
                // 定义每次读取的字节数组
                byte[] buffer = stringMapEntry.getValue().getBytes("UTF-8");
                zoutput.write(buffer);
            }
            zoutput.closeEntry();
            zoutput.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        InputStream ins = null;
        try {
            ins = new FileInputStream(zipFile);
            Response response = qnService.uploadFile(ins);
            zipFile.delete();
            if (response.isOK()) {
                QiNiuPurRet qiNiuPurRet = gson.fromJson(response.bodyString(), QiNiuPurRet.class);
                return ApiResponse.ofSuccess(cdnPrefix + qiNiuPurRet.key);
            } else {
                return ApiResponse.ofMessage(response.statusCode, response.getInfo());
            }
        } catch (FileNotFoundException | QiniuException e) {
            e.printStackTrace();
            return ApiResponse.ofStatus(ApiResponse.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 生成模型内容
     *
     * @param entityInfo
     * @return
     */
    private Map<String, String> generateEntityInfo(GenerateTemplateModelDto entityInfo) {
        Map<String, String> resultMap = new HashMap<>();
        StringBuffer entityContent = new StringBuffer();
        StringBuffer columnsContent = new StringBuffer();
        entityContent.append("package  com.mclon.facade.service.api.").append(entityInfo.getModuleName()).append(".model").append(";\r\n").append("\r\n");

        entityContent.append("import javax.persistence.Id;\r\n");
        entityContent.append("import lombok.Data;\r\n");
        entityContent.append("import javax.persistence.Column;\r\n");
        entityContent.append("import javax.persistence.Table;\r\n");
        entityContent.append("import java.io.Serializable;\r\n");
        entityContent.append("import java.math.BigDecimal;\r\n");
        if (StringUtils.isNotEmpty(entityInfo.getKeyId())) {
            entityContent.append("import javax.persistence.*;\r\n");
        }
        if (entityInfo.getImportDate()) {
            entityContent.append("import java.util.Date;\r\n").append("\r\n");
        }
        entityContent.append("@Table(schema = \"`").append(entityInfo.getDatabase()).append("`\", name = \"").append(entityInfo.getTableName()).append(
                "\")").append("\r\n");
        entityContent.append("@Data").append("\r\n");
        entityContent.append("public class ").append(entityInfo.getEntityName()).append(" implements Serializable").append(" {").append("\r" +
                "\n");
        for (int i = 0; i < entityInfo.getColumns().size(); i++) {
            String colComment = entityInfo.getColComment().get(i);
            if (StringUtils.isNotEmpty(colComment)) {
                Matcher m = blankPattern.matcher(colComment);
                colComment = m.replaceAll("");
                entityContent.append("\t/**\r\n").append("\t * ").append(colComment).append("\r\n").append(
                        "\t */").append("\r\n");
            }
            entityContent.append("\t@Column(name = \"" + entityInfo.getColumns().get(i) + "\")\r\n");
            String columnName = underline2Camel(entityInfo.getColumns().get(i));
            String columnUpperName = entityInfo.getColumns().get(i).toUpperCase();
            columnsContent.append("\t\t").append(columnUpperName).append("(\"").append(columnName).append("\", \"").append(colComment).append("\")");
            if (i == entityInfo.getColumns().size() - 1) {
                columnsContent.append(";\n");
            } else {
                columnsContent.append(",\n");
            }
            if (entityInfo.getKeyId().equals(columnName)) {
                entityContent.append("\t@Id\r\n");
                entityContent.append("\t@GeneratedValue(strategy = GenerationType.IDENTITY)\r\n");
            }
            entityContent.append("\tprivate ").append(getType(entityInfo.getColTypes().get(i))).append(" ").append(columnName)
                    .append(";").append("\r\n");

        }
        entityContent.append("\r\n");
        entityContent.append("}\r\n");
        resultMap.put(entityInfo.getEntityName(), entityContent.toString());
        resultMap.put("columnsContent", columnsContent.toString());
        return resultMap;
    }

    /**
     * 生成模型内容
     *
     * @param entityInfo
     * @return
     */
    private Map<String, String> generatePlusEntityInfo(GenerateTemplateModelDto entityInfo) {
        Map<String, String> resultMap = new HashMap<>();
        StringBuffer entityContent = new StringBuffer();
        StringBuffer xmlContent = new StringBuffer();
        entityContent.append("package  com.mclon.facade.service.api.").append(entityInfo.getModuleName()).append(".model").append(";\r\n").append("\r\n");

        entityContent.append("import com.baomidou.mybatisplus.annotation.IdType;\r\n");
        entityContent.append("import lombok.*;\r\n");
        entityContent.append("import com.baomidou.mybatisplus.annotation.TableField;\r\n");
        entityContent.append("import com.baomidou.mybatisplus.annotation.TableId;\r\n");
        entityContent.append("import com.baomidou.mybatisplus.annotation.TableName;\r\n");
        entityContent.append("import java.io.Serializable;\r\n");
        entityContent.append("import java.math.BigDecimal;\r\n");
        if (entityInfo.getImportDate()) {
            entityContent.append("import java.util.Date;\r\n").append("\r\n");
        }
        entityContent.append("@TableName(value = \"").append(entityInfo.getTableName()).append(
                "\")").append("\r\n");
        entityContent.append("@Getter").append("\r\n");
        entityContent.append("@Setter").append("\r\n");
        entityContent.append("@NoArgsConstructor").append("\r\n");
        entityContent.append("@AllArgsConstructor").append("\r\n");
        entityContent.append("@ToString").append("\r\n");
        entityContent.append("public class ").append(entityInfo.getEntityName()).append(" implements Serializable").append(" {").append("\r" +
                "\n");
        xmlContent.append("<resultMap id=\"BaseResultMap\" type=\"com.mclon.facade.service.api.").append(entityInfo.getModuleName()).append(".model.").append(entityInfo.getEntityName()).append("\">").append("\r\n");
        StringBuffer columnListStr = new StringBuffer();
        for (int i = 0; i < entityInfo.getColumns().size(); i++) {
            String colComment = entityInfo.getColComment().get(i);
            if (StringUtils.isNotEmpty(colComment)) {
                Matcher m = blankPattern.matcher(colComment);
                colComment = m.replaceAll("");
                entityContent.append("\t/**\r\n").append("\t * ").append(colComment).append("\r\n").append(
                        "\t */").append("\r\n");
            }
            String columnName = underline2Camel(entityInfo.getColumns().get(i));
            xmlContent.append("\t\t<result column=\"").append(entityInfo.getColumns().get(i)).append("\" property=\"").append(columnName).append("\" />").append("\r\n");
            if (entityInfo.getKeyId().equals(columnName)) {
                entityContent.append("\t@TableId(value = \"" + entityInfo.getColumns().get(i) + "\", type = IdType.AUTO)" + "\r\n");
            } else {
                entityContent.append("\t@TableField(\"" + entityInfo.getColumns().get(i) + "\")\r\n");
            }
            entityContent.append("\tprivate ").append(getType(entityInfo.getColTypes().get(i))).append(" ").append(columnName)
                    .append(";").append("\r\n");
            columnListStr.append(entityInfo.getColumns().get(i));
            if (entityInfo.getColumns().size() - 1 != i) {
                columnListStr.append(",");
            }
        }
        xmlContent.append("\t</resultMap>\r\n");
        xmlContent.append("\t<!-- 通用查询结果列 -->\r\n");
        xmlContent.append("\t<sql id=\"Base_Column_List\">").append(columnListStr).append("</sql>");
        entityContent.append("\r\n");
        entityContent.append("}\r\n");
        resultMap.put(entityInfo.getEntityName(), entityContent.toString());
        resultMap.put("xmlContent", xmlContent.toString());
        return resultMap;
    }

    /**
     * 下划线转首字母大写
     *
     * @param line
     * @return
     */
    public String underline2Camel(String line) {
        if (line == null || "".equals(line)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(matcher.start() == 0 ? Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }


    /**
     * 根据数据库类型获取java类型
     *
     * @param sqlType
     * @return
     */
    private String getType(String sqlType) {
        switch (sqlType) {
            case "bit":
            case "tinyint":
                return "Boolean";
            case "smallint":
            case "int":
            case "integer":
            case "char":
                return "Integer";
            case "bigint":
                return "Long";
            case "float":
            case "double":
            case "decimal":
            case "numeric":
                return "BigDecimal";
            case "varchar":
            case "text":
            case "mediumtext":
                return "String";
            case "date":
            case "time":
            case "datetime":
            case "timestamp":
                return "Date";
            default:
                System.out.println("ERROR DATA TYPE : " + sqlType);
                return null;
        }
    }

    /**
     * 初始化数据库字段
     *
     * @param entity
     * @return
     */
    private void initTable(GenerateTemplateModelDto entity) {
        try {
            entity.setImportDate(false);
            entity.setColComment(Lists.newArrayList());
            entity.setColumns(Lists.newArrayList());
            entity.setColTypes(Lists.newArrayList());
            Class.forName("com.mysql.jdbc.Driver");
            String url = String.format("jdbc:mysql://%s:%s/%s", entity.getHost(), entity.getPort(), entity.getDatabase());
            Connection conn = DriverManager.getConnection(url, entity.getUsername(), entity.getPassword());
            String strsql = "SELECT COLUMN_NAME, DATA_TYPE, COLUMN_KEY, COLUMN_COMMENT  FROM INFORMATION_SCHEMA.COLUMNS " +
                    "WHERE TABLE_SCHEMA = '" + entity.getDatabase() + "' AND TABLE_NAME = '" + entity.getTableName() + "'"; //读一行记录;
            PreparedStatement pstmt = conn.prepareStatement(strsql);
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                entity.getColumns().add(result.getString(1));
                entity.getColTypes().add(result.getString(2));
                entity.getColComment().add(result.getString(4));
                if ("date".equals(result.getString(2)) || "datetime".equals(result.getString(2)) || "timestamp".equals(result.getString(2))) {
                    entity.setImportDate(true);
                }
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            throw new BusinessException("连接数据库失败");
        }
    }

    public void generateXml() {// 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir("E:\\IdeaWorkSpace\\xunwu");
        gc.setAuthor("jobob");
        gc.setOpen(false);
        gc.setBaseResultMap(true);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://47.98.128.180:3306/xunwu?useUnicode=true&characterEncoding=utf-8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root123456");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.baomidou.ant");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("id");

        strategy.setInclude("user");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
