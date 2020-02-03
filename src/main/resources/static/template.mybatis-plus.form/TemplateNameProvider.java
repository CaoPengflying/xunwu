package com.mclon.facade.service.api.templateModule;

import com.mclon.facade.service.api.common.BaseProvider;
import com.mclon.facade.service.api.templateModule.form.TemplateNameForm;
import com.mclon.commons.support.webmvc.result.Result;
import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateName;

import java.util.List;



/**
 * @description TemplateDesc
 * @author TemplateCreate
 * @date Created in TemplateCreateDate
 * @version fileUtilVersion
 */
public interface TemplateNameProvider extends BaseProvider<TemplateNameForm> {
    /**
     * 标准查询
     * @param object ExtTemplateName
     * @return 
     */
    Result<List<ExtTemplateName>> list(Object object);

    /**
     * 按照条件查询
     * @param object Example
     * @return 
     */
    Result<List<ExtTemplateName>> listByCondition(Object object);
}
