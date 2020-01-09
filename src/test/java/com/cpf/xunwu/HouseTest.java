package com.cpf.xunwu;

import com.cpf.xunwu.entity.House;
import com.cpf.xunwu.service.HouseService;
import org.junit.Test;

import javax.annotation.Resource;

public class HouseTest extends XunwuApplicationTests {
    @Resource
    private HouseService houseService;
    @Test
    public void testGetById(){
        House byId = houseService.getById(15);
        System.out.println(byId);
    }
}
