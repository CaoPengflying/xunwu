package com.mclon.kernel.support.templateModule.handle;

import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateName;

import java.util.List;
import java.util.Map;

import com.mclon.facade.service.api.common.BaseModel;

import com.mclon.commons.support.webmvc.constants.ErrorConstant;
import com.mclon.commons.support.webmvc.result.Result;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.compress.utils.Lists;


/**
 * @author TemplateCreate
 * @version fileUtilVersion
 * @description TemplateDesc Service扩展
 * @date Created in TemplateCreateDate
 */

public class TemplateNameHandle {

    /**
     * 校验TemplateDesc主表新增
     *
     * @return
     */
    public static Result checkCreate(ExtTemplateName extTemplateName) {
        StringBuffer errorMsg = new StringBuffer();
        /*if (condition){
            errorMsg.append("错误提示");
        }*/
        if (StringUtils.isNotEmpty(errorMsg)) {
            return ErrorConstant.getErrorResult(ErrorConstant.PARAM_IS_NULL, errorMsg.toString());
        }
        return ErrorConstant.getSuccessResult("");
    }

    /**
     * 校验TemplateDesc主表删除
     *
     * @return
     */
    public static Result checkDelete(ExtTemplateName extTemplateName) {
        StringBuffer errorMsg = new StringBuffer();
        if (CollectionUtils.isEmpty(extTemplateName.getIdList())) {
            errorMsg.append("idList标识不能为空");
        }
        if (StringUtils.isNotEmpty(errorMsg)) {
            return ErrorConstant.getErrorResult(ErrorConstant.PARAM_IS_NULL, errorMsg.toString());
        }
        return ErrorConstant.getSuccessResult("");
    }

    /**
     * 校验TemplateDesc主表更新
     *
     * @return
     */
    public static Result checkUpdate(ExtTemplateName extTemplateName) {
        StringBuffer errorMsg = new StringBuffer();
        /*if (condition){
            errorMsg.append("错误提示");
        }*/
        if (StringUtils.isNotEmpty(errorMsg)) {
            return ErrorConstant.getErrorResult(ErrorConstant.PARAM_IS_NULL, errorMsg.toString());
        }
        return ErrorConstant.getSuccessResult("");
    }

    /**
     * 校验TemplateDesc主表获取详情
     *
     * @return
     */
    public static Result checkGet(ExtTemplateName extTemplateName) {
        StringBuffer errorMsg = new StringBuffer();
        if (null == extTemplateName.getTemplateMainIdStr()) {
            errorMsg.append("id标识不能为空");
        }
        if (StringUtils.isNotEmpty(errorMsg)) {
            return ErrorConstant.getErrorResult(ErrorConstant.PARAM_IS_NULL, errorMsg.toString());
        }
        return ErrorConstant.getSuccessResult("");
    }


}
