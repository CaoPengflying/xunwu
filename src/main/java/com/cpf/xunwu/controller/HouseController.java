package com.cpf.xunwu.controller;

import com.cpf.xunwu.base.ApplicationConstants;
import com.cpf.xunwu.base.RentValueBlock;
import com.cpf.xunwu.base.ServiceMultiResult;
import com.cpf.xunwu.base.ServiceResult;
import com.cpf.xunwu.constants.SupportAddressConstants;
import com.cpf.xunwu.dto.HouseDetailDTO;
import com.cpf.xunwu.dto.HouseDto;
import com.cpf.xunwu.dto.SupportAddressDto;
import com.cpf.xunwu.dto.UserDto;
import com.cpf.xunwu.entity.User;
import com.cpf.xunwu.form.RentSearch;
import com.cpf.xunwu.service.HouseService;
import com.cpf.xunwu.service.SupportAddressService;
import com.cpf.xunwu.service.UserService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author caopengflying
 * @time 2020/1/26
 */
@Controller
public class HouseController {
    @Resource
    private SupportAddressService supportAddressService;
    @Resource
    private HouseService houseService;
    @Resource
    private UserService userService;

    /**
     * 租房页面展示
     *
     * @param rentSearch
     * @param model
     * @param session
     * @param redirectAttributes
     * @return
     */
    @GetMapping("rent/house")
    public String rentHousePage(RentSearch rentSearch, Model model, HttpSession session,
                                RedirectAttributes redirectAttributes) {
        if (StringUtils.isEmpty(rentSearch.getCityEnName())) {
            String cityName = (String) session.getAttribute("cityEnName");
            if (StringUtils.isEmpty(cityName)) {
                redirectAttributes.addAttribute("msg", "must_chose_city");
            } else {
                rentSearch.setCityEnName(cityName);
            }
        } else {
            session.setAttribute("cityEnName", rentSearch.getCityEnName());
        }
        ServiceMultiResult<SupportAddressDto> addressResult = supportAddressService.getRegionsByCityName(rentSearch.getCityEnName());
        if (addressResult.getResult() == null || addressResult.getTotal() < 1) {
            redirectAttributes.addAttribute("msg", "must_chose_city");
            return ApplicationConstants.REDIRECT_TO_INDEX_URL;
        }
        ServiceResult<SupportAddressDto> cityResult = supportAddressService.getCityByEnName(rentSearch.getCityEnName());
        if (!cityResult.isSuccess()){
            redirectAttributes.addAttribute("msg", "must_chose_city");
            return ApplicationConstants.REDIRECT_TO_INDEX_URL;
        }
        model.addAttribute("currentCity", cityResult.getResult());
        ServiceMultiResult<HouseDto> houseResult = houseService.search(rentSearch);
        model.addAttribute("total", houseResult.getTotal());
        model.addAttribute("houses", houseResult.getResult());
        if (rentSearch.getCityEnName() == null){
            rentSearch.setCityEnName("*");
        }
        model.addAttribute("searchBody", rentSearch);
        model.addAttribute("regions", addressResult.getResult());
        model.addAttribute("priceBlocks", RentValueBlock.PRICE_BLOCK);
        model.addAttribute("areaBlocks", RentValueBlock.AREA_BLOCK);
        model.addAttribute("currentPriceBlock", RentValueBlock.matchPrice(rentSearch.getPriceBlock()));
        model.addAttribute("currentAreaBlock", RentValueBlock.matchArea(rentSearch.getAreaBlock()));
        return "rent-list";
    }

    @GetMapping("rent/house/show/{id}")
    public String show(@PathVariable(value = "id") Integer houseId,Model model){
        if (houseId < 1) {
            return "404";
        }
        ServiceResult<HouseDto> result = houseService.getDetail(houseId);
        if (!result.isSuccess()){
            return "404";
        }
        HouseDto houseDto = result.getResult();
        Map<String, SupportAddressDto> addressDtoMap = supportAddressService.getCityAndRegion(houseDto.getCityEnName(), houseDto.getRegionEnName());
        SupportAddressDto city = addressDtoMap.get(SupportAddressConstants.LevelEnum.CITY.getCode());
        SupportAddressDto region = addressDtoMap.get(SupportAddressConstants.LevelEnum.REGION.getCode());
        ServiceResult<UserDto> userResult = userService.getDetailById(houseDto.getAdminId());
        model.addAttribute("agent", userResult.getResult());
        model.addAttribute("city", city);
        model.addAttribute("region", region);
        model.addAttribute("house",houseDto);
        model.addAttribute("houseCountInDistrict", 0);
        return "house-detail";
    }
}
