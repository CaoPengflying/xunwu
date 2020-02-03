package com.mclon.kernel.support.templateModule.provider;

import com.mclon.commons.support.webmvc.constants.ErrorConstant;
import com.mclon.commons.support.webmvc.result.Result;
import com.mclon.kernel.support.templateModule.handle.TemplateNameHandle;
import com.mclon.facade.service.api.common.BaseModel;
import com.mclon.facade.service.api.common.BaseProviderImpl;
import com.mclon.facade.service.api.templateModule.TemplateNameProvider;
import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateName;
import com.mclon.facade.service.api.templateModule.model.TemplateName;
import com.mclon.kernel.support.templateModule.service.TemplateNameService;
import com.mclon.facade.service.api.templateModule.constants.TemplateNameConstants;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import tk.mybatis.mapper.entity.Example;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.Resource;

import java.util.Map;


/**
 * @author TemplateCreate
 * @version fileUtilVersion
 * @description TemplateDesc
 * @date Created in TemplateCreateDate
 */
@Service
public class TemplateNameProviderImpl extends BaseProviderImpl<ExtTemplateName> implements TemplateNameProvider {

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
     *
     * @param object ExtTemplateName
     * @return
     */
    @Override
    public Result<ExtTemplateName> create(Object object) {
        try {
            Result<ExtTemplateName> result = new Result<>();
            ExtTemplateName extTemplateName = (ExtTemplateName) object;
            // 1、校验入参
            Result checkResult = TemplateNameHandle.checkCreate(extTemplateName);
            result.timeDesc("入参检测");
            if (ErrorConstant.checkSuccess(checkResult)) {
                return checkResult;
            }
            // 2、执行保存方法
            Result<ExtTemplateName> providerResult = templateNameService.create(extTemplateName);
            providerResult.headDesc(result, "保存服务");
            return providerResult;
        } catch (Exception e) {
            return super.handleException("TemplateDesc保存失败！", e);
        }
    }


    /**
     * 标准删除
     * 1、校验入参
     * 2、执行删除方法
     *
     * @param object ExtTemplateName
     * @return
     */
    @Override
    public Result<ExtTemplateName> delete(Object object) {
        try {
            Result<ExtTemplateName> result = new Result<>();
            ExtTemplateName extTemplateName = (ExtTemplateName) object;
            // 1、校验入参
            Result checkResult = TemplateNameHandle.checkDelete(extTemplateName);
            result.timeDesc("入参检测");
            if (ErrorConstant.checkSuccess(checkResult)) {
                return checkResult;
            }
            // 2、执行删除方法
            Result<ExtTemplateName> providerResult = templateNameService.delete(extTemplateName);
            providerResult.headDesc(result, "删除服务");
            return providerResult;
        } catch (Exception e) {
            return super.handleException("TemplateDesc删除失败！", e);
        }
    }

    /**
     * 标准修改
     * 1、校验入参
     * 2、执行修改方法
     *
     * @param object ExtTemplateName
     * @return
     */
    @Override
    public Result<ExtTemplateName> update(Object object) {
        try {
            Result<ExtTemplateName> result = new Result<>();
            ExtTemplateName extTemplateName = (ExtTemplateName) object;
            // 1、校验入参
            Result checkResult = TemplateNameHandle.checkUpdate(extTemplateName);
            result.timeDesc("入参检测");
            if (ErrorConstant.checkSuccess(checkResult)) {
                return checkResult;
            }
            // 2、执行修改方法
            Result<ExtTemplateName> providerResult = templateNameService.update(extTemplateName);
            providerResult.headDesc(result, "修改服务");
            return providerResult;
        } catch (Exception e) {
            return super.handleException("TemplateDesc修改失败！", e);
        }
    }

    /**
     * 标准详情
     * 1、校验入参
     * 2、执行获取详情
     *
     * @param object ExtTemplateName
     * @return
     */
    @Override
    public Result<ExtTemplateName> get(Object object) {
        try {
            Result<ExtTemplateName> result = new Result<>();
            ExtTemplateName extTemplateName = (ExtTemplateName) object;
            // 1、校验入参
            Result checkResult = TemplateNameHandle.checkGet(extTemplateName);
            result.timeDesc("入参检测");
            if (ErrorConstant.checkSuccess(checkResult)) {
                return checkResult;
            }
            // 2、执行获取详情方法
            Result<ExtTemplateName> providerResult = templateNameService.get(extTemplateName);
            providerResult.headDesc(result, "获取详情服务");
            return providerResult;
        } catch (Exception e) {
            return super.handleException("TemplateDesc获取详情失败！", e);
        }
    }

    /**
     * 标准查询
     * 1、执行获取列表
     *
     * @param extTemplateName
     * @return
     */
    @Override
    public Result<List<ExtTemplateName>> list(ExtTemplateName extTemplateName) {
        try {
            Example example = new Example(TemplateName.class);
            Example.Criteria criteria = example.createCriteria();
            if (CollectionUtils.isNotEmpty(extTemplateName.getIdList())) {
                criteria.andIn(TemplateNameConstants.MAIN_ID_STR, extTemplateName.getIdList());
            }
            // 1、执行获取列表方法
            Result<List<ExtTemplateName>> providerResult = templateNameService.listByCondition(example);
            providerResult.timeDesc("获取列表服务");
            return providerResult;
        } catch (Exception e) {
            return super.handleException("TemplateDesc获取列表失败！", e);
        }
    }

    /**
     * 标准详情
     * 1. 执行获取列表
     *
     * @param example
     * @return
     */
    @Override
    public Result<List<ExtTemplateName>> listByCondition(Example example) {
        try {
            // 1、执行获取列表
            Result<List<ExtTemplateName>> providerResult = templateNameService.listByCondition(example);
            providerResult.timeDesc("自定义条件获取列表服务");
            return providerResult;
        } catch (Exception e) {
            return super.handleException("TemplateDesc自定义条件获取列表失败！", e);
        }
    }


    /**
     * 标准导入数据
     *
     * @param baseModel 基础模型，存储创建人、创建组织
     * @param list      待导入的数据
     * @return
     */
    @Override
    public Result importData(BaseModel baseModel, List<Map<String, String>> list) {
        try {
            return templateNameService.importData(baseModel, list);
        } catch (Exception e) {
            return handleException("TemplateDesc导入失败", e);
        }
    }
}
