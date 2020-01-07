package com.cpf.xunwu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName(value = "house")
public class House implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("title")
    private String title;
    /**
     * '价格'
     */
    @TableField("price")

    private Integer price;
    /**
     * '面积'
     */
    @TableField("area")

    private Integer area;
    /**
     * '卧室数量'
     */
    @TableField("room")

    private Integer room;
    /**
     * '楼层'
     */
    @TableField("floor")

    private Integer floor;
    /**
     * '总楼层'
     */
    @TableField("total_floor")

    private Integer totalFloor;
    /**
     * '被看次数'
     */
    @TableField("watch_times")

    private Integer watchTimes;
    /**
     * '建立年限'
     */
    @TableField("build_year")

    private Integer buildYear;
    /**
     * 房屋状态 0-未审核 1-审核通过 2-已出租 3-逻辑删除
     */
    @TableField("status")

    private Integer status;
    /**
     * '创建时间'
     */
    @TableField("create_time")

    private Date createTime;
    /**
     * '最近数据更新时间'
     */
    @TableField("last_update_time")

    private Date lastUpdateTime;
    /**
     * 城市标记缩写 如 北京bj
     */
    @TableField("city_en_name")

    private String cityEnName;
    /**
     * 地区英文简写 如昌平区 cpq
     */
    @TableField("region_en_name")

    private String regionEnName;
    /**
     * '封面'
     */
    @TableField("cover")

    private String cover;
    /**
     * '房屋朝向'
     */
    @TableField("direction")

    private Integer direction;
    /**
     * '距地铁距离 默认-1 附近无地铁'
     */
    @TableField("distance_to_subway")

    private Integer distanceToSubway;
    /**
     * '客厅数量'
     */
    @TableField("parlour")

    private Integer parlour;
    /**
     * '所在小区'
     */
    @TableField("district")

    private String district;
    /**
     * '所属管理员id'
     */
    @TableField("admin_id")

    private Integer adminId;
    @TableField("bathroom")

    private Integer bathroom;
    /**
     * '街道'
     */
    @TableField("street")

    private String street;
}
