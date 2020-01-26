package com.cpf.xunwu.controller;

import com.cpf.xunwu.base.ApiResponse;
import com.cpf.xunwu.base.ErrorConstant;
import com.cpf.xunwu.base.QiNiuPurRet;
import com.cpf.xunwu.base.Result;
import com.cpf.xunwu.service.QNService;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author caopengflying
 * @time 2020/1/23
 */
@Controller
@RequestMapping("admin")
public class AdminController {
    @Resource
    private QNService qnService;
    @Resource
    private Gson gson;
    @GetMapping("/center")
    public String adminCenterPage() {
        return "admin/center";
    }

    @GetMapping("/welcome")
    public String adminWelcomePage() {
        return "admin/welcome";
    }

    @GetMapping("/login")
    public String adminLoginPage() {
        return "admin/login";
    }

    @GetMapping("add/house")
    public String addHousePage() {
        return "admin/house-add";
    }

    /**
     * 图片上传
     *
     * @param file
     * @return
     */
    @PostMapping(value = "upload/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ApiResponse uploadPhoto(@RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
           return ApiResponse.ofMessage(ApiResponse.Status.NOT_VALID_PARAM.getCode(),"参数错误");
        }
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            Response response = qnService.uploadFile(inputStream);
            if (response.isOK()){
                QiNiuPurRet qiNiuPurRet = gson.fromJson(response.bodyString(), QiNiuPurRet.class);
                return ApiResponse.ofSuccess(qiNiuPurRet);
            }else {
                return ApiResponse.ofMessage(response.statusCode, response.getInfo());
            }
        } catch (QiniuException e){
            Response response = e.response;
            try {
                return ApiResponse.ofMessage(response.statusCode, response.bodyString());
            } catch (QiniuException ex) {
                return ApiResponse.ofStatus(ApiResponse.Status.INTERNAL_SERVER_ERROR);
            }
        } catch (IOException e) {
            return ApiResponse.ofStatus(ApiResponse.Status.INTERNAL_SERVER_ERROR);
        }

    }
}
