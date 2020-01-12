package com.cpf.xunwu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cpf.xunwu.entity.HousePicture;
import com.cpf.xunwu.entity.SupportAddress;
import com.cpf.xunwu.mapper.HousePictureMapper;
import com.cpf.xunwu.mapper.SupportAddressMapper;
import com.cpf.xunwu.service.SupportAddressService;
import org.springframework.stereotype.Service;

@Service
public class SupportAddressServiceImpl extends ServiceImpl<SupportAddressMapper, SupportAddress> implements SupportAddressService {
}
