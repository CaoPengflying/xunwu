package com.mclon.facade.service.api.templateModule.extmodel;

import com.mclon.facade.service.api.templateModule.model.TemplateName;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @description TemplateDesc 扩展类
 * @author TemplateCreate
 * @date Created in TemplateCreateDate
 * @version fileUtilVersion
 */
 @Data
 @EqualsAndHashCode(callSuper=false)
public class ExtTemplateName extends TemplateName {
	private List<Integer> idList;
	
}
