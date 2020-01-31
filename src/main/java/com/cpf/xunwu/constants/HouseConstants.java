package com.cpf.xunwu.constants;

/**
 * @author caopengflying
 * @time 2020/1/28
 */
public class HouseConstants {
    /**
     * 字段枚举
     */
    public enum ColumnsEnum {
        DISTANCE_TO_SUBWAY("distance_to_subway","距离地铁站距离");

        private String code;
        private String value;
        ColumnsEnum(String code, String value){
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * 状态枚举
     */
    public enum StatusEnum {
        NOT_AUDITED(0,"未审核"),
        PASSES(1,"审核通过"),
        RENTED(2,"已出租"),
        DELETED(3,"逻辑删除");

        private Integer code;
        private String value;
        StatusEnum(Integer code, String value){
            this.code = code;
            this.value = value;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
