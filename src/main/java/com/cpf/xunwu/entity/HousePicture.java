package com.cpf.xunwu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.*;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
@TableName(value = "house_picture")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HousePicture implements Serializable {
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 所属房屋id
	 */
	@TableField("house_id")
	private Integer houseId;
	/**
	 * 图片路径
	 */
	@TableField("cdn_prefix")
	private String cdnPrefix;
	/**
	 * 宽
	 */
	@TableField("width")
	private Integer width;
	/**
	 * 高
	 */
	@TableField("height")
	private Integer height;
	/**
	 * 所属房屋位置
	 */
	@TableField("location")
	private String location;
	/**
	 * 文件名
	 */
	@TableField("path")
	private String path;

}

