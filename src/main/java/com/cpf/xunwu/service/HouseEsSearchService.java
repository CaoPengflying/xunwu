package com.cpf.xunwu.service;

/**
 * @author caopengflying
 * @time 2020/3/1
 */
public interface HouseEsSearchService {
    /**
     * 增加索引
     * @param houseId
     */
    void index(Long houseId);

    /**
     * 移除索引
     * @param houseId
     */
    void delete(Long houseId);
}
