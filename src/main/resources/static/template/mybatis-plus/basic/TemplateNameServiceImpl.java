package com.mclon.kernel.support.templateModule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mclon.facade.service.api.framework.BusinessException;
import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateName;
import com.mclon.facade.service.api.templateModule.model.TemplateName;
import com.mclon.kernel.support.templateModule.mapper.TemplateNameMapper;
import com.mclon.kernel.support.templateModule.service.TemplateNameService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mclon.commons.support.webmvc.constants.ErrorConstant;
import com.mclon.facade.service.api.common.BasePlusServiceImpl;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;

import com.mclon.facade.service.api.utils.ModelTransformUtils;

import java.util.List;
import java.util.Map;

/**
 * @author TemplateCreate
 * @version fileUtilVersion
 * @description TemplateDesc
 * @date Created in TemplateCreateDate
 */
@Service
public class TemplateNameServiceImpl extends BasePlusServiceImpl<TemplateNameMapper, TemplateName, ExtTemplateName> implements TemplateNameService {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(TemplateNameServiceImpl.class);

    @Resource
    private TemplateNameMapper templateNameMapper;

    /**
     * 标准新增
     * 1、执行保存方法
     *
     * @param extTemplateName
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ExtTemplateName create(ExtTemplateName extTemplateName) {
        // 1、执行保存方法
        boolean saveFlag = this.save(extTemplateName);
        if (saveFlag){
            return extTemplateName;
        }else {
            return null;
        }
    }


    /**
     * 标准删除
     * 1、执行删除方法
     *
     * @param extTemplateName
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ExtTemplateName delete(ExtTemplateName extTemplateName) {
        QueryWrapper<TemplateName> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(TemplateName::getTemplateMainIdStr, extTemplateName.getIdList());
        int count = this.count(queryWrapper);
        if (count != extTemplateName.getIdList().size()){
            throw new BusinessException("数据已被更新，请刷新！", ErrorConstant.DATA_NOT_EXISTS);
        }
        boolean removeFlag = this.removeByIds(extTemplateName.getIdList());
        if (removeFlag){
            return extTemplateName;
        }else {
            throw new BusinessException("删除失败！", ErrorConstant.FAIL);
        }
    }

    /**
     * 标准修改
     * 1、执行修改方法
     *
     * @param extTemplateName
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ExtTemplateName update(ExtTemplateName extTemplateName) {
        TemplateName templateName = this.getById(extTemplateName.getTemplateMainIdStr());
        if (null == templateName) {
            return null;
        }
        boolean updateFlag = this.updateById(extTemplateName);
        if (updateFlag){
            return extTemplateName;
        }else {
            throw new BusinessException("修改失败！", ErrorConstant.FAIL);
        }
    }

    /**
     * 标准详情
     *
     * @param extTemplateName
     * @return
     */
    @Override
    public ExtTemplateName get(ExtTemplateName extTemplateName) {
        TemplateName templateName = this.getById(extTemplateName.getTemplateMainIdStr());
        if (null == templateName) {
            throw new BusinessException("数据已被更新，请刷新！", ErrorConstant.DATA_NOT_EXISTS);
        }
        return ModelTransformUtils.exchangeClass(templateName, ExtTemplateName.class);
    }

}
