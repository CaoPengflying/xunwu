package com.cpf.xunwu.service;

import com.cpf.xunwu.dto.GenerateTemplateModelDto;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.OutputStream;

/**
 * @author caopengflying
 * @time 2020/1/31
 */
public interface GenerateTemplateModelService {
    /**
     * 生成模板代码
     * @param generateTemplateModelDto
     */
    String generateTemplateModel(GenerateTemplateModelDto generateTemplateModelDto);
}
