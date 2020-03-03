package com.mclon.facade.service.api.templateModule.form;

import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateName;
import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateNameDetail;

import java.util.List;

import lombok.*;

/**
 * @author TemplateCreate
 * @version fileUtilVersion
 * @description TemplateDesc 表单
 * @date Created in TemplateCreateDate
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TemplateNameForm {
    private ExtTemplateName extTemplateName;
    private List<ExtTemplateNameDetail> extTemplateNameDetailList;

}
