package com.cpf.xunwu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cpf.xunwu.mapper.UserMapper;
import com.cpf.xunwu.entity.User;
import com.cpf.xunwu.service.UserService;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService  {
}

