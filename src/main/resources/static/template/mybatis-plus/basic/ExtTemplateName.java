package com.mclon.facade.service.api.templateModule.extmodel;

import com.mclon.facade.service.api.templateModule.model.TemplateName;

import java.util.List;

import lombok.*;

/**
 * @author TemplateCreate
 * @version fileUtilVersion
 * @description TemplateDesc 扩展类
 * @date Created in TemplateCreateDate
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExtTemplateName extends TemplateName {
    private List<Integer> idList;
    /**
     * 导入错误错误信息
     */
    private String importErrorMsg;

}