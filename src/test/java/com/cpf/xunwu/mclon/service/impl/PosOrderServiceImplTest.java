package com.cpf.xunwu.mclon.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cpf.xunwu.XunwuApplicationTests;
import com.cpf.xunwu.base.HttpClientResult;
import com.cpf.xunwu.entity.PosOrder;
import com.cpf.xunwu.util.HttpClientUtils;
import com.google.common.collect.Maps;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * date 2020/5/28
 *
 * @author caopengflying
 */
public class PosOrderServiceImplTest extends XunwuApplicationTests {
    @Resource
    PosOrderServiceImpl posOrderService;

    @Test
    @Transactional("transactionManager1")
    public void testListPosOrder() {
        LambdaQueryWrapper<PosOrder> posOrderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        posOrderLambdaQueryWrapper.eq(PosOrder::getStatus, 5)
                .ge(PosOrder::getSalesOrderTime, "2020-05-20")
                .gt(PosOrder::getOrderId,80660942);
        List<PosOrder> list = posOrderService.list(posOrderLambdaQueryWrapper);
        for (PosOrder posOrder : list) {
            try {
                String url = "http://erp.mclon.com:15000/pos/posOrderRetail/manualSendOrderToDMOrNC";
                Map<String, String> paramMap = Maps.newHashMap();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", 2);
                jsonObject.put("orderNos", Lists.newArrayList(posOrder.getOrderNo()));
                paramMap.put("jsonObject", jsonObject.toJSONString());
                HttpClientResult httpClientResult = HttpClientUtils.doPost(url, paramMap);
                System.out.println(httpClientResult.getContent());
                Thread.sleep(400);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(list.size());
    }
}