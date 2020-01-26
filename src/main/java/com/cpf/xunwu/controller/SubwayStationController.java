package com.cpf.xunwu.controller;

import com.cpf.xunwu.base.ApiResponse;
import com.cpf.xunwu.base.ServiceMultiResult;
import com.cpf.xunwu.dto.SubwayStationDto;
import com.cpf.xunwu.service.SubwayStationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author caopengflying
 * @time 2020/1/26
 */
@Controller
public class SubwayStationController {
    @Resource
    private SubwayStationService subwayStationService;

    @GetMapping("address/support/subway/station")
    @ResponseBody
    public ApiResponse getStationBySubwayId(@RequestParam("subway_id") Integer subwayId) {
        ServiceMultiResult<SubwayStationDto> result = subwayStationService.getStationBySubwayId(subwayId);
        return ApiResponse.ofSuccessMultiResult(result);
    }
}
