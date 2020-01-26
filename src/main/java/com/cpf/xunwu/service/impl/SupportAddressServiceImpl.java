package com.cpf.xunwu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cpf.xunwu.base.ServiceMultiResult;
import com.cpf.xunwu.constants.SupportAddressConstants;
import com.cpf.xunwu.dto.SupportAddressDto;
import com.cpf.xunwu.entity.HousePicture;
import com.cpf.xunwu.entity.SupportAddress;
import com.cpf.xunwu.mapper.HousePictureMapper;
import com.cpf.xunwu.mapper.SupportAddressMapper;
import com.cpf.xunwu.service.SupportAddressService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SupportAddressServiceImpl extends ServiceImpl<SupportAddressMapper, SupportAddress> implements SupportAddressService {
    @Resource
    private ModelMapper modelMapper;
    @Override
    public ServiceMultiResult<SupportAddressDto> getAllCities() {
        QueryWrapper<SupportAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SupportAddress::getLevel, SupportAddressConstants.LevelEnum.CITY.getCode());
        List<SupportAddress> list = list(queryWrapper);
        List<SupportAddressDto> supportAddressDtoList = Lists.newArrayList();
        list.forEach(e -> supportAddressDtoList.add(modelMapper.map(e,SupportAddressDto.class)));
        return new ServiceMultiResult<>(supportAddressDtoList, supportAddressDtoList.size());
    }

    @Override
    public ServiceMultiResult<SupportAddressDto> getRegionsByCityName(String cityName) {
        QueryWrapper<SupportAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SupportAddress::getBelongTo, cityName)
        .eq(SupportAddress::getLevel, SupportAddressConstants.LevelEnum.REGION.getCode());
        List<SupportAddress> list = list(queryWrapper);
        List<SupportAddressDto> supportAddressDtoList = Lists.newArrayList();
        list.forEach(e -> supportAddressDtoList.add(modelMapper.map(e,SupportAddressDto.class)));
        return new ServiceMultiResult<>(supportAddressDtoList, supportAddressDtoList.size());
    }

    @Override
    public Map<String, SupportAddressDto> getCityAndRegion(String cityEnName, String regionEnName) {
        Map<String, SupportAddressDto> result = Maps.newHashMap();
        QueryWrapper<SupportAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SupportAddress::getLevel, SupportAddressConstants.LevelEnum.CITY.getCode())
                .eq(SupportAddress::getEnName, cityEnName);
        SupportAddress city = getOne(queryWrapper);
        QueryWrapper<SupportAddress> regionWrapper = new QueryWrapper<>();
        regionWrapper.lambda().eq(SupportAddress::getLevel, SupportAddressConstants.LevelEnum.REGION.getCode())
                .eq(SupportAddress::getBelongTo, cityEnName)
                .eq(SupportAddress::getEnName, regionEnName);
        SupportAddress region = getOne(regionWrapper);
        result.put(SupportAddressConstants.LevelEnum.CITY.getCode(), modelMapper.map(city, SupportAddressDto.class));
        result.put(SupportAddressConstants.LevelEnum.REGION.getCode(), modelMapper.map(region, SupportAddressDto.class));
        return result;
    }
}
