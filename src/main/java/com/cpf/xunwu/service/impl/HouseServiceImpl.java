package com.cpf.xunwu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cpf.xunwu.base.BusinessException;
import com.cpf.xunwu.base.ServiceMultiResult;
import com.cpf.xunwu.base.ServiceResult;
import com.cpf.xunwu.dto.HouseDto;
import com.cpf.xunwu.dto.HousePictureDTO;
import com.cpf.xunwu.dto.SupportAddressDto;
import com.cpf.xunwu.entity.*;
import com.cpf.xunwu.form.DatatableSearch;
import com.cpf.xunwu.form.HouseForm;
import com.cpf.xunwu.form.PhotoForm;
import com.cpf.xunwu.mapper.HouseMapper;
import com.cpf.xunwu.service.*;
import com.cpf.xunwu.util.LoginUserUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author caopengflying
 * @time 2020/1/26
 */
@Service
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements HouseService {
    @Resource
    private ModelMapper modelMapper;
    @Resource
    private SupportAddressService supportAddressService;
    @Resource
    private HouseDetailService houseDetailService;
    @Resource
    private HousePictureService housePictureService;
    @Resource
    private HouseTagService houseTagService;
    @Resource
    private SubwayService subwayService;
    @Resource
    private SubwayStationService subwayStationService;
    @Value("${qiniu.cdn.prefix}")
    private String cdnPrefix;
    @Override
    public ServiceResult<HouseDto> saveForm(HouseForm houseForm) {
        //地区合法校验
        Map<String, SupportAddressDto> cityAndRegion = supportAddressService.getCityAndRegion(houseForm.getCityEnName(), houseForm.getRegionEnName());
        if (cityAndRegion.keySet().size() != 2){
            throw new BusinessException("地区信息错误");
        }
        House house = new House();
        modelMapper.map(houseForm,house);
        house.setCreateTime(new Date());
        house.setLastUpdateTime(new Date());
        house.setAdminId(LoginUserUtil.getLoginUserId());
        save(house);

        HouseDetail houseDetail = new HouseDetail();
        wrapperDetailInfo(houseDetail, houseForm);
        houseDetail.setHouseId(house.getId());
        houseDetailService.save(houseDetail);
        List<HousePicture> housePictureList = geneatePictures(houseForm,house);
        HouseDto houseDto = modelMapper.map(house, HouseDto.class);
        if (CollectionUtils.isNotEmpty(housePictureList)){
            housePictureService.saveBatch(housePictureList);
            houseDto.setPictures(housePictureList.parallelStream().map(e -> modelMapper.map(e, HousePictureDTO.class)).collect(Collectors.toList()));
        }
        List<HouseTag> houseTagList = generateTags(houseForm,house);
        if (CollectionUtils.isNotEmpty(houseTagList)){
            houseTagService.saveBatch(houseTagList);
            houseDto.setTags(houseTagList.parallelStream().map(HouseTag::getName).collect(Collectors.toList()));
        }
        return new ServiceResult<>(true,houseDto,"新增成功");
    }

    @Override
    public ServiceMultiResult<HouseDto> adminQuery(DatatableSearch datatableSearch) {
        List<House> list = list();
        List<HouseDto> houseDtoList = Lists.newArrayList();
        for (House house : list) {
            HouseDto houseDto = modelMapper.map(house, HouseDto.class);
            houseDto.setCover(cdnPrefix + house.getCover());
            houseDtoList.add(houseDto);
        }
        return new ServiceMultiResult<>(houseDtoList, houseDtoList.size());
    }

    /**
     * 生成房屋标签
     * @param houseForm
     * @param house
     * @return
     */
    private List<HouseTag> generateTags(HouseForm houseForm, House house) {
        List<HouseTag> houseTagList = Lists.newArrayList();
        for (String tag : houseForm.getTags()) {
            HouseTag houseTag = new HouseTag();
            houseTag.setHouseId(house.getId());
            houseTag.setName(tag);
            houseTagList.add(houseTag);
        }
        return houseTagList;
    }

    /**
     * 生成房屋图片
     * @param houseForm
     * @param house
     * @return
     */
    private List<HousePicture> geneatePictures(HouseForm houseForm, House house) {
        List<HousePicture> housePictureList = Lists.newArrayList();
        for (PhotoForm photo : houseForm.getPhotos()) {
            HousePicture housePicture = new HousePicture();
            modelMapper.map(photo,housePicture);
            housePicture.setHouseId(house.getId());
            housePicture.setCdnPrefix(cdnPrefix);
            housePictureList.add(housePicture);
        }
        return housePictureList;
    }

    /**
     * 拼接明细信息
     * @param houseDetail
     * @param houseForm
     */
    private void wrapperDetailInfo(HouseDetail houseDetail, HouseForm houseForm) {
        modelMapper.map(houseForm,houseDetail);
        SubwayStation subwayStation = subwayStationService.getById(houseForm.getSubwayStationId());
        if (null == subwayStation){
            throw new BusinessException("地铁站不存在");
        }
        Subway subway = subwayService.getById(houseForm.getSubwayLineId());
        if (null == subway){
            throw new BusinessException("地铁线不存在");
        }
        houseDetail.setSubwayLineId(subway.getId());
        houseDetail.setSubwayLineName(subway.getName());
        houseDetail.setSubwayStationId(subwayStation.getId());
        houseDetail.setSubwayStationName(subwayStation.getName());
    }


}
