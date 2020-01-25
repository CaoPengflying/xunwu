package com.cpf.xunwu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cpf.xunwu.base.BusinessException;
import com.cpf.xunwu.base.ErrorConstant;
import com.cpf.xunwu.entity.Role;
import com.cpf.xunwu.mapper.UserMapper;
import com.cpf.xunwu.entity.User;
import com.cpf.xunwu.service.RoleService;
import com.cpf.xunwu.service.UserService;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService  {
    @Resource
    private RoleService roleService;
    @Override
    public User selectUserByName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getName, username);
        User user = getOne(queryWrapper);
        if (null != user){
            QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
            roleQueryWrapper.lambda().eq(Role::getUserId,user.getId());
            List<Role> roleList = roleService.list(roleQueryWrapper);
            if (CollectionUtils.isEmpty(roleList)){
                throw new BusinessException("权限不能为空", ErrorConstant.FAIL);
            }
            List<GrantedAuthority> authorityList = Lists.newArrayList();
            roleList.forEach(e -> authorityList.add(new SimpleGrantedAuthority("ROLE_" + e.getName())));
            user.setAuthorityList(authorityList);
        }
        return user;

    }
}

