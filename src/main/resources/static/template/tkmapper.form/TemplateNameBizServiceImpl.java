package com.mclon.bomc.support.templateModule.service.implement;

import com.mclon.bomc.support.templateModule.service.TemplateNameBizService;
import com.mclon.commons.support.webmvc.result.Result;
import com.mclon.facade.service.api.templateModule.TemplateModuleProviderCenter;
import com.mclon.facade.service.api.templateModule.TemplateNameProvider;
import com.mclon.facade.service.api.common.BaseBizServiceImpl;
import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateName;
import com.mclon.facade.service.api.templateModule.form.TemplateNameForm;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @description TemplateDesc
 * @author TemplateCreate
 * @date Created in TemplateCreateDate
 * @version fileUtilVersion
 */
@Service("templateNameBizService")
public class TemplateNameBizServiceImpl extends BaseBizServiceImpl<TemplateNameForm>  implements TemplateNameBizService {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(TemplateNameBizServiceImpl.class);

    @Reference
    private TemplateNameProvider templateNameProvider;

    /**
     * 标准新增
     * 1、执行保存方法
     * @param object TemplateNameForm
     * @return
     */
    @Override
    public Result<TemplateNameForm> create(Object object) {
        try {
            // 1、执行保存方法
			TemplateNameForm templateNameForm = (TemplateNameForm)object;
            return templateNameProvider.create(templateNameForm);
        } catch (Exception e) {
            return super.handleException("TemplateDesc保存失败!",TemplateModuleProviderCenter.descMap, TemplateModuleProviderCenter.TemplateModuleProviderEnum.TEMPLATENAMEPROVIDER.getCode(), e);
        }
    }
	
    /**
     * 标准删除
     * 1、执行删除方法
     * @param object ExtTemplateName
     * @return
     */
    @Override
    public Result<TemplateNameForm> delete(Object object) {
        try {
            // 1、执行删除方法
			ExtTemplateName extTemplateName = (ExtTemplateName)object;
            return templateNameProvider.delete(extTemplateName);
        } catch (Exception e) {
            return super.handleException("TemplateDesc删除失败!",TemplateModuleProviderCenter.descMap, TemplateModuleProviderCenter.TemplateModuleProviderEnum.TEMPLATENAMEPROVIDER.getCode(), e);
        }
    }

    /**
     * 标准修改
     * 1、执行修改方法
     * @param object TemplateNameForm
     * @return
     */
    @Override
    public Result<TemplateNameForm> update(Object object) {
        try {
			//1、执行修改方法
			TemplateNameForm templateNameForm = (TemplateNameForm)object;
            return templateNameProvider.update(templateNameForm);
        } catch (Exception e) {
            return super.handleException("TemplateDesc修改失败!",TemplateModuleProviderCenter.descMap, TemplateModuleProviderCenter.TemplateModuleProviderEnum.TEMPLATENAMEPROVIDER.getCode(), e);
        }
    }

    /**
     * 标准获取详情
     * @param object ExtTemplateName
     * @return
     */
    @Override
    public Result<TemplateNameForm> get(Object object) {
        try {
			ExtTemplateName extTemplateName = (ExtTemplateName)object;
            return templateNameProvider.get(extTemplateName);
        } catch (Exception e) {
            return super.handleException("TemplateDesc获取详情!",TemplateModuleProviderCenter.descMap, TemplateModuleProviderCenter.TemplateModuleProviderEnum.TEMPLATENAMEPROVIDER.getCode(), e);
        }
    }
}
