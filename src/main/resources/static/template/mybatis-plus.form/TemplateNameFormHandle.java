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
    public static String checkFormCreate(TemplateNameForm templateNameForm) {
        StringBuilder errorMsg = new StringBuilder();
        //1、校验主表新增
        if (null == templateNameForm.getExtTemplateName()) {
            errorMsg.append("主表信息不能为空");
        }else {
            errorMsg.append(checkCreate(templateNameForm.getExtTemplateName()));
        }
        //2、校验子表新增
        if (CollectionUtils.isEmpty(templateNameForm.getExtTemplateNameDetailList())) {
            errorMsg.append("子表信息不能为空");
        } else {
            for (int i = 0; i < templateNameForm.getExtTemplateNameDetailList().size(); i++) {
                errorMsg.append(checkDetailCreate(templateNameForm.getExtTemplateNameDetailList().get(i)));
            }
        }
        return errorMsg.toString();
    }

    /**
     * 校验TemplateDesc表单更新
     * 1、校验主表更新
     * 2、校验子表更新
     * @return
     */
    public static String checkFormUpdate(TemplateNameForm templateNameForm) {
        StringBuilder errorMsg = new StringBuilder();
        //1、校验主表更新
        if (null == templateNameForm.getExtTemplateName()) {
            errorMsg.append("主表信息不能为空");
        }else {
            errorMsg.append(checkUpdate(templateNameForm.getExtTemplateName()));
        }
        //2、校验子表更新
        if (CollectionUtils.isEmpty(templateNameForm.getExtTemplateNameDetailList())) {
            errorMsg.append("子表信息不能为空");
        } else {
            for (int i = 0; i < templateNameForm.getExtTemplateNameDetailList().size(); i++) {
                errorMsg.append(checkDetailUpdate(templateNameForm.getExtTemplateNameDetailList().get(i)));
            }
        }
        return errorMsg.toString();
    }

    /**
     * 校验TemplateDesc主表新增
     * @return
     */
    public static String checkCreate(ExtTemplateName extTemplateName) {
        StringBuilder errorMsg = new StringBuilder();
        /*if (condition){
            errorMsg.append("错误提示");
        }*/
        return errorMsg.toString();
    }
	
    /**
     * 校验TemplateDesc主表删除
     * @return
     */
    public static String checkDelete(ExtTemplateName extTemplateName) {
        StringBuilder errorMsg = new StringBuilder();
        if (CollectionUtils.isEmpty(extTemplateName.getIdList())){
            errorMsg.append("idList标识不能为空");
        }
        return errorMsg.toString();
    }
	
	/**
     * 校验TemplateDesc主表更新
     * @return
     */
    public static String checkUpdate(ExtTemplateName extTemplateName) {
        StringBuilder errorMsg = new StringBuilder();
        /*if (condition){
            errorMsg.append("错误提示");
        }*/
        return errorMsg.toString();
    }
	
    /**
     * 校验TemplateDesc主表获取详情
     * @return
     */
    public static String checkGet(ExtTemplateName extTemplateName) {
        StringBuilder errorMsg = new StringBuilder();
        if (null == extTemplateName.getTemplateMainIdStr()){
            errorMsg.append("id标识不能为空");
        }
        return errorMsg.toString();
    }

    /**
     * 校验TemplateDesc明细新增
     * @return
     */
    public static String checkDetailCreate(ExtTemplateNameDetail extTemplateNameDetail) {
        StringBuilder errorMsg = new StringBuilder();
        /*if (condition){
            errorMsg.append("错误提示");
        }*/
        return errorMsg.toString();
    }
	
    /**
     * 校验TemplateDesc明细删除
     * @return
     */
    public static String checkDetailDelete(ExtTemplateNameDetail extTemplateNameDetail) {
        StringBuilder errorMsg = new StringBuilder();
        if (CollectionUtils.isEmpty(extTemplateNameDetail.getIdList())){
            errorMsg.append("idList标识不能为空");
        }
        return errorMsg.toString();
    }
	
	/**
     * 校验TemplateDesc明细更新
     * @return
     */
    public static String checkDetailUpdate(ExtTemplateNameDetail extTemplateNameDetail) {
        StringBuilder errorMsg = new StringBuilder();
        /*if (condition){
            errorMsg.append("错误提示");
        }*/
        return errorMsg.toString();
    }
	
    /**
     * 校验TemplateDesc明细获取详情
     * @return
     */
    public static String checkDetailGet(ExtTemplateNameDetail extTemplateNameDetail) {
        StringBuilder errorMsg = new StringBuilder();
        if (null == extTemplateNameDetail.getTempalteDetailMainIdStr()){
            errorMsg.append("id标识不能为空");
        }
        return errorMsg.toString();
    }
}
