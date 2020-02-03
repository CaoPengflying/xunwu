package com.mclon.kernel.support.templateModule.service.implement;

com.mclon.facade.service.api.framework.BusinessException;
import com.mclon.facade.service.api.templateModule.constants.TemplateNameConstants;
import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateName;
import com.mclon.facade.service.api.templateModule.extmodel.ExtTemplateNameDetail;
import com.mclon.facade.service.api.templateModule.form.TemplateNameForm;
import com.mclon.facade.service.api.templateModule.model.TemplateName;
import com.mclon.facade.service.api.templateModule.model.TemplateNameDetail;
import com.mclon.kernel.support.templateModule.repository.TemplateNameDetailMapper;
import com.mclon.kernel.support.templateModule.repository.TemplateNameMapper;
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
public class TemplateNameServiceImpl extends BaseServiceImpl<TemplateNameForm> implements TemplateNameService {
	/**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(TemplateNameServiceImpl.class);

    @Resource
    private TemplateNameMapper templateNameMapper;
	@Resource
	private TemplateNameDetailMapper templateNameDetailMapper;

    /**
     * 标准新增
     * 1、插入主表
	 * 2、插入明细表
     * @param object TemplateNameForm
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<TemplateNameForm> create(Object object) {
		Result<TemplateNameForm> result = new Result<>();
		TemplateNameForm templateNameForm = (TemplateNameForm) object;
		ExtTemplateName extTemplateName = templateNameForm.getExtTemplateName();
		List<ExtTemplateNameDetail> extTemplateNameDetailList = templateNameForm.getExtTemplateNameDetailList();
		// 1、执行保存方法
		int i = templateNameMapper.insert(extTemplateName);
		if(0 < i){
			for(ExtTemplateNameDetail extTemplateNameDetail : extTemplateNameDetailList){
				extTemplateNameDetail.setTemplateMainIdStr(extTemplateName.getTemplateMainIdStr());
			}
			List<TemplateNameDetail> templateNameDetailList = ModelTransformUtils.exchangeClassList(extTemplateNameDetailList,TemplateNameDetail.class);
			i = templateNameDetailMapper.insertList(templateNameDetailList);
			if(0 >= i){
				throw new BusinessException("明细行保存失败");
			}
			result.setText("保存成功");
			result.setT(templateNameForm);
		}else{
			result.setText("主表保存失败");
			result.setStatus(ErrorConstant.FAIL);		
		}
		return result;
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
    public Result<TemplateNameForm> delete(Object object) {
		Result<TemplateNameForm> result = new Result<>();
		ExtTemplateName extTemplateName = (ExtTemplateName) object;
		TemplateNameForm templateNameForm = new TemplateNameForm();
		// 1、删除主表
		Example example = new Example(TemplateName.class);
		example.createCriteria().andIn(TemplateNameConstants.MAIN_ID_STR,extTemplateName.getIdList());
		int i = templateNameMapper.deleteByExample(example);
		int count = templateNameMapper.selectCountByExample(example);
        if (count != extTemplateName.getIdList().size()){
            return ErrorConstant.getErrorResult(ErrorConstant.DATA_NOT_EXISTS, "数据已被更新，请刷新！");
        }
		if(0 < i){
			//2、删除明细表
			example = new Example(TemplateNameDetail.class);
			example.createCriteria().andIn(TemplateNameConstants.MAIN_ID_STR,extTemplateName.getIdList());
			i = templateNameDetailMapper.deleteByExample(example);
			if(0 >= i){
				throw new BusinessException("明细行删除失败");
			}
			templateNameForm.setExtTemplateName(extTemplateName);
			result.setText("删除成功");
			result.setT(templateNameForm);
		}else{
			result.setText("删除失败");
			result.setStatus(ErrorConstant.FAIL);		
		}
		return result;
    }

    /**
     * 标准修改
     * 1、执行修改方法
     * @param object TemplateNameForm
     * @return
     */
    @Override
	@Transactional(rollbackFor = Exception.class)
    public Result<TemplateNameForm> update(Object object) {
		Result<TemplateNameForm> result = new Result<>();
		TemplateNameForm templateNameForm = (TemplateNameForm) object;
		ExtTemplateName extTemplateName = templateNameForm.getExtTemplateName();
		TemplateName templateName = templateNameMapper.selectByPrimaryKey(extTemplateName.getTemplateMainIdStr());
		if(null == templateName){
			return ErrorConstant.getErrorResult(ErrorConstant.DATA_NOT_EXISTS,"该数据不存在");
		}
		// 其他判断
		// 1、执行修改方法
		int i = templateNameMapper.updateByPrimaryKeySelective(extTemplateName);
		if(0 < i){
			result.setText("修改成功");
			result.setT(templateNameForm);
		}else{
			result.setText("修改失败");
			result.setStatus(ErrorConstant.FAIL);		
		}
		return result;
    }

    /**
     * 标准详情
	 * 1、查询主表
	 * 2、根据主表查询明细表
     * @param object ExtTemplateName
     * @return
     */
    @Override
    public Result<TemplateNameForm> get(Object object) {   
		Result<TemplateNameForm> result = new Result<>();
		ExtTemplateName extTemplateName = (ExtTemplateName) object;
		//1、查询主表
		TemplateName templateName = templateNameMapper.selectByPrimaryKey(extTemplateName.getTemplateMainIdStr());
		if(null == templateName){
			return ErrorConstant.getErrorResult(ErrorConstant.DATA_NOT_EXISTS,"该数据不存在");
		}
		//2、查询明细表
		Example example = new Example(TemplateNameDetail.class);
		example.createCriteria().andEqualTo(TemplateNameConstants.MAIN_ID_STR,templateName.getTemplateMainIdStr());
		List<TemplateNameDetail> templateNameDetailList = templateNameDetailMapper.selectByExample(example);
		if(CollectionUtils.isEmpty(templateNameDetailList)){
			return ErrorConstant.getErrorResult(ErrorConstant.DATA_NOT_EXISTS,"该数据明细不存在");
		}
		extTemplateName =  ModelTransformUtils.exchangeClass(templateName, ExtTemplateName.class);
		List<ExtTemplateNameDetail> extTemplateNameDetailList = ModelTransformUtils.exchangeClassList(templateNameDetailList, ExtTemplateNameDetail.class);
		TemplateNameForm templateNameForm = new TemplateNameForm();
		templateNameForm.setExtTemplateName(extTemplateName);
		templateNameForm.setExtTemplateNameDetailList(extTemplateNameDetailList);
		result.setT(templateNameForm);
		return result;
    }
	
	/**
     * 根据条件查询
     * @param object Example
     * @return
     */
	 @Override
    public Result<List<ExtTemplateName>> listByCondition(Object object) {
		Result<List<ExtTemplateName>> result = new Result<>();
		Example example = (Example)object;
		List<TemplateName> templateNameList = templateNameMapper.selectByExample(example);
		List<ExtTemplateName>extTemplateNameList =  ModelTransformUtils.exchangeClassList(templateNameList, ExtTemplateName.class);
		result.setT(extTemplateNameList);
		return result;
	}
}
