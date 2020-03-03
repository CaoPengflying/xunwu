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
public class HouseIndexMessage {

    public static final String INDEX = "index";
    public static final String REMOVE = "remove";

    public static final int MAX_RETRY = 3;

    private Long houseId;
    private String operation;
    private int retry = 0;

}
