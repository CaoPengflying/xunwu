package com.cpf.xunwu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author caopengflying
 * @time 2020/1/26
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SubwayStationDto {
    private Integer id;
    @JsonProperty("subway_id")
    private Integer subwayId;
    private String name;
}
