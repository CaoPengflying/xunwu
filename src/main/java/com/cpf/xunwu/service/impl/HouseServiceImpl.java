package com.cpf.xunwu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cpf.xunwu.entity.House;
import com.cpf.xunwu.mapper.HouseMapper;
import com.cpf.xunwu.service.HouseService;
import org.springframework.stereotype.Service;

@Service
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements HouseService {

}
