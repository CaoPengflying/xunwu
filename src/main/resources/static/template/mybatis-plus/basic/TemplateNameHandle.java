package com.mclon.kernel.support.templateModule.handle;

import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateName;

import java.util.List;
import java.util.Map;
import com.mclon.commons.support.lang.convention.IntegerUtils;

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
    public static String checkCreate(ExtTemplateName extTemplateName) {
        StringBuilder errorMsg = new StringBuilder();
        /*if (condition){
            errorMsg.append("错误提示");
        }*/
       return errorMsg.toString();
    }

    /**
     * 校验TemplateDesc主表删除
     *
     * @return
     */
    public static String checkDelete(ExtTemplateName extTemplateName) {
        StringBuilder errorMsg = new StringBuilder();
        if (CollectionUtils.isEmpty(extTemplateName.getIdList())) {
            errorMsg.append("idList标识不能为空");
        }
       return errorMsg.toString();
    }

    /**
     * 校验TemplateDesc主表更新
     *
     * @return
     */
    public static String checkUpdate(ExtTemplateName extTemplateName) {
        StringBuilder errorMsg = new StringBuilder();
        if (IntegerUtils.isEmpty(extTemplateName.getTemplateMainIdStr())){
            errorMsg.append("id标识不能为空");
        }
       return errorMsg.toString();
    }

    /**
     * 校验TemplateDesc主表获取详情
     *
     * @return
     */
    public static String checkGet(ExtTemplateName extTemplateName) {
        StringBuilder errorMsg = new StringBuilder();
        if (null == extTemplateName.getTemplateMainIdStr()) {
            errorMsg.append("id标识不能为空");
        }
        return errorMsg.toString();
    }


}
