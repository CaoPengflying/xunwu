package com.mclon.kernel.support.templateModule.service;

import com.mclon.facade.service.api.common.BaseService;
import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateName;
import com.mclon.commons.support.webmvc.result.Result;

import java.util.List;
import tk.mybatis.mapper.entity.Example;

/**
 * @author TemplateCreate
 * @version fileUtilVersion
 * @description TemplateDesc
 * @date Created in TemplateCreateDate
 */
public interface TemplateNameService extends BaseService<ExtTemplateName> {
    /**
     * 按照条件查询
     *
     * @param example
     * @return
     */
    Result<List<ExtTemplateName>> listByCondition(Example example);
}
