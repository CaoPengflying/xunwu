package com.cpf.xunwu;

import com.cpf.xunwu.constants.GenerateTemplateModelConstants;
import com.cpf.xunwu.dto.GenerateTemplateModelDto;
import com.cpf.xunwu.service.impl.GenerateTemplateModelServiceImpl;
import com.google.common.collect.Maps;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author caopengflying
 * @time 2020/1/31
 */
public class GenerateTemplateModelServiceTest extends XunwuApplicationTests {
    @Resource
    private GenerateTemplateModelServiceImpl generateTemplateModelService;

    @Test
    public void testReadAndReplace() {
        GenerateTemplateModelDto generateTemplateModelDto = new GenerateTemplateModelDto();
        generateTemplateModelDto.setDesc("测试");
        generateTemplateModelDto.setEntityName("MyTest");
        generateTemplateModelDto.setModuleName("platform");
        generateTemplateModelDto.setAuth("caopengflying");
        generateTemplateModelDto.setTemplateType(GenerateTemplateModelConstants.TemplateTypeEnum.MYBATIS_PLUS_BASIC.getCode());
        try {
            generateTemplateModelService.readTemplateAndReplace(Maps.newHashMap(), generateTemplateModelDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGenerateTemplate() {
        GenerateTemplateModelDto generateTemplateModelDto = new GenerateTemplateModelDto();
        generateTemplateModelDto.setDesc("测试");
        generateTemplateModelDto.setModuleName("stock");
        generateTemplateModelDto.setAuth("caopengflying");
        generateTemplateModelDto.setHost("rm-uf6sia30x7328dj99fo.mysql.rds.aliyuncs.com");
        generateTemplateModelDto.setUsername("leysen_manager");
        generateTemplateModelDto.setPassword("rfmzJTsGUooL&yrd");
        generateTemplateModelDto.setDatabase("stock");
        generateTemplateModelDto.setProjectName("tesiro");
        generateTemplateModelDto.setTableName("css_test_import");
        generateTemplateModelDto.setEntityName("CssTestImport");
        generateTemplateModelDto.setPort("3306");
        generateTemplateModelDto.setTemplateType(GenerateTemplateModelConstants.TemplateTypeEnum.MYBATIS_PLUS_BASIC.getCode());
        generateTemplateModelService.generateTemplateModel(generateTemplateModelDto);
    }

}
