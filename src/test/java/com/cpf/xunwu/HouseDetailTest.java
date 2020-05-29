package com.cpf.xunwu;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cpf.xunwu.entity.HouseDetail;
import com.cpf.xunwu.service.HouseDetailService;
import org.assertj.core.util.Lists;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author caopengflying
 * @descript
 * @createTime 2020/1/9 20:46
 */
public class HouseDetailTest extends XunwuApplicationTests {
    @Resource
    private HouseDetailService houseDetailService;

    @Test
    public void testGetById() {
        Page<HouseDetail> page = new Page<>(1, 20);
        Page<HouseDetail> page1 = houseDetailService.page(page);
        List<HouseDetail> records = page1.getRecords();
        System.out.println(page1.getTotal());
        System.out.println(records);
//        HouseDetail byId = houseDetailService.getById(21);
//        System.out.println(byId);
    }

    @Test
    public void testBatchSave() {
        HouseDetail houseDetail = new HouseDetail();
        houseDetail.setRentWay(1);
        houseDetail.setAddress("test");
        houseDetail.setHouseId(1);
        List<HouseDetail> houseDetails = Lists.newArrayList(houseDetail);
        houseDetailService.saveBatch(houseDetails);
        System.out.println(houseDetails);
    }
}
