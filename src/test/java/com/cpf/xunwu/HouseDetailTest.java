package com.cpf.xunwu;

import com.cpf.xunwu.entity.HouseDetail;
import com.cpf.xunwu.service.HouseDetailService;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author caopengflying
 * @descript
 * @createTime 2020/1/9 20:46
 */
public class HouseDetailTest extends XunwuApplicationTests {
    @Resource
    private HouseDetailService houseDetailService;
    @Test
    public void testGetById(){
        HouseDetail byId = houseDetailService.getById(21);
        System.out.println(byId);
    }
}
