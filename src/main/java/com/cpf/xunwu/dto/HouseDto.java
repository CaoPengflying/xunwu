package com.cpf.xunwu.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * @author caopengflying
 * @time 2020/1/26
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class HouseDto {
    private Long id;

    private String title;

    private int price;

    private int area;

    private int direction;

    private int room;

    private int parlour;

    private int bathroom;

    private int floor;

    private Long adminId;

    private String district;

    private int totalFloor;

    private int watchTimes;

    private int buildYear;

    private int status;

    private Date createTime;

    private Date lastUpdateTime;

    private String cityEnName;

    private String regionEnName;

    private String street;

    private String cover;

    private int distanceToSubway;

    private HouseDetailDTO houseDetail;

    private List<String> tags;

    private List<HousePictureDTO> pictures;

    private int subscribeStatus;
}
