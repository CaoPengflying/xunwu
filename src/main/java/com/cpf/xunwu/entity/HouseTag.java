package com.cpf.xunwu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.*;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
@TableName(value = "house_tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HouseTag implements Serializable {
	/**
	 * 房屋id
	 */
	@TableField("house_id")
	private Integer houseId;
	/**
	 * 标签id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	@TableField("name")
	private String name;

}

