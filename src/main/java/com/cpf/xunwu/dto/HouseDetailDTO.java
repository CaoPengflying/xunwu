package com.cpf.xunwu.dto;

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
public class HouseDetailDTO {
    private String description;

    private String layoutDesc;

    private String traffic;

    private String roundService;

    private Integer rentWay;

    private Long adminId;

    private String address;

    private Long subwayLineId;

    private Long subwayStationId;

    private String subwayLineName;

    private String subwayStationName;
}
