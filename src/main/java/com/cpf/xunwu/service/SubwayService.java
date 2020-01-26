package com.cpf.xunwu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cpf.xunwu.base.ServiceMultiResult;
import com.cpf.xunwu.dto.SubWayDto;
import com.cpf.xunwu.entity.Subway;

public interface SubwayService extends IService<Subway> {
    /**
     * 根据城市名称获取地铁线路
     * @param cityName
     * @return
     */
    ServiceMultiResult<SubWayDto> getSubwayByCityName(String cityName);
}
