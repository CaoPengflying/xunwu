package com.cpf.xunwu.controller;

import com.cpf.xunwu.base.ApiResponse;
import com.cpf.xunwu.base.ServiceMultiResult;
import com.cpf.xunwu.dto.SupportAddressDto;
import com.cpf.xunwu.service.SupportAddressService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author caopengflying
 * @time 2020/1/26
 */
@Controller
@RequestMapping("address")
public class SupportAddressController {
    @Resource
    private SupportAddressService supportAddressService;

    /**
     * 获取全部城市
     *
     * @return
     */
    @GetMapping("/support/cities")
    @ResponseBody
    public ApiResponse getAllCities() {
        ServiceMultiResult<SupportAddressDto> result = supportAddressService.getAllCities();
        if (result.getTotal() == 0) {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_FOUND);
        }
        return ApiResponse.ofSuccess(result.getResult());
    }

    /**
     * 根据城市名称获取县区
     *
     * @return
     */
    @RequestMapping("/support/regions")
    @ResponseBody
    public ApiResponse getRegionsByCityName(@RequestParam("city_name") String cityName) {
        ServiceMultiResult<SupportAddressDto> result = supportAddressService.getRegionsByCityName(cityName);
        if (result.getTotal() == 0) {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_FOUND);
        }
        return ApiResponse.ofSuccess(result.getResult());
    }
}
