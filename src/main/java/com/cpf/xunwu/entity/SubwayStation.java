package com.cpf.xunwu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.*;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
@TableName(value = "subway_station")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubwayStation implements Serializable {
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 所属地铁线id
	 */
	@TableField("subway_id")
	private Integer subwayId;
	/**
	 * 站点名称
	 */
	@TableField("name")
	private String name;

}

