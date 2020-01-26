package com.cpf.xunwu.constants;

/**
 * @author caopengflying
 * @time 2020/1/26
 */
public class SupportAddressConstants {
    /**
     * 地区级别枚举
     */
    public enum LevelEnum {
        CITY("city","城市"),
        REGION("region","区县");
        private String code;
        private String value;
        LevelEnum(String code, String value){
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
}
