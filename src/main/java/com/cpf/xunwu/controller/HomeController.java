package com.cpf.xunwu.controller;

import base.ErrorConstant;
import base.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author caopengflying
 * @time 2020/1/21
 */
@Controller
public class HomeController {
    @GetMapping("/")
    public String index(){
        return "/index";
    }
    @RequestMapping("/get")
    @ResponseBody
    public Result get(){
        return new Result(ErrorConstant.SUCCESS, "成功了");
    }
}
