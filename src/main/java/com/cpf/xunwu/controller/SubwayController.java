package com.cpf.xunwu.controller;

import com.cpf.xunwu.base.ApiResponse;
import com.cpf.xunwu.base.ServiceMultiResult;
import com.cpf.xunwu.dto.SubWayDto;
import com.cpf.xunwu.service.SubwayService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author caopengflying
 * @time 2020/1/26
 */

@Controller
public class SubwayController {
    @Resource
    private SubwayService subwayService;
    @RequestMapping("address/support/subway/line")
    @ResponseBody
    public ApiResponse getSubwayByCityName(@RequestParam("city_name") String cityName){
        ServiceMultiResult<SubWayDto> result = subwayService.getSubwayByCityName(cityName);
        return ApiResponse.ofSuccessMultiResult(result);
    }
}
