package com.cpf.xunwu.controller;

import com.cpf.xunwu.base.*;
import com.cpf.xunwu.dto.HouseDto;
import com.cpf.xunwu.form.DatatableSearch;
import com.cpf.xunwu.form.HouseForm;
import com.cpf.xunwu.service.HouseService;
import com.cpf.xunwu.service.QNService;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
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
    private HouseService houseService;
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

    @GetMapping("/add/house")
    public String addHousePage() {
        return "admin/house-add";
    }

    /**
     * 房源列表页
     *
     * @return
     */
    @GetMapping("/house/list")
    public String houseListPage() {
        return "admin/house-list";
    }

    /**
     * 图片上传
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/upload/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ApiResponse uploadPhoto(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ApiResponse.ofMessage(ApiResponse.Status.NOT_VALID_PARAM.getCode(), "参数错误");
        }
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            Response response = qnService.uploadFile(inputStream);
            if (response.isOK()) {
                QiNiuPurRet qiNiuPurRet = gson.fromJson(response.bodyString(), QiNiuPurRet.class);
                return ApiResponse.ofSuccess(qiNiuPurRet);
            } else {
                return ApiResponse.ofMessage(response.statusCode, response.getInfo());
            }
        } catch (QiniuException e) {
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

    @PostMapping(value = "/add/house")
    @ResponseBody
    public ApiResponse addHouse(@Valid @ModelAttribute("form-house-add") HouseForm houseForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ApiResponse(HttpStatus.BAD_REQUEST.value(), bindingResult.getAllErrors().get(0).getDefaultMessage(), null);
        }
        if (CollectionUtils.isEmpty(houseForm.getPhotos()) || StringUtils.isEmpty(houseForm.getCover())) {
            return ApiResponse.ofMessage(HttpStatus.BAD_REQUEST.value(), "必须上传图片");
        }
        ServiceResult<HouseDto> result = houseService.saveForm(houseForm);
        if (result.getSuccess()) {
            return ApiResponse.ofSuccess(result.getResult());
        } else {
            return ApiResponse.ofStatus(ApiResponse.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/houses")
    @ResponseBody
    public ApiDataTableResponse houses(@ModelAttribute DatatableSearch datatableSearch) {
        ServiceMultiResult<HouseDto> result = houseService.adminQuery(datatableSearch);
        ApiDataTableResponse response = new ApiDataTableResponse(ApiResponse.Status.SUCCESS);
        response.setData(result.getResult());
        response.setRecordsFiltered(result.getTotal());
        response.setRecordsTotal(result.getTotal());
        response.setDraw(datatableSearch.getDraw());
        return response;
    }
}
