package com.mclon.kernel.support.templateModule.service.implement;

com.mclon.facade.service.api.framework.BusinessException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mclon.facade.service.api.templateModule.constants.TemplateNameConstants;
import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateName;
import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateNameDetail;
import com.mclon.facade.service.api.templateModule.form.TemplateNameForm;
import com.mclon.facade.service.api.templateModule.model.TemplateName;
import com.mclon.facade.service.api.templateModule.model.TemplateNameDetail;
import com.mclon.kernel.support.templateModule.repository.TemplateNameDetailService;
import com.mclon.kernel.support.templateModule.repository.templateNameService;
import com.mclon.kernel.support.templateModule.service.TemplateNameService;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
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


/**
 * @description TemplateDesc
 * @author TemplateCreate
 * @date Created in TemplateCreateDate
 * @version fileUtilVersion
 */
 @Service
public class TemplateNameServiceImpl extends BasePlusServiceImpl<TemplateNameMapper, TemplateName, TemplateNameForm> implements TemplateNameService {
	/**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(TemplateNameServiceImpl.class);
    
	@Resource
	private TemplateNameDetailService templateNameDetailService;

    /**
     * 标准新增
     * 1、插入主表
	 * 2、插入明细表
     * @param object TemplateNameForm
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TemplateNameForm create(Object object) {
		TemplateNameForm templateNameForm = (TemplateNameForm) object;
		ExtTemplateName extTemplateName = templateNameForm.getExtTemplateName();
		List<ExtTemplateNameDetail> extTemplateNameDetailList = templateNameForm.getExtTemplateNameDetailList();
		// 1、执行保存方法
		boolean saveFlag = this.save(extTemplateName);
		if(saveFlag){
			for(ExtTemplateNameDetail extTemplateNameDetail : extTemplateNameDetailList){
				extTemplateNameDetail.setTemplateMainIdStr(extTemplateName.getTemplateMainIdStr());
			}
			List<TemplateNameDetail> templateNameDetailList = ModelTransformUtils.exchangeClassList(extTemplateNameDetailList,TemplateNameDetail.class);
			int i = TemplateNameDetailService.saveBatch(templateNameDetailList);
			if(0 >= i){
				throw new BusinessException("明细行保存失败");
			}
		}else{
			throw new BusinessException("明细行保存失败");
		}
		return templateNameForm;
    }
	

    /**
     * 标准删除
     * 1、删除主表
	 * 2、删除明细表
     * @param object ExtTemplateName
     * @return
     */
    @Override
	@Transactional(rollbackFor = Exception.class)
    public TemplateNameForm delete(Object object) {
		ExtTemplateName extTemplateName = (ExtTemplateName) object;
		TemplateNameForm templateNameForm = new TemplateNameForm();
		// 1、删除主表
		QueryWrapper<TemplateName> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().in(TemplateName::getTemplateMainIdStr, extTemplateName.getIdList());
		int count = this.count(queryWrapper);
		if (count != extTemplateName.getIdList().size()){
			throw new BusinessException("数据已被更新，请刷新！", ErrorConstant.DATA_NOT_EXISTS);
		}
		boolean removeFlag = this.removeByIds(extTemplateName.getIdList());
		if(removeFlag){
			//2、删除明细表
			QueryWrapper<TemplateNameDetail> detailQueryWrapper = new QueryWrapper<>();
			detailQueryWrapper.lambda().in(TemplateNameDetail::getTemplateMainIdStr, extTemplateName.getIdList());
			int i = TemplateNameDetailService.remove(detailQueryWrapper);
			if(0 >= i){
				throw new BusinessException("明细行删除失败");
			}
			templateNameForm.setExtTemplateName(extTemplateName);
		}else {
			throw new BusinessException("明细行删除失败");
		}
		return templateNameForm;
    }

    /**
     * 标准修改
     * 1、执行修改方法
     * @param object TemplateNameForm
     * @return
     */
    @Override
	@Transactional(rollbackFor = Exception.class)
    public TemplateNameForm update(Object object) {
		TemplateNameForm templateNameForm = (TemplateNameForm) object;
		ExtTemplateName extTemplateName = templateNameForm.getExtTemplateName();
		TemplateName templateName = this.getById(extTemplateName.getTemplateMainIdStr());
		if(null == templateName){
			throw new BusinessException("数据已被更新，请刷新！", ErrorConstant.DATA_NOT_EXISTS);
		}
		// 其他判断
		// 1、执行修改方法
		boolean updateFlag = this.updateById(extTemplateName);
		if(!updateFlag){
			throw new BusinessException("修改失败", ErrorConstant.FAIL);
		}
		return templateNameForm;
    }

    /**
     * 标准详情
	 * 1、查询主表
	 * 2、根据主表查询明细表
     * @param object ExtTemplateName
     * @return
     */
    @Override
    public TemplateNameForm get(Object object) {
		ExtTemplateName extTemplateName = (ExtTemplateName) object;
		//1、查询主表
		TemplateName templateName = this.getById(extTemplateName.getTemplateMainIdStr());
		if(null == templateName){
			throw new BusinessException("数据已被更新，请刷新！", ErrorConstant.DATA_NOT_EXISTS);
		}
		//2、查询明细表
		QueryWrapper<TemplateNameDetail> detailQueryWrapper = new QueryWrapper<>();
		detailQueryWrapper.lambda().eq(TemplateNameDetail::getTemplateMainIdStr, extTemplateName.getTemplateMainIdStr());
		List<TemplateNameDetail> templateNameDetailList = TemplateNameDetailService.list(detailQueryWrapper);
		if(CollectionUtils.isEmpty(templateNameDetailList)){
			return ErrorConstant.getErrorResult(ErrorConstant.DATA_NOT_EXISTS,"该数据明细不存在");
		}
		extTemplateName =  ModelTransformUtils.exchangeClass(templateName, ExtTemplateName.class);
		List<ExtTemplateNameDetail> extTemplateNameDetailList = ModelTransformUtils.exchangeClassList(templateNameDetailList, ExtTemplateNameDetail.class);
		TemplateNameForm templateNameForm = new TemplateNameForm();
		templateNameForm.setExtTemplateName(extTemplateName);
		templateNameForm.setExtTemplateNameDetailList(extTemplateNameDetailList);
		return TemplateNameForm;
    }
}
