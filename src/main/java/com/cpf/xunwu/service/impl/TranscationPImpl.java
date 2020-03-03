package com.cpf.xunwu.service.impl;

import com.cpf.xunwu.service.A;
import com.cpf.xunwu.service.TranscationP;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author caopengflying
 * @time 2020/2/29
 */
@Service
public class TranscationPImpl implements TranscationP {
    @Resource
    private A aClass;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void a() {
        aClass.create();
        try {
            aClass.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
