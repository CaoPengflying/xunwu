package com.cpf.xunwu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cpf.xunwu.entity.HousePicture;
import com.cpf.xunwu.entity.HouseTag;
import com.cpf.xunwu.mapper.HousePictureMapper;
import com.cpf.xunwu.mapper.HouseTagMapper;
import com.cpf.xunwu.service.HouseTagService;
import org.springframework.stereotype.Service;
/**
 * @author caopengflying
 * @time 2020/1/26
 */
@Service
public class HouseTagServiceImpl extends ServiceImpl<HouseTagMapper, HouseTag> implements HouseTagService {
}
