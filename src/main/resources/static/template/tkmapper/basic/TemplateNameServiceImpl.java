package com.mclon.kernel.support.templateModule.service.impl;

import com.mclon.facade.service.api.common.BaseModel;
import com.mclon.facade.service.api.templateModule.constants.TemplateNameConstants;
import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateName;
import com.mclon.kernel.support.templateModule.handle.TemplateNameHandle;
import com.mclon.facade.service.api.templateModule.model.TemplateName;
import com.mclon.kernel.support.templateModule.repository.TemplateNameMapper;
import com.mclon.kernel.support.templateModule.service.TemplateNameService;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mclon.commons.support.webmvc.result.Result;
import com.mclon.commons.support.webmvc.constants.ErrorConstant;
import com.mclon.facade.service.api.common.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;

import tk.mybatis.mapper.entity.Example;
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
public class TemplateNameServiceImpl extends BaseServiceImpl<ExtTemplateName> implements TemplateNameService {
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
     * @param object
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<ExtTemplateName> create(Object object) {
        Result<ExtTemplateName> result = new Result<>();
        ExtTemplateName extTemplateName = (ExtTemplateName) object;
        // 1、执行保存方法
        int i = templateNameMapper.insert(extTemplateName);
        if (0 < i) {
            result.setText("保存成功");
            result.setT(extTemplateName);
        } else {
            result.setText("保存失败");
            result.setStatus(ErrorConstant.FAIL);
        }
        return result;
    }


    /**
     * 标准删除
     * 1、执行删除方法
     *
     * @param object
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<ExtTemplateName> delete(Object object) {
        Result<ExtTemplateName> result = new Result<>();
        ExtTemplateName extTemplateName = (ExtTemplateName) object;
        // 1、执行删除方法
        Example example = new Example(TemplateName.class);
        example.createCriteria().andIn(TemplateNameConstants.MAIN_ID_STR, extTemplateName.getIdList());
        int count = templateNameMapper.selectCountByExample(example);
        if (count != extTemplateName.getIdList().size()){
            return ErrorConstant.getErrorResult(ErrorConstant.DATA_NOT_EXISTS, "数据已被更新，请刷新！");
        }
        int i = templateNameMapper.deleteByExample(example);
        if (0 < i) {
            result.setText("删除成功");
            result.setT(extTemplateName);
        } else {
            result.setText("删除失败");
            result.setStatus(ErrorConstant.FAIL);
        }
        return result;
    }

    /**
     * 标准修改
     * 1、执行修改方法
     *
     * @param object
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<ExtTemplateName> update(Object object) {
        Result<ExtTemplateName> result = new Result<>();
        ExtTemplateName extTemplateName = (ExtTemplateName) object;
        TemplateName templateName = templateNameMapper.selectByPrimaryKey(extTemplateName.getTemplateMainIdStr());
        if (null == templateName) {
            return ErrorConstant.getErrorResult(ErrorConstant.DATA_NOT_EXISTS, "该数据不存在");
        }
        // 其他判断
        // 1、执行修改方法
        int i = templateNameMapper.updateByPrimaryKeySelective(extTemplateName);
        if (0 < i) {
            result.setText("修改成功");
            result.setT(extTemplateName);
        } else {
            result.setText("修改失败");
            result.setStatus(ErrorConstant.FAIL);
        }
        return result;
    }

    /**
     * 标准详情
     *
     * @param object
     * @return
     */
    @Override
    public Result<ExtTemplateName> get(Object object) {
        Result<ExtTemplateName> result = new Result<>();
        ExtTemplateName extTemplateName = (ExtTemplateName) object;
        TemplateName templateName = templateNameMapper.selectByPrimaryKey(extTemplateName.getTemplateMainIdStr());
        if (null == templateName) {
            return ErrorConstant.getErrorResult(ErrorConstant.DATA_NOT_EXISTS, "该数据不存在");
        }
        extTemplateName = ModelTransformUtils.exchangeClass(templateName, ExtTemplateName.class);
        result.setT(extTemplateName);
        return result;
    }

    /**
     * 根据条件查询
     *
     * @param example
     * @return
     */
    @Override
    public Result<List<ExtTemplateName>> listByCondition(Example example) {
        Result<List<ExtTemplateName>> result = new Result<>();
        List<TemplateName> templateNameList = templateNameMapper.selectByExample(example);
        List<ExtTemplateName> extTemplateNameList = ModelTransformUtils.exchangeClassList(templateNameList, ExtTemplateName.class);
        result.setT(extTemplateNameList);
        return result;
    }

    /**
     * 标准导入数据
     *
     * @param baseModel 基础模型，存储创建人、创建组织
     * @param list      待导入的数据
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result importData(BaseModel baseModel, List<Map<String, String>> list) {
        Result result = new Result();
        //组装数据
        List<ExtTemplateName> extTemplateNameList = TemplateNameHandle.parseImportData(baseModel, list);
        long errorCount = extTemplateNameList.stream().filter(x -> StringUtils.isNotEmpty(x.getImportErrorMsg())).count();
        if (errorCount > 0) {
            result.setT(extTemplateNameList);
            result.setStatus(ErrorConstant.IMPORT_DATA_CODE);
            return result;
        }
        List<TemplateName> templateNameList = ModelTransformUtils.exchangeClassList(extTemplateNameList, TemplateName.class);
        templateNameMapper.insertList(templateNameList);
        return result;

    }
}
