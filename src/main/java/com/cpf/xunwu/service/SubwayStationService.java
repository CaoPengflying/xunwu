package com.cpf.xunwu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cpf.xunwu.base.ServiceMultiResult;
import com.cpf.xunwu.dto.SubwayStationDto;
import com.cpf.xunwu.entity.House;
import com.cpf.xunwu.entity.SubwayStation;

public interface SubwayStationService extends IService<SubwayStation> {
    /**
     * 根据地铁线路获取地铁站
     * @param subwayId
     * @return
     */
    ServiceMultiResult<SubwayStationDto> getStationBySubwayId(Integer subwayId);
}
