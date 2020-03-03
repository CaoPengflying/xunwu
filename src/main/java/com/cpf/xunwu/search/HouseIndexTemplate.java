package com.cpf.xunwu.search;

import lombok.*;

import java.util.Date;
import java.util.List;

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
public class HouseIndexTemplate {
    private Long houseId;

    private String title;

    private int price;

    private int area;

    private Date createTime;

    private Date lastUpdateTime;

    private String cityEnName;

    private String regionEnName;

    private int direction;

    private int distanceToSubway;

    private String subwayLineName;

    private String subwayStationName;

    private String street;

    private String district;

    private String description;

    private String layoutDesc;

    private String traffic;

    private String roundService;

    private int rentWay;

    private List<String> tags;

    private List<HouseSuggest> suggest;

    private BaiduMapLocation location;
}
