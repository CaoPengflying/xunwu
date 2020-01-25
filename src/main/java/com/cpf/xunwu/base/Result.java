package com.cpf.xunwu.base;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author caopengflying
 * @time 2020/1/21
 */
@Getter
@Setter
@ToString
public class Result<T> implements Serializable {

    private String traceId;

    private static final long serialVersionUID = -3951187350403816092L;


    public interface OnlyHintView {

    }

    public interface OnlyObjectView {

    }

    public interface AllView extends OnlyHintView, OnlyObjectView {

    }

    @JsonView({OnlyHintView.class, AllView.class})
    public boolean isSuccess() {
        return StringUtils.equals(getStatus(), ErrorConstant.SUCCESS);
    }

    /**
     * 状态
     */
    @JsonView({OnlyHintView.class, AllView.class})
    private String status;

    /**
     * 状态文本
     */
    @JsonView({OnlyHintView.class, AllView.class})
    private String text;

    /**
     * 描述
     */
    @JsonView({OnlyHintView.class, AllView.class})
    private String description = "";

    /**
     * 内容
     */
    @JsonView({OnlyObjectView.class, AllView.class})
    private T t;

    public long timePoint = System.currentTimeMillis();

    public void timeDesc(String message) {
        description = description + "[" + message + "耗时->" + (System.currentTimeMillis() - timePoint) + "]";
        timePoint = System.currentTimeMillis();
    }

    public Result() {
        this.status = ErrorConstant.SUCCESS;
        this.text = "success";
        this.description = "";
    }

    public Result(T t) {
        this.status = ErrorConstant.SUCCESS;
        this.text = "success";
        this.t = t;
    }

    public Result(String text, String status){
        this.status = status;
        this.text = text;
    }


    public Result(String status, String text, String description) {
        this.status = status;
        this.text = text;
        this.description = description;
    }

    public Result(String status, String text, T t) {
        this.status = status;
        this.text = text;
        this.t = t;
    }

    /**
     * 错误请求编码
     */
    public enum Status {
        SUCCESS(200, "OK"),
        BAD_REQUEST(400, "Bad Request"),
        NOT_FOUND(404, "Not Found"),
        INTERNAL_SERVER_ERROR(500, "Unknown Internal Error"),
        NOT_VALID_PARAM(40005, "Not valid Params"),
        NOT_SUPPORTED_OPERATION(40006, "Operation not supported"),
        NOT_LOGIN(50000, "Not Login");

        Status(Integer code, String errorMsg) {
            this.code = code;
            this.errorMsg = errorMsg;
        }

        private Integer code;
        private String errorMsg;

        public Integer getCode() {
            return code;
        }

        public String getErrorMsg() {
            return errorMsg;
        }
    }
}