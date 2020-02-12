package com.mclon.kernel.support.templateModule.provider;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mclon.facade.service.api.utils.ModelTransformUtils;

import com.mclon.kernel.support.templateModule.handle.TemplateNameHandle;
import com.mclon.facade.service.api.framework.BusinessException;
import com.mclon.facade.service.api.common.BasePlusProviderImpl;
import com.mclon.facade.service.api.templateModule.TemplateNameProvider;
import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateName;
import com.mclon.facade.service.api.templateModule.model.TemplateName;
import com.mclon.kernel.support.templateModule.service.TemplateNameService;
import org.apache.dubbo.config.annotation.Service;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.assertj.core.util.Lists;

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
public class TemplateNameProviderImpl extends BasePlusProviderImpl<ExtTemplateName> implements TemplateNameProvider {

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
     * @param extTemplateName ExtTemplateName
     * @return
     */
    @Override
    public ExtTemplateName create(ExtTemplateName extTemplateName) {
        try {
            // 1、校验入参
            String errorMsg = TemplateNameHandle.checkCreate(extTemplateName);
            if (StringUtils.isNotEmpty(errorMsg)) {
                throw new BusinessException(errorMsg);
            }
            // 2、执行保存方法
            return templateNameService.create(extTemplateName);
        } catch (Exception e) {
            return super.handleException("TemplateDesc保存失败！", e);
        }
    }


    /**
     * 标准删除
     * 1、校验入参
     * 2、执行删除方法
     *
     * @param extTemplateName ExtTemplateName
     * @return
     */
    @Override
    public ExtTemplateName delete(ExtTemplateName extTemplateName) {
        try {
            // 1、校验入参
            String errorMsg = TemplateNameHandle.checkDelete(extTemplateName);
            if (StringUtils.isNotEmpty(errorMsg)) {
                throw new BusinessException(errorMsg);
            }
            // 2、执行删除方法
            return templateNameService.delete(extTemplateName);
        } catch (Exception e) {
            return super.handleException("TemplateDesc删除失败！", e);
        }
    }

    /**
     * 标准修改
     * 1、校验入参
     * 2、执行修改方法
     *
     * @param extTemplateName ExtTemplateName
     * @return
     */
    @Override
    public ExtTemplateName update(ExtTemplateName extTemplateName) {
        try {
            // 1、校验入参
            String errorMsg = TemplateNameHandle.checkUpdate(extTemplateName);
            if (StringUtils.isNotEmpty(errorMsg)) {
                throw new BusinessException(errorMsg);
            }
            // 2、执行修改方法
            return templateNameService.update(extTemplateName);
        } catch (Exception e) {
            return super.handleException("TemplateDesc修改失败！", e);
        }
    }

    /**
     * 标准详情
     * 1、校验入参
     * 2、执行获取详情
     *
     * @param extTemplateName ExtTemplateName
     * @return
     */
    @Override
    public ExtTemplateName get(ExtTemplateName extTemplateName) {
        try {
            // 1、校验入参
            String errorMsg = TemplateNameHandle.checkGet(extTemplateName);
            if (StringUtils.isNotEmpty(errorMsg)) {
                throw new BusinessException(errorMsg);
            }
            // 2、执行获取详情方法
            return templateNameService.get(extTemplateName);
        } catch (Exception e) {
            return super.handleException("TemplateDesc获取详情失败！", e);
        }
    }

    /**
     * 标准详情
     * 1. 执行获取列表
     *
     * @param extTemplateName
     * @return
     */
    @Override
    public List<ExtTemplateName> listByCondition(ExtTemplateName extTemplateName) {
        try {
            QueryWrapper<TemplateName> queryWrapper = new QueryWrapper<>();
            List<TemplateName> list = templateNameService.list(queryWrapper);
            return ModelTransformUtils.exchangeClassList(list, ExtTemplateName.class);
        } catch (Exception e) {
            return Lists.newArrayList(super.handleException("TemplateDesc自定义条件获取列表失败！", e));
        }
    }
}
