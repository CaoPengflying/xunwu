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
        String filePath = generateTemplateModelService.generateTemplateModel(generateTemplateModelDto);
        try {
            File f = new File(new String(filePath.getBytes("ISO8859-1"), "utf-8"));
            Response response = qnService.uploadFile(f);
            if (response.isOK()) {
                QiNiuPurRet qiNiuPurRet = gson.fromJson(response.bodyString(), QiNiuPurRet.class);
                return ApiResponse.ofSuccess(cdnPrefix + qiNiuPurRet.key);
            } else {
                return ApiResponse.ofMessage(response.statusCode, response.getInfo());
            }
        } catch (IOException e) {
            return ApiResponse.ofStatus(ApiResponse.Status.INTERNAL_SERVER_ERROR);
        }
    }
}

