package com.cpf.xunwu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.*;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@TableName(value = "`house_subscribe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HouseSubscribe implements Serializable {
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 房源id
	 */
	@TableField("house_id")
	private Integer houseId;
	/**
	 * 用户id
	 */
	@TableField("user_id")
	private Integer userId;
	/**
	 * 用户描述
	 */
	@TableField("desc")
	private String desc;
	/**
	 * 预约状态1-加入待看清单2-已预约看房时间3-看房完成
	 */
	@TableField("status")
	private Integer status;
	/**
	 * 数据创建时间
	 */
	@TableField("create_time")
	private Date createTime;
	/**
	 * 记录更新时间
	 */
	@TableField("last_update_time")
	private Date lastUpdateTime;
	/**
	 * 预约时间
	 */
	@TableField("order_time")
	private Date orderTime;
	/**
	 * 联系电话
	 */
	@TableField("telephone")
	private String telephone;
	/**
	 * 房源发布者id
	 */
	@TableField("admin_id")
	private Integer adminId;

}

