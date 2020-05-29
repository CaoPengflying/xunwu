package com.cpf.xunwu.base;

import lombok.*;

import java.io.Serializable;

/**
 * date 2020/5/28
 *
 * @author caopengflying
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class HttpClientResult implements Serializable {

    public HttpClientResult(int code){
        this.code = code;
    }

    /**
     * 响应状态码
     */
    private int code;

    /**
     * 响应数据
     */
    private String content;

}
