package com.cpf.xunwu.service;

import com.cpf.xunwu.base.ApiResponse;
import com.cpf.xunwu.dto.GenerateTemplateModelDto;

import javax.servlet.http.HttpServletResponse;

/**
 * @author caopengflying
 * @time 2020/1/31
 */
public interface GenerateTemplateModelService {
    /**
     * 生成模板代码
     * @param generateTemplateModelDto
     */
    ApiResponse generateTemplateModel(GenerateTemplateModelDto generateTemplateModelDto);
}
