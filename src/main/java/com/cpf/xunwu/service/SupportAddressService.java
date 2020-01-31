package com.cpf.xunwu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cpf.xunwu.base.ServiceMultiResult;
import com.cpf.xunwu.base.ServiceResult;
import com.cpf.xunwu.dto.SupportAddressDto;
import com.cpf.xunwu.entity.House;
import com.cpf.xunwu.entity.SupportAddress;

import java.util.Map;

public interface SupportAddressService extends IService<SupportAddress> {
    /**
     * 获取所有得城市
     * @return
     */
    ServiceMultiResult<SupportAddressDto> getAllCities();

    /**
     * 根据城市名称获取区县
     * @param cityName
     * @return
     */
    ServiceMultiResult<SupportAddressDto> getRegionsByCityName(String cityName);

    /**
     * 获取城市和区县
     * @param cityEnName
     * @param regionEnName
     * @return
     */
    Map<String, SupportAddressDto> getCityAndRegion(String cityEnName, String regionEnName);

    /**
     * 根据城市名称获取城市详情
     * @param cityEnName
     * @return
     */
    ServiceResult<SupportAddressDto> getCityByEnName(String cityEnName);
}
