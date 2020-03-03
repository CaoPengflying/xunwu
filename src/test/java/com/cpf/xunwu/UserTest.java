package com.cpf.xunwu;

import com.cpf.xunwu.entity.User;
import com.cpf.xunwu.service.TranscationP;
import com.cpf.xunwu.service.UserService;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author caopengflying
 * @time 2020/1/25
 */
public class UserTest extends XunwuApplicationTests {
    @Resource
    private UserService userService;


    @Test
    public void testGetById() {
        User admin = userService.selectUserByName("admin");
        System.out.println(admin);
    }


}
