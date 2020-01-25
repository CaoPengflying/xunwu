package com.cpf.xunwu.base;

/**
 * @author caopengflying
 * @time 2020/1/25
 */
public class BusinessException extends RuntimeException {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BusinessException() {
        super();
    }

    public BusinessException(String message, Throwable cause,
                             boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, String code) {
        super(message);
        this.code = code;
    }
    public BusinessException(String message, Throwable cause, String code) {
        super(message);
        this.code = code;
    }
}
