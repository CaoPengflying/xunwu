package com.mclon.kernel.support.templateModule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mclon.facade.service.api.templateModule.model.TemplateName;
import com.mclon.kernel.support.templateModule.repository.MyBatisRepository;

/**
 * @author TemplateCreate
 * @version fileUtilVersion
 * @description TemplateDesc mapper
 * @date Created in TemplateCreateDate
 */
@MyBatisRepository
public interface TemplateNameMapper extends BaseMapper<TemplateName> {

}
