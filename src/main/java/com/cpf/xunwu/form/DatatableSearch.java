package com.cpf.xunwu.form;

import java.io.Serializable;
import java.util.Date;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author caopengflying
 * @time 2020/1/23
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DatatableSearch implements Serializable {
    /**
     * Datatables要求回显字段
     */
    private int draw;

    /**
     * Datatables规定分页字段
     */
    private int start;
    private int length;

    private Integer status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTimeMin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTimeMax;

    private String city;
    private String title;
    private String direction;
    private String orderBy;
}
