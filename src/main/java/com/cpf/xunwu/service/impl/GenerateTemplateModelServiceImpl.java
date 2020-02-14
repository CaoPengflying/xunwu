package com.cpf.xunwu.service.impl;

import com.alibaba.excel.util.DateUtils;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
                resultMap.put("facade/facade-service-api/src/main/java/com/mclon/facade/service/api/" + generateTemplateModelDto.getModuleName() + "/model/" + stringStringEntry.getKey() + ".java", stringStringEntry.getValue());
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
        String templateUrl = "static/template/" + dir + "/" + type + "/";
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
            extTemplateNameContent = extTemplateNameContent.replaceAll("__REPLACE_CONTENT", columnsContent);

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
        for (int i = 0; i < entityInfo.getColumns().size(); i++) {
            String colComment = entityInfo.getColComment().get(i);
            if (StringUtils.isNotEmpty(colComment)) {
                Matcher m = blankPattern.matcher(colComment);
                colComment = m.replaceAll("");
                entityContent.append("\t/**\r\n").append("\t * ").append(colComment).append("\r\n").append(
                        "\t */").append("\r\n");
            }
            String columnName = underline2Camel(entityInfo.getColumns().get(i));
            if (entityInfo.getKeyId().equals(columnName)) {
                entityContent.append("\t@TableId(value = \"" + entityInfo.getColumns().get(i) + "\", type = IdType.AUTO)" + "\r\n");
            } else {
                entityContent.append("\t@TableField(\"" + entityInfo.getColumns().get(i) + "\")\r\n");
            }
            entityContent.append("\tprivate ").append(getType(entityInfo.getColTypes().get(i))).append(" ").append(columnName)
                    .append(";").append("\r\n");
        }
        entityContent.append("\r\n");
        entityContent.append("}\r\n");
        resultMap.put(entityInfo.getEntityName(), entityContent.toString());
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
}
