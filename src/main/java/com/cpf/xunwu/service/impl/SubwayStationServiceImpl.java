package com.cpf.xunwu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cpf.xunwu.base.ServiceMultiResult;
import com.cpf.xunwu.dto.SubwayStationDto;
import com.cpf.xunwu.entity.HousePicture;
import com.cpf.xunwu.entity.SubwayStation;
import com.cpf.xunwu.mapper.HousePictureMapper;
import com.cpf.xunwu.mapper.SubwayStationMapper;
import com.cpf.xunwu.service.SubwayStationService;
import com.google.common.collect.Lists;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SubwayStationServiceImpl extends ServiceImpl<SubwayStationMapper, SubwayStation> implements SubwayStationService {
    @Resource
    private ModelMapper modelMapper;
    @Override
    public ServiceMultiResult<SubwayStationDto> getStationBySubwayId(Integer subwayId) {
        QueryWrapper<SubwayStation> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SubwayStation::getSubwayId, subwayId);
        List<SubwayStation> list = list(queryWrapper);
        List<SubwayStationDto> subwayStationDtoList = Lists.newArrayList();
        list.forEach(e -> subwayStationDtoList.add(modelMapper.map(e, SubwayStationDto.class)));
        return new ServiceMultiResult<>(subwayStationDtoList, subwayStationDtoList.size());
    }
}
