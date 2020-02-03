package com.mclon.bomc.support.templateModule.web;

import com.mclon.commons.support.webmvc.constants.ErrorConstant;
import com.aliyun.oss.model.ObjectMetadata;

import com.mclon.commons.support.webmvc.constants.ApplicationConstants;
import org.apache.commons.lang3.StringUtils;
import com.mclon.facade.service.api.common.BaseModel;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import com.mclon.commons.support.webmvc.export.ExportResolve;
import com.mclon.facade.service.api.templateModule.constants.TemplateNameConstants;
import com.mclon.bomc.support.templateModule.service.TemplateNameBizService;
import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateName;
import com.mclon.bomc.cloud.framework.annotation.QineasyGuest;
import com.mclon.bomc.cloud.framework.annotation.QineasyService;
import com.mclon.commons.support.webmvc.constants.HeadersConstant;
import com.mclon.commons.support.webmvc.result.Result;
import com.mclon.facade.service.api.utils.GetInfo;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.mclon.commons.support.lang.convention.DateUtils;

import io.swagger.annotations.ApiParam;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.aliyun.oss.model.PutObjectRequest;


import java.util.Iterator;
import java.util.UUID;


/**
 * @author TemplateCreate
 * @version fileUtilVersion
 * @description TemplateDesc
 * @date Created in TemplateCreateDate
 */
@QineasyService
@RequestMapping(value = "templateName", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TemplateNameController {

    private static final Logger logger = LoggerFactory.getLogger(TemplateNameController.class);


    @Resource
    private TemplateNameBizService templateNameBizService;

    /**
     * Reason: 新增TemplateDesc(单表)
     */
    @QineasyGuest
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result create(
            @RequestParam(value = "jsonObject") String jsonObject,
            @RequestHeader(name = HeadersConstant.X_OAUTH_USER_ID) String userId,
            @RequestHeader(name = HeadersConstant.X_OAUTH_USER_NAME) String userName,
            @RequestHeader(name = HeadersConstant.X_OAUTH_ORGANIZATION_ID) String organizationId,
            @RequestHeader(name = HeadersConstant.X_OAUTH_ORGANIZATION_NAME) String organizationName) throws Exception {
        //转JSON对象
        ExtTemplateName extTemplateName = GetInfo.parseJSON(ExtTemplateName.class, jsonObject);
        GetInfo.stringToJsonString(Lists.newArrayList(extTemplateName), ExtTemplateName.class,
                TemplateNameConstants.MAIN_ID_STR, userId, userName, organizationId, organizationName);
        return templateNameBizService.create(extTemplateName);
    }


    /**
     * Reason: 删除TemplateDesc
     */
    @QineasyGuest
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(
            @ApiParam(value = "jsonObject:{idList:[1,2]}") @RequestParam(value = "jsonObject") String jsonObject,
            @RequestHeader(name = HeadersConstant.X_OAUTH_USER_ID) String userId,
            @RequestHeader(name = HeadersConstant.X_OAUTH_USER_NAME) String userName,
            @RequestHeader(name = HeadersConstant.X_OAUTH_ORGANIZATION_ID) String organizationId,
            @RequestHeader(name = HeadersConstant.X_OAUTH_ORGANIZATION_NAME) String organizationName) {
        ExtTemplateName extTemplateName = GetInfo.parseJSON(ExtTemplateName.class, jsonObject);
        return templateNameBizService.delete(extTemplateName);
    }

    /**
     * Reason: 修改TemplateDesc(单表)
     */
    @QineasyGuest
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(
            @RequestParam(value = "jsonObject") String jsonObject,
            @RequestHeader(name = HeadersConstant.X_OAUTH_USER_ID) String userId,
            @RequestHeader(name = HeadersConstant.X_OAUTH_USER_NAME) String userName,
            @RequestHeader(name = HeadersConstant.X_OAUTH_ORGANIZATION_ID) String organizationId,
            @RequestHeader(name = HeadersConstant.X_OAUTH_ORGANIZATION_NAME) String organizationName
    ) throws Exception {
        ExtTemplateName extTemplateName = GetInfo.parseJSON(ExtTemplateName.class, jsonObject);
        GetInfo.stringToJsonString(Lists.newArrayList(extTemplateName), ExtTemplateName.class,
                TemplateNameConstants.MAIN_ID_STR, userId, userName, organizationId, organizationName);
        return templateNameBizService.update(extTemplateName);
    }


    /**
     * Reason: 获取详情
     */
    @QineasyGuest
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Result get(
            @RequestParam(value = "jsonObject") String jsonObject,
            @RequestHeader(name = HeadersConstant.X_OAUTH_USER_ID) String userId,
            @RequestHeader(name = HeadersConstant.X_OAUTH_USER_NAME) String userName,
            @RequestHeader(name = HeadersConstant.X_OAUTH_ORGANIZATION_ID) String organizationId,
            @RequestHeader(name = HeadersConstant.X_OAUTH_ORGANIZATION_NAME) String organizationName
    ) {
        ExtTemplateName extTemplateName = GetInfo.parseJSON(ExtTemplateName.class, jsonObject);
        return templateNameBizService.get(extTemplateName);
    }

}
