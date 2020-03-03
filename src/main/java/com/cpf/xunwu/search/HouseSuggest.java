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
public class HouseSuggest {
    private String input;
    // 默认权重
    private int weight = 10;
}
