package com.cpf.xunwu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cpf.xunwu.base.ServiceMultiResult;
import com.cpf.xunwu.dto.SubWayDto;
import com.cpf.xunwu.entity.HousePicture;
import com.cpf.xunwu.entity.Subway;
import com.cpf.xunwu.mapper.HousePictureMapper;
import com.cpf.xunwu.mapper.SubwayMapper;
import com.cpf.xunwu.service.SubwayService;
import com.google.common.collect.Lists;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.accessibility.AccessibleIcon;
import javax.annotation.Resource;
import java.util.List;
/**
 * @author caopengflying
 * @time 2020/1/26
 */
@Service
public class SubwayServiceImpl extends ServiceImpl<SubwayMapper, Subway> implements SubwayService {
    @Resource
    private ModelMapper modelMapper;
    @Override
    public ServiceMultiResult<SubWayDto> getSubwayByCityName(String cityName) {
        QueryWrapper<Subway> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Subway::getCityEnName, cityName);
        List<Subway> list = list(queryWrapper);
        List<SubWayDto> subWayDtoList = Lists.newArrayList();
        list.forEach(e -> subWayDtoList.add(modelMapper.map(e, SubWayDto.class)));
        return new ServiceMultiResult<>(subWayDtoList,subWayDtoList.size());
    }
}
