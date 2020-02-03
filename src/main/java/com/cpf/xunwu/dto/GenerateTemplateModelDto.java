package com.cpf.xunwu.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author caopengflying
 * @time 2020/1/31
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GenerateTemplateModelDto implements Serializable {
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 数据库地址
     */
    private String host;
    /**
     * 端口号
     */
    private String port;
    /**
     * 数据库
     */
    private String database;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 模块名
     */
    private String moduleName;
    /**
     * 主键Id名
     */
    private String keyId;
    /**
     * 实体名称
     */
    private String entityName;
    /**
     * 作者信息
     */
    private String auth;
    /**
     * 模块描述
     */
    private String desc;
    /**
     * 模板类型
     */
    private Integer templateType;
    /**
     * 是否只生成模型
     */
    private Boolean onlyModelFlag;

    private List<String> columns;
    private List<String> colTypes;
    private List<String> colComment;
    private Boolean importDate;

}
