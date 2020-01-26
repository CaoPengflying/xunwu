package com.cpf.xunwu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author caopengflying
 * @time 2020/1/26
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SubWayDto {
    private Integer id;
    private String name;
    @JsonProperty("city_en_name")
    private String cityEnName;

}
