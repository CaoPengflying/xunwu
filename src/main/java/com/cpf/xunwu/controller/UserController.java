package com.cpf.xunwu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author caopengflying
 * @time 2020/1/25
 */
@Controller
@RequestMapping("user")
public class UserController {
    @GetMapping("/login")
    public String loginPage(){
        return "user/login";
    }
    @GetMapping("/center")
    public String centerPage(){
        return "user/center";
    }
}
