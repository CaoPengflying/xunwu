package com.mclon.facade.service.api.templateModule.form;

import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateName;
import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateNameDetail;
import java.util.List;
import lombok.Data;

/**
 * @description TemplateDesc 表单
 * @author TemplateCreate
 * @date Created in TemplateCreateDate
 * @version fileUtilVersion
 */
 @Data
public class TemplateNameForm{
	private ExtTemplateName extTemplateName;
	private List<ExtTemplateNameDetail> extTemplateNameDetailList;

}
