package com.cpf.xunwu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cpf.xunwu.entity.HousePicture;
import com.cpf.xunwu.entity.Role;
import com.cpf.xunwu.mapper.HousePictureMapper;
import com.cpf.xunwu.mapper.RoleMapper;
import com.cpf.xunwu.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
