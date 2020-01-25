package com.cpf.xunwu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.*;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
@TableName(value = "support_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SupportAddress implements Serializable {
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 上一级行政单位名
	 */
	@TableField("belong_to")
	private String belongTo;
	/**
	 * 行政单位英文名缩写
	 */
	@TableField("en_name")
	private String enName;
	/**
	 * 行政单位中文名
	 */
	@TableField("cn_name")
	private String cnName;
	/**
	 * 行政级别市-city地区-region
	 */
	@TableField("level")
	private String level;
	/**
	 * 百度地图经度
	 */
	@TableField("baidu_map_lng")
	private BigDecimal baiduMapLng;
	/**
	 * 百度地图纬度
	 */
	@TableField("baidu_map_lat")
	private BigDecimal baiduMapLat;

}

