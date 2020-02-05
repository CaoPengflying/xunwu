package com.cpf.xunwu.controller;

import com.cpf.xunwu.base.ApiResponse;
import com.cpf.xunwu.base.QiNiuPurRet;
import com.cpf.xunwu.dto.GenerateTemplateModelDto;
import com.cpf.xunwu.service.GenerateTemplateModelService;
import com.cpf.xunwu.service.QNService;
import com.cpf.xunwu.util.GenerateService;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * @author caopengflying
 * @time 2020/1/21
 */
@Controller
public class GenerateUtilController {
    @Resource
    private GenerateTemplateModelService generateTemplateModelService;
    @Resource
    private QNService qnService;
    @Resource
    private Gson gson;
    @Value("${qiniu.cdn.prefix}")
    private String cdnPrefix;

    @PostMapping("/generate/generateModel")
    @ResponseBody
    public ApiResponse generateModel(GenerateTemplateModelDto generateTemplateModelDto) {
        return generateTemplateModelService.generateTemplateModel(generateTemplateModelDto);
    }
}

