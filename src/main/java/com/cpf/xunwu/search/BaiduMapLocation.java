package com.cpf.xunwu.search;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class BaiduMapLocation {
    // 经度
    @JsonProperty("lon")
    private double longitude;

    // 纬度
    @JsonProperty("lat")
    private double latitude;

}
