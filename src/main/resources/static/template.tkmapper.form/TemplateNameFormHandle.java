package com.mclon.kernel.support.templateModule.handle;

import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateName;
import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateNameDetail;
import com.mclon.facade.service.api.templateModule.form.TemplateNameForm;
import java.util.List;
import com.mclon.commons.support.webmvc.constants.ErrorConstant;
import com.mclon.commons.support.webmvc.result.Result;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @description TemplateDesc service扩展
 * @author TemplateCreate
 * @date Created in TemplateCreateDate
 * @version fileUtilVersion
 */
public class TemplateNameFormHandle {
	
        /**
     * 校验TemplateDesc表单新增
     * 1、校验主表新增
     * 2、校验子表新增
     * @return
     */
    public static Result checkFormCreate(TemplateNameForm templateNameForm) {
        StringBuffer errorMsg = new StringBuffer();
        //1、校验主表新增
        if (null == templateNameForm.getExtTemplateName()) {
            errorMsg.append("主表信息不能为空");
        }else {
            Result result = checkCreate(templateNameForm.getExtTemplateName());
            if (ErrorConstant.checkSuccess(result)) {
                errorMsg.append("[").append(result.getText()).append("]");
            }
        }
        //2、校验子表新增
        if (CollectionUtils.isEmpty(templateNameForm.getExtTemplateNameDetailList())) {
            errorMsg.append("子表信息不能为空");
        } else {
            for (int i = 0; i < templateNameForm.getExtTemplateNameDetailList().size(); i++) {
                Result result = checkDetailCreate(templateNameForm.getExtTemplateNameDetailList().get(i));
                if (ErrorConstant.checkSuccess(result)) {
                    errorMsg.append("[第").append(i + "条" + result.getText()).append("]");
                }
            }
        }
        if (StringUtils.isNotEmpty(errorMsg)){
            return ErrorConstant.getErrorResult(ErrorConstant.PARAM_IS_NULL,errorMsg.toString());
        }
        return ErrorConstant.getSuccessResult("");
    }

    /**
     * 校验TemplateDesc表单更新
     * 1、校验主表更新
     * 2、校验子表更新
     * @return
     */
    public static Result checkFormUpdate(TemplateNameForm templateNameForm) {
        StringBuffer errorMsg = new StringBuffer();
        //1、校验主表更新
        if (null == templateNameForm.getExtTemplateName()) {
            errorMsg.append("主表信息不能为空");
        }else {
            Result result = checkUpdate(templateNameForm.getExtTemplateName());
            if (ErrorConstant.checkSuccess(result)) {
                errorMsg.append("[").append(result.getText()).append("]");
            }
        }
        //2、校验子表更新
        if (CollectionUtils.isEmpty(templateNameForm.getExtTemplateNameDetailList())) {
            errorMsg.append("子表信息不能为空");
        } else {
            for (int i = 0; i < templateNameForm.getExtTemplateNameDetailList().size(); i++) {
                Result result = checkDetailUpdate(templateNameForm.getExtTemplateNameDetailList().get(i));
                if (ErrorConstant.checkSuccess(result)) {
                    errorMsg.append("[第").append(i + "条" + result.getText()).append("]");
                }
            }
        }
        if (StringUtils.isNotEmpty(errorMsg)){
            return ErrorConstant.getErrorResult(ErrorConstant.PARAM_IS_NULL,errorMsg.toString());
        }
        return ErrorConstant.getSuccessResult("");
    }

    /**
     * 校验TemplateDesc主表新增
     * @return
     */
    public static Result checkCreate(ExtTemplateName extTemplateName) {
        StringBuffer errorMsg = new StringBuffer();
        /*if (condition){
            errorMsg.append("错误提示");
        }*/
        if (StringUtils.isNotEmpty(errorMsg)){
            return ErrorConstant.getErrorResult(ErrorConstant.PARAM_IS_NULL,errorMsg.toString());
        }
        return ErrorConstant.getSuccessResult("");
    }
	
    /**
     * 校验TemplateDesc主表删除
     * @return
     */
    public static Result checkDelete(ExtTemplateName extTemplateName) {
        StringBuffer errorMsg = new StringBuffer();
        if (CollectionUtils.isEmpty(extTemplateName.getIdList())){
            errorMsg.append("idList标识不能为空");
        }
        if (StringUtils.isNotEmpty(errorMsg)){
            return ErrorConstant.getErrorResult(ErrorConstant.PARAM_IS_NULL,errorMsg.toString());
        }
        return ErrorConstant.getSuccessResult("");
    }
	
	/**
     * 校验TemplateDesc主表更新
     * @return
     */
    public static Result checkUpdate(ExtTemplateName extTemplateName) {
        StringBuffer errorMsg = new StringBuffer();
        /*if (condition){
            errorMsg.append("错误提示");
        }*/
        if (StringUtils.isNotEmpty(errorMsg)){
            return ErrorConstant.getErrorResult(ErrorConstant.PARAM_IS_NULL,errorMsg.toString());
        }
        return ErrorConstant.getSuccessResult("");
    }
	
    /**
     * 校验TemplateDesc主表获取详情
     * @return
     */
    public static Result checkGet(ExtTemplateName extTemplateName) {
        StringBuffer errorMsg = new StringBuffer();
        if (null == extTemplateName.getTemplateMainIdStr()){
            errorMsg.append("id标识不能为空");
        }
        if (StringUtils.isNotEmpty(errorMsg)){
            return ErrorConstant.getErrorResult(ErrorConstant.PARAM_IS_NULL,errorMsg.toString());
        }
        return ErrorConstant.getSuccessResult("");
    }

    /**
     * 校验TemplateDesc明细新增
     * @return
     */
    public static Result checkDetailCreate(ExtTemplateNameDetail extTemplateNameDetail) {
        StringBuffer errorMsg = new StringBuffer();
        /*if (condition){
            errorMsg.append("错误提示");
        }*/
        if (StringUtils.isNotEmpty(errorMsg)){
            return ErrorConstant.getErrorResult(ErrorConstant.PARAM_IS_NULL,errorMsg.toString());
        }
        return ErrorConstant.getSuccessResult("");
    }
	
    /**
     * 校验TemplateDesc明细删除
     * @return
     */
    public static Result checkDetailDelete(ExtTemplateNameDetail extTemplateNameDetail) {
        StringBuffer errorMsg = new StringBuffer();
        if (CollectionUtils.isEmpty(extTemplateNameDetail.getIdList())){
            errorMsg.append("idList标识不能为空");
        }
        if (StringUtils.isNotEmpty(errorMsg)){
            return ErrorConstant.getErrorResult(ErrorConstant.PARAM_IS_NULL,errorMsg.toString());
        }
        return ErrorConstant.getSuccessResult("");
    }
	
	/**
     * 校验TemplateDesc明细更新
     * @return
     */
    public static Result checkDetailUpdate(ExtTemplateNameDetail extTemplateNameDetail) {
        StringBuffer errorMsg = new StringBuffer();
        /*if (condition){
            errorMsg.append("错误提示");
        }*/
        if (StringUtils.isNotEmpty(errorMsg)){
            return ErrorConstant.getErrorResult(ErrorConstant.PARAM_IS_NULL,errorMsg.toString());
        }
        return ErrorConstant.getSuccessResult("");
    }
	
    /**
     * 校验TemplateDesc明细获取详情
     * @return
     */
    public static Result checkDetailGet(ExtTemplateNameDetail extTemplateNameDetail) {
        StringBuffer errorMsg = new StringBuffer();
        if (null == extTemplateNameDetail.getTempalteDetailMainIdStr()){
            errorMsg.append("id标识不能为空");
        }
        if (StringUtils.isNotEmpty(errorMsg)){
            return ErrorConstant.getErrorResult(ErrorConstant.PARAM_IS_NULL,errorMsg.toString());
        }
        return ErrorConstant.getSuccessResult("");
    }
}
