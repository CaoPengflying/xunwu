package com.cpf.xunwu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cpf.xunwu.entity.HousePicture;
import com.cpf.xunwu.entity.Subway;
import com.cpf.xunwu.mapper.HousePictureMapper;
import com.cpf.xunwu.mapper.SubwayMapper;
import com.cpf.xunwu.service.SubwayService;
import org.springframework.stereotype.Service;

@Service
public class SubwayServiceImpl extends ServiceImpl<SubwayMapper, Subway> implements SubwayService {
}
