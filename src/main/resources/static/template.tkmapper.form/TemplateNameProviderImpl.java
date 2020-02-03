package com.mclon.kernel.support.templateModule.provider;


import com.mclon.commons.support.webmvc.constants.ErrorConstant;
import com.mclon.commons.support.webmvc.result.Result;
com.mclon.facade.service.api.framework.BusinessException;
import com.mclon.facade.service.api.templateModule.TemplateNameProvider;
import com.mclon.facade.service.api.common.BaseProviderImpl;
import com.mclon.kernel.support.templateModule.handle.TemplateNameFormHandle;
import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateName;
import com.mclon.facade.service.api.templateModule.model.TemplateName;
import com.mclon.facade.service.api.templateModule.form.TemplateNameForm;
import com.mclon.kernel.support.templateModule.service.TemplateNameService;
import com.mclon.facade.service.api.templateModule.constants.TemplateNameConstants;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import tk.mybatis.mapper.entity.Example;
import org.apache.commons.collections4.CollectionUtils;
import javax.annotation.Resource;

/**
 * @description TemplateDesc
 * @author TemplateCreate
 * @date Created in TemplateCreateDate
 * @version fileUtilVersion
 */
@Service
public class TemplateNameProviderImpl extends BaseProviderImpl<TemplateNameForm> implements TemplateNameProvider {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(TemplateNameProviderImpl.class);

    @Resource
    private TemplateNameService templateNameService;

    /**
     * 标准新增
     * 1、校验入参
     * 2、执行保存方法
     * @param object TemplateNameForm
     * @return
     */
    @Override
    public Result<TemplateNameForm> create(Object object) {
        try {
            Result<TemplateNameForm> result = new Result<>();
            TemplateNameForm templateNameForm = (TemplateNameForm) object;
            // 1、校验入参
            Result checkResult = TemplateNameFormHandle.checkFormCreate(templateNameForm);
            result.timeDesc("入参检测");
            if (ErrorConstant.checkSuccess(checkResult)) {
                return checkResult;
            }
            // 2、执行保存方法
            Result<TemplateNameForm> providerResult = templateNameService.create(templateNameForm);
            providerResult.headDesc(result, "保存服务");
            return providerResult;
        } catch (Exception e) {    
            return super.handleException("templateModule保存失败！", e);
        }
    }
	

    /**
     * 标准删除
     * 1、校验入参
     * 2、执行删除方法
     * @param object ExtTemplateName
     * @return
     */
    @Override
    public Result<TemplateNameForm> delete(Object object) {
        try {
            Result<TemplateNameForm> result = new Result<>();
            ExtTemplateName extTemplateName = (ExtTemplateName) object;
            // 1、校验入参
            Result checkResult = TemplateNameFormHandle.checkDelete(extTemplateName);
            result.timeDesc("入参检测");
            if (ErrorConstant.checkSuccess(checkResult)) {
                return checkResult;
            }
            // 2、执行删除方法
            Result<TemplateNameForm> providerResult = templateNameService.delete(extTemplateName);
            providerResult.headDesc(result, "删除服务");
            return providerResult;
        } catch (Exception e) {    
            return super.handleException("templateModule删除失败！", e);
        }
    }

    /**
     * 标准修改
     * 1、校验入参
     * 2、执行修改方法
     * @param object TemplateNameForm
     * @return
     */
    @Override
    public Result<TemplateNameForm> update(Object object) {
        try {
            Result<TemplateNameForm> result = new Result<>();
            TemplateNameForm templateNameForm = (TemplateNameForm) object;
            // 1、校验入参
            Result checkResult = TemplateNameFormHandle.checkFormUpdate(templateNameForm);
            result.timeDesc("入参检测");
            if (ErrorConstant.checkSuccess(checkResult)) {
                return checkResult;
            }
            // 2、执行修改方法
            Result<TemplateNameForm> providerResult = templateNameService.update(templateNameForm);
            providerResult.headDesc(result, "修改服务");
            return providerResult;
        } catch (Exception e) {
            return super.handleException("templateModule修改失败！", e);
        }
    }

    /**
     * 标准详情
     * 1、校验入参
     * 2、执行获取详情
     * @param object ExtTemplateName
     * @return
     */
    @Override
    public Result<TemplateNameForm> get(Object object) {
        try {
            Result<TemplateNameForm> result = new Result<>();
            ExtTemplateName extTemplateName = (ExtTemplateName) object;
            // 1、校验入参
            Result checkResult = TemplateNameFormHandle.checkGet(extTemplateName);
            result.timeDesc("入参检测");
            if (ErrorConstant.checkSuccess(checkResult)) {
                return checkResult;
            }
            // 2、执行获取详情
            Result<TemplateNameForm> providerResult = templateNameService.get(extTemplateName);
            providerResult.headDesc(result, "获取详情服务");
            return providerResult;
        } catch (Exception e) {
            return super.handleException("templateModule获取详情失败！", e);
        }
    }
	
	    /**
     * 标准查询
     * 1、执行获取列表
     * @param object ExtTemplateName
     * @return
     */
     @Override
    public Result<List<ExtTemplateName>> list(Object object) {
        try {
            ExtTemplateName extTemplateName = (ExtTemplateName) object;
			Example example = new Example(TemplateName.class);
			Example.Criteria criteria = example.createCriteria();
			if (CollectionUtils.isNotEmpty(extTemplateName.getIdList())){
				criteria.andIn(TemplateNameConstants.MAIN_ID_STR,extTemplateName.getIdList());
			}
            // 1、执行获取列表方法
            Result<List<ExtTemplateName>> providerResult = templateNameService.listByCondition(example);
            providerResult.timeDesc("获取列表服务");
            return providerResult;
        } catch (Exception e) { 
           return super.handleException("templateModule获取列表失败！", e);
        }
    }
	
	    /**
     * 标准详情
     * 1. 执行获取列表
     * @param object Example
     * @return
     */
     @Override
    public Result<List<ExtTemplateName>> listByCondition(Object object) {
        try {
            Example example = (Example) object; 
			// 1、执行获取列表
            Result<List<ExtTemplateName>> providerResult = templateNameService.listByCondition(example);
            providerResult.timeDesc("自定义条件获取列表服务");
            return providerResult;
        } catch (Exception e) {
            return super.handleException("templateModule自定义条件获取列表！", e);
        }
    }
}
