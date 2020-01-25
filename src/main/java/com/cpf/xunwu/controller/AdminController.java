package com.cpf.xunwu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author caopengflying
 * @time 2020/1/23
 */
@Controller
@RequestMapping("admin")
public class AdminController {
    @GetMapping("/center")
    public String adminCenterPage() {
        return "admin/center";
    }

    @GetMapping("/welcome")
    public String adminWelcomePage() {
        return "admin/welcome";
    }

    @GetMapping("/login")
    public String adminLoginPage() {
        return "admin/login";
    }
}
