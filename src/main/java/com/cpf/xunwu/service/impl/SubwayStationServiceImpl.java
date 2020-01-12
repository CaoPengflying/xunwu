package com.cpf.xunwu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cpf.xunwu.entity.HousePicture;
import com.cpf.xunwu.entity.SubwayStation;
import com.cpf.xunwu.mapper.HousePictureMapper;
import com.cpf.xunwu.mapper.SubwayStationMapper;
import com.cpf.xunwu.service.SubwayStationService;
import org.springframework.stereotype.Service;

@Service
public class SubwayStationServiceImpl extends ServiceImpl<SubwayStationMapper, SubwayStation> implements SubwayStationService {
}
