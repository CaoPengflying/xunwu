package com.cpf.xunwu;

import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author caopengflying
 * @time 2020/2/29
 */
public class TranscationP extends XunwuApplicationTests {
    @Resource
    private com.cpf.xunwu.service.TranscationP transcationP;

    @Test
    public void testA() {
        transcationP.a();
    }
}
