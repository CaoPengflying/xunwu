package com.cpf.xunwu.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cpf.xunwu.entity.House;
import com.cpf.xunwu.mapper.HouseMapper;
import com.cpf.xunwu.service.HouseService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements HouseService {

}
