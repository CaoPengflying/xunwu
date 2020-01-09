package com.cpf.xunwu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.*;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
@TableName(value = "house_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HouseDetail implements Serializable {
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 详细描述
	 */
	@TableField("description")
	private String description;
	/**
	 * 户型介绍
	 */
	@TableField("layout_desc")
	private String layoutDesc;
	/**
	 * 交通出行
	 */
	@TableField("traffic")
	private String traffic;
	/**
	 * 周边配套
	 */
	@TableField("round_service")
	private String roundService;
	/**
	 * 租赁方式
	 */
	@TableField("rent_way")
	private Integer rentWay;
	/**
	 * 详细地址
	 */
	@TableField("address")
	private String address;
	/**
	 * 附近地铁线id
	 */
	@TableField("subway_line_id")
	private Integer subwayLineId;
	/**
	 * 附近地铁线名称
	 */
	@TableField("subway_line_name")
	private String subwayLineName;
	/**
	 * 地铁站id
	 */
	@TableField("subway_station_id")
	private Integer subwayStationId;
	/**
	 * 地铁站名
	 */
	@TableField("subway_station_name")
	private String subwayStationName;
	/**
	 * 对应house的id
	 */
	@TableField("house_id")
	private Integer houseId;

}

