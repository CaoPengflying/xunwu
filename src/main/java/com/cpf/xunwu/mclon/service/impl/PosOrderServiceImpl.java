package com.cpf.xunwu.mclon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cpf.xunwu.entity.PosOrder;
import com.cpf.xunwu.mclon.mapper.PosOrderMapper;
import com.cpf.xunwu.mclon.service.PosOrderService;
import org.springframework.stereotype.Service;

/**
 * date 2020/5/28
 *
 * @author caopengflying
 */
@Service
public class PosOrderServiceImpl extends ServiceImpl<PosOrderMapper, PosOrder> implements PosOrderService {
}
