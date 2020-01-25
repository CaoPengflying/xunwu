package com.cpf.xunwu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.*;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.beans.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@TableName(value = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable,UserDetails {
    /**
     * 用户唯一id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户名
     */
    @TableField("name")
    private String name;
    /**
     * 电子邮箱
     */
    @TableField("email")
    private String email;
    /**
     * 电话号码
     */
    @TableField("phone_number")
    private String phoneNumber;
    /**
     * 密码
     */
    @TableField("password")
    private String password;
    /**
     * 用户状态0-正常1-封禁
     */
    @TableField("status")
    private Integer status;
    /**
     * 用户账号创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 上次登录时间
     */
    @TableField("last_login_time")
    private Date lastLoginTime;
    /**
     * 上次更新记录时间
     */
    @TableField("last_update_time")
    private Date lastUpdateTime;
    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;
    @TableField(exist = false)
    private List<GrantedAuthority> authorityList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

