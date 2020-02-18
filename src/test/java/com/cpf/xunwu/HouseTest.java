package com.cpf.xunwu;

import com.cpf.xunwu.entity.House;
import com.cpf.xunwu.mapper.HouseMapper;
import com.cpf.xunwu.service.HouseService;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class HouseTest extends XunwuApplicationTests {
    @Resource
    private HouseService houseService;
    @Resource
    private HouseMapper houseMapper;
    @Test
    public void testGetById(){
        House byId = houseService.getById(15);
        System.out.println(byId);
    }
    @Test
    public void testXml(){
        List<House> allHouse = houseMapper.getAllHouse();
        System.out.println(allHouse);

    }
}
