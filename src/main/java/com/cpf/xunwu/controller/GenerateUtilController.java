package com.cpf.xunwu.controller;

import com.cpf.xunwu.base.ApiResponse;
import com.cpf.xunwu.dto.GenerateTemplateModelDto;
import com.cpf.xunwu.service.GenerateTemplateModelService;
import com.cpf.xunwu.util.GenerateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author caopengflying
 * @time 2020/1/21
 */
@Controller
public class GenerateUtilController {
    @Resource
    private GenerateTemplateModelService generateTemplateModelService;

    @PostMapping("/generate/generateModel")
    @ResponseBody
    public ApiResponse generateModel(HttpServletResponse response, GenerateTemplateModelDto generateTemplateModelDto) {
        generateTemplateModelService.generateTemplateModel(generateTemplateModelDto);
        return ApiResponse.ofSuccess(null);
    }
}

