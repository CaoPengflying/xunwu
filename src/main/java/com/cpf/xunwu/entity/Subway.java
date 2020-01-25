package com.cpf.xunwu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.*;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
@TableName(value = "subway")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Subway implements Serializable {
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 线路名
	 */
	@TableField("name")
	private String name;
	/**
	 * 所属城市英文名缩写
	 */
	@TableField("city_en_name")
	private String cityEnName;

}

