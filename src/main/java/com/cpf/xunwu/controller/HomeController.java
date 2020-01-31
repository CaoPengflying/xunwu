package com.cpf.xunwu.controller;

import com.cpf.xunwu.base.ErrorConstant;
import com.cpf.xunwu.base.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author caopengflying
 * @time 2020/1/21
 */
@Controller
public class HomeController {
    @GetMapping(value = {"/","/index"})
    public String index(){
        return "index";
    }

    @GetMapping("/404")
    public String notFoundPage() {
        return "404";
    }

    @GetMapping("/403")
    public String accessError() {
        return "403";
    }

    @GetMapping("/500")
    public String internalError() {
        return "500";
    }

    @GetMapping("/logout/page")
    public String logout() {
        return "logout";
    }
}
