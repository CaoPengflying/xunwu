package com.cpf.xunwu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.*;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@TableName(value = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {
	/**
	 * �û�Ψһid
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * �û���
	 */
	@TableField("name")
	private String name;
	/**
	 * ��������
	 */
	@TableField("email")
	private String email;
	/**
	 * �绰����
	 */
	@TableField("phone_number")
	private String phoneNumber;
	/**
	 * ����
	 */
	@TableField("password")
	private String password;
	/**
	 * �û�״̬0-����1-���
	 */
	@TableField("status")
	private Integer status;
	/**
	 * �û��˺Ŵ���ʱ��
	 */
	@TableField("create_time")
	private Date createTime;
	/**
	 * �ϴε�¼ʱ��
	 */
	@TableField("last_login_time")
	private Date lastLoginTime;
	/**
	 * �ϴθ��¼�¼ʱ��
	 */
	@TableField("last_update_time")
	private Date lastUpdateTime;
	/**
	 * ͷ��
	 */
	@TableField("avatar")
	private String avatar;

}

