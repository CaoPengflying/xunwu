package com.cpf.xunwu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cpf.xunwu.base.ServiceMultiResult;
import com.cpf.xunwu.base.ServiceResult;
import com.cpf.xunwu.dto.HouseDto;
import com.cpf.xunwu.entity.House;
import com.cpf.xunwu.form.DatatableSearch;
import com.cpf.xunwu.form.HouseForm;
import com.cpf.xunwu.form.RentSearch;

public interface HouseService extends IService<House> {
    /**
     * 保存房屋信息
     * @param houseForm
     * @return
     */
    ServiceResult<HouseDto> saveForm(HouseForm houseForm);

    /**
     * 管理员查询全部房屋信息
     * @return
     * @param datatableSearch
     */
    ServiceMultiResult<HouseDto> adminQuery(DatatableSearch datatableSearch);

    /**
     * 查询房屋信息
     * @param rentSearch
     * @return
     */
    ServiceMultiResult<HouseDto> search(RentSearch rentSearch);

    /**
     * 根据房屋id获取明细
     * @param houseId
     * @return
     */
    ServiceResult<HouseDto> getDetail(Integer houseId);
}
