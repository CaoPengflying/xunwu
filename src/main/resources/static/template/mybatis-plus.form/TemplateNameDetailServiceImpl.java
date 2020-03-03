package com.mclon.kernel.support.templateModule.service.implement;

import com.mclon.facade.service.api.framework.BusinessException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mclon.facade.service.api.templateModule.constants.TemplateNameConstants;
import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateName;
import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateNameDetail;
import com.mclon.facade.service.api.templateModule.form.TemplateNameForm;
import com.mclon.facade.service.api.templateModule.model.TemplateName;
import com.mclon.facade.service.api.templateModule.model.TemplateNameDetail;
import com.mclon.kernel.support.templateModule.repository.templateNameDetailService;
import com.mclon.kernel.support.templateModule.repository.TemplateNameMapper;
import com.mclon.kernel.support.templateModule.service.TemplateNameService;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mclon.commons.support.webmvc.result.Result;
import com.mclon.commons.support.webmvc.constants.ErrorConstant;
import com.mclon.facade.service.api.common.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import tk.mybatis.mapper.entity.Example;
import com.mclon.facade.service.api.utils.ModelTransformUtils;
import java.util.List;


/**
 * @description TemplateDesc
 * @author TemplateCreate
 * @date Created in TemplateCreateDate
 * @version fileUtilVersion
 */
 @Service
public class TemplateNameDetailServiceImpl  extends BasePlusServiceImpl<TemplateNameDetailMapper, TemplateNameDetail, ExtTemplateNameDetail> implements TemplateNameDetailService {
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(TemplateNameDetailServiceImpl.class);

}
