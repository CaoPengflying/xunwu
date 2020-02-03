package com.mclon.bomc.support.templateModule.service.implement;

import com.mclon.bomc.support.templateModule.service.TemplateNameBizService;
import com.mclon.commons.support.webmvc.result.Result;
import com.mclon.commons.support.webmvc.constants.ErrorConstant;
import com.mclon.facade.service.api.templateModule.TemplateModuleProviderCenter;
import com.mclon.facade.service.api.templateModule.TemplateNameProvider;
import com.mclon.commons.support.webmvc.export.ImportResolve;
import org.apache.dubbo.config.annotation.Reference;
import com.mclon.facade.service.api.common.BaseBizServiceImpl;
import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author TemplateCreate
 * @version fileUtilVersion
 * @description TemplateDesc
 * @date Created in TemplateCreateDate
 */
@Service("templateNameBizService")
public class TemplateNameBizServiceImpl extends BaseBizServiceImpl<ExtTemplateName> implements TemplateNameBizService {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(TemplateNameBizServiceImpl.class);

    @Reference
    private TemplateNameProvider templateNameProvider;

    @Resource
    private ImportResolve importResolve;

    /**
     * 标准新增
     * 1、执行保存方法
     *
     * @param object ExtTemplateName
     * @return
     */
    @Override
    public Result<ExtTemplateName> create(Object object) {
        try {
            // 1、执行保存方法
            ExtTemplateName extTemplateName = (ExtTemplateName) object;
            return ErrorConstant.getSuccessResult(templateNameProvider.create(extTemplateName),"保存成功");
        } catch (Exception e) {
            return super.handleException("TemplateDesc保存失败!", TemplateModuleProviderCenter.descMap, TemplateModuleProviderCenter.TemplateModuleProviderEnum.TEMPLATENAMEPROVIDER.getCode(), e);
        }
    }

    /**
     * 标准删除
     * 1、执行删除方法
     *
     * @param object ExtTemplateName
     * @return
     */
    @Override
    public Result<ExtTemplateName> delete(Object object) {
        try {
            // 1、执行删除方法
            ExtTemplateName extTemplateName = (ExtTemplateName) object;
            return ErrorConstant.getSuccessResult(templateNameProvider.delete(extTemplateName),"删除成功");
        } catch (Exception e) {
            return super.handleException("TemplateDesc删除失败!", TemplateModuleProviderCenter.descMap, TemplateModuleProviderCenter.TemplateModuleProviderEnum.TEMPLATENAMEPROVIDER.getCode(), e);
        }
    }

    /**
     * 标准修改
     * 1、执行修改方法
     *
     * @param object ExtTemplateName
     * @return
     */
    @Override
    public Result<ExtTemplateName> update(Object object) {
        try {
            //1、执行修改方法
            ExtTemplateName extTemplateName = (ExtTemplateName) object;
            return ErrorConstant.getSuccessResult(templateNameProvider.update(extTemplateName),"修改成功");
        } catch (Exception e) {
            return super.handleException("TemplateDesc修改失败!", TemplateModuleProviderCenter.descMap, TemplateModuleProviderCenter.TemplateModuleProviderEnum.TEMPLATENAMEPROVIDER.getCode(), e);
        }
    }

    /**
     * 标准获取详情
     *
     * @param object ExtTemplateName
     * @return
     */
    @Override
    public Result get(Object object) {
        try {
            ExtTemplateName extTemplateName = (ExtTemplateName) object;
            return ErrorConstant.getSuccessResult(templateNameProvider.get(extTemplateName),"获取详情成功");
        } catch (Exception e) {
            return super.handleException("TemplateDesc获取详情!", TemplateModuleProviderCenter.descMap, TemplateModuleProviderCenter.TemplateModuleProviderEnum.TEMPLATENAMEPROVIDER.getCode(), e);
        }
    }

}
