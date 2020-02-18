package com.cpf.xunwu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cpf.xunwu.entity.House;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseMapper extends BaseMapper<House> {
    List<House> getAllHouse();
}
