package com.baomidou.ant.service.impl;

import com.baomidou.ant.entity.User;
import com.baomidou.ant.mapper.UserMapper;
import com.baomidou.ant.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户基本信息表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-02-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
