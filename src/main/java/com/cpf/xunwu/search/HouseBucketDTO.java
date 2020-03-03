package com.cpf.xunwu.search;

import lombok.*;

/**
 * @author caopengflying
 * @descript
 * @createTime 2020/1/9 20:39
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HouseBucketDTO {
    /**
     * 聚合bucket的key
     */
    private String key;

    /**
     * 聚合结果值
     */
    private long count;

}
