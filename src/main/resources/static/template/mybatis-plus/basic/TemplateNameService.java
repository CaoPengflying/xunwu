package com.mclon.kernel.support.templateModule.service;

import com.mclon.facade.service.api.common.BasePlusService;
import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateName;
import com.mclon.facade.service.api.templateModule.model.TemplateName;
import com.mclon.commons.support.webmvc.result.Result;

import java.util.List;
import tk.mybatis.mapper.entity.Example;

/**
 * @author TemplateCreate
 * @version fileUtilVersion
 * @description TemplateDesc
 * @date Created in TemplateCreateDate
 */
public interface TemplateNameService extends BasePlusService<ExtTemplateName,TemplateName> {
}
