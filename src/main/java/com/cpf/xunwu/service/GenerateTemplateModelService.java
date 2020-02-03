package com.cpf.xunwu.service;

import com.cpf.xunwu.dto.GenerateTemplateModelDto;
import org.springframework.stereotype.Service;

/**
 * @author caopengflying
 * @time 2020/1/31
 */
public interface GenerateTemplateModelService {
    /**
     * 生成模板代码
     * @param generateTemplateModelDto
     */
    void generateTemplateModel(GenerateTemplateModelDto generateTemplateModelDto);
}
