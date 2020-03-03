package com.cpf.xunwu.service.impl;

import com.cpf.xunwu.entity.User;
import com.cpf.xunwu.service.A;
import com.cpf.xunwu.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author caopengflying
 * @time 2020/2/29
 */
@Service
public class AImpl implements A {
    @Resource
    private UserService userService;
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void create() {
        User user = new User();
        user.setName("ceshi");
        user.setPhoneNumber("18679229291");
        userService.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void update() {
        User user = new User();
        user.setId(5);
        user.setName("update");
        int i = 1/0;
        userService.updateById(user);
    }
}
