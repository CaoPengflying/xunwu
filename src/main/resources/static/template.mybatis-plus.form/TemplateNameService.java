package com.mclon.kernel.support.templateModule.service;

import com.mclon.facade.service.api.common.BaseService;
import com.mclon.facade.service.api.templateModule.form.TemplateNameForm;
import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateName;

import java.util.List;
import com.mclon.commons.support.webmvc.result.Result;

/**
 * @description TemplateDesc
 * @author TemplateCreate
 * @date Created in TemplateCreateDate
 * @version fileUtilVersion
 */
public interface TemplateNameService extends BaseService<TemplateNameForm> {
     /**
     * 根据条件查询
     * @param object Example
     * @return
     */
    Result<List<ExtTemplateName>> listByCondition(Object object);
}
