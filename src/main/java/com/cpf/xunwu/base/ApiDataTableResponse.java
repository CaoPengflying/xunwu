package com.cpf.xunwu.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author caopengflying
 * @time 2020/1/25
 */
@Setter
@Getter
@ToString
public class ApiDataTableResponse extends ApiResponse {
    private int draw;
    private long recordsTotal;
    private long recordsFiltered;

    public ApiDataTableResponse(ApiResponse.Status status) {
        this(status.getCode(), status.getStandardMessage(), null);
    }

    public ApiDataTableResponse(int code, String message, Object data) {
        super(code, message, data);
    }
}
