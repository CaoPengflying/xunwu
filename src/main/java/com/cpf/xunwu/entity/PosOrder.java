package com.cpf.xunwu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.*;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@TableName(value = "pos_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PosOrder implements Serializable {
	@TableId(value = "order_id", type = IdType.AUTO)
	private Integer orderId;
	/**
	 * 销售单据编号
	 */
	@TableField("order_no")
	private String orderNo;
	/**
	 * 单据类型(1销售，2退货，4换货)
	 */
	@TableField("order_type")
	private Integer orderType;
	/**
	 * 定金单id
	 */
	@TableField("deposit_Id")
	private Integer depositId;
	/**
	 * 定金单编号
	 */
	@TableField("deposit_no")
	private String depositNo;
	/**
	 * 促销活动ID
	 */
	@TableField("activity_id")
	private Integer activityId;
	/**
	 * 抵扣类型:微信/线下
	 */
	@TableField("deduct_code")
	private String deductCode;
	/**
	 * 审批类型，1无需审批2负金额，4大类通换
	 */
	@TableField("approval_type")
	private Integer approvalType;
	/**
	 * 顾客ID
	 */
	@TableField("member_id")
	private Integer memberId;
	/**
	 * 顾客卡号
	 */
	@TableField("member_no")
	private String memberNo;
	/**
	 * 顾客姓名
	 */
	@TableField("member_name")
	private String memberName;
	/**
	 * 顾客电话
	 */
	@TableField("member_telephone")
	private String memberTelephone;
	/**
	 * 顾客头像地址
	 */
	@TableField("member_head_url")
	private String memberHeadUrl;
	/**
	 * 销售数量
	 */
	@TableField("quantity")
	private Integer quantity;
	/**
	 * 退回件数，鉴定单退货记录数和抵扣单抵扣记录数之和
	 */
	@TableField("quantity_return")
	private Integer quantityReturn;
	/**
	 * 鉴定单退货记录数
	 */
	@TableField("quantity_return_identify")
	private Integer quantityReturnIdentify;
	/**
	 * 抵扣单抵扣记录数
	 */
	@TableField("quantity_return_deduct")
	private Integer quantityReturnDeduct;
	/**
	 * 所有行记账状态的饰品数量未收讫件数
	 */
	@TableField("quantity_noreceive")
	private Integer quantityNoreceive;
	/**
	 * 销售饰品金额之和
	 */
	@TableField("money_goods")
	private BigDecimal moneyGoods;
	/**
	 * 系统计算应付金额
	 */
	@TableField("calc_money_amount")
	private BigDecimal calcMoneyAmount;
	/**
	 * 应付金额:是否取整为否时同系统计算应付金额,否则为取整后的金额
	 */
	@TableField("money_amount")
	private BigDecimal moneyAmount;
	/**
	 * 已付金额
	 */
	@TableField("money_paid")
	private BigDecimal moneyPaid;
	/**
	 * 退货抵扣
	 */
	@TableField("money_return_identify")
	private BigDecimal moneyReturnIdentify;
	/**
	 * 抵扣单抵扣金额
	 */
	@TableField("money_return_deduct")
	private BigDecimal moneyReturnDeduct;
	/**
	 * 退货抵扣和抵扣单抵扣金额
	 */
	@TableField("money_return")
	private BigDecimal moneyReturn;
	/**
	 * 扣点比率（待确定）
	 */
	@TableField("deduction_rate")
	private BigDecimal deductionRate;
	/**
	 * 扣点金额
	 */
	@TableField("deduction_money")
	private BigDecimal deductionMoney;
	/**
	 * 返点比率
	 */
	@TableField("rebate_rate")
	private BigDecimal rebateRate;
	/**
	 * 返点金额
	 */
	@TableField("rebate_money")
	private BigDecimal rebateMoney;
	/**
	 * 单据状体，具体值需确认，1:保存，2：记账，3：审批中，4:审核完成，？:部分付款,？:已付款,？:已完成,？:已关闭,？:已冲红？已经删除
	 */
	@TableField("status")
	private Integer status;
	/**
	 * 取整标识，0是，1否
	 */
	@TableField("round_off_flag")
	private Boolean roundOffFlag;
	/**
	 * 是否享受会员折扣，0否，1是
	 */
	@TableField("cust_rebate_flag")
	private Boolean custRebateFlag;
	/**
	 * 是否整单收银，0否，1是
	 */
	@TableField("whole_payment_flag")
	private Boolean wholePaymentFlag;
	/**
	 * 备注
	 */
	@TableField("memo")
	private String memo;
	/**
	 * 创建组织ID
	 */
	@TableField("organization_id_create")
	private Integer organizationIdCreate;
	/**
	 * 销售日期
	 */
	@TableField("sales_order_time")
	private Date salesOrderTime;
	/**
	 * 创建时间
	 */
	@TableField("create_time")
	private Date createTime;
	/**
	 * 销售员
	 */
	@TableField("sales_user")
	private Integer salesUser;
	/**
	 * 创建人
	 */
	@TableField("create_user")
	private Integer createUser;
	/**
	 * 确认交接时间
	 */
	@TableField("hand_over_time")
	private Date handOverTime;
	/**
	 * 确认交接人
	 */
	@TableField("hand_over_user")
	private Integer handOverUser;
	/**
	 * 最后修改时间
	 */
	@TableField("update_time")
	private Date updateTime;
	/**
	 * 最后修改人
	 */
	@TableField("update_user")
	private Integer updateUser;
	/**
	 * 性别
	 */
	@TableField("sex")
	private Integer sex;
	/**
	 * 打印时间
	 */
	@TableField("print_time")
	private Date printTime;
	/**
	 * 是否打印姓名
	 */
	@TableField("print_name_flag")
	private Boolean printNameFlag;
	/**
	 * 会员级别
	 */
	@TableField("level")
	private String level;
	/**
	 * 是否支付宝支付0否1是
	 */
	@TableField("is_aliPay")
	private Boolean isAlipay;
	/**
	 * 支付宝商户订单号
	 */
	@TableField("alipay_trade_no")
	private String alipayTradeNo;

}

