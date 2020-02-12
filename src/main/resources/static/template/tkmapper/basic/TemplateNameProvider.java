package com.mclon.facade.service.api.templateModule;

import com.mclon.facade.service.api.common.BaseProvider;
import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateName;

import java.util.List;
import tk.mybatis.mapper.entity.Example;
import com.mclon.commons.support.webmvc.result.Result;


/**
 * @author TemplateCreate
 * @version fileUtilVersion
 * @description TemplateDesc
 * @date Created in TemplateCreateDate
 */
public interface TemplateNameProvider extends BaseProvider<ExtTemplateName> {
    /**
     * 标准查询
     *
     * @param extTemplateName
     * @return
     */
    Result<List<ExtTemplateName>> list(ExtTemplateName extTemplateName);

    /**
     * 按照条件查询
     *
     * @param example
     * @return
     */
    Result<List<ExtTemplateName>> listByCondition(Example example);
}
