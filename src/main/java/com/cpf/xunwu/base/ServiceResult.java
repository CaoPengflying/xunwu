package com.cpf.xunwu.base;

import lombok.*;

import java.io.Serializable;

/**
 * @author caopengflying
 * @time 2020/1/26
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ServiceResult<T> implements Serializable {
    private Boolean success;
    private T result;
    private String message;

    public ServiceResult(boolean success, String message){
        this.message = message;
        this.success = success;
    }

}
