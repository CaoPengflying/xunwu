package com.cpf.xunwu.constants;

/**
 * @author caopengflying
 * @time 2020/1/31
 */
public class GenerateTemplateModelConstants {
    public enum TemplateTypeEnum {
        MYBATIS_PLUS_BASIC(1, "标准mybatis-plus"),
        MYBATIS_PLUS_FORM(2, "表单mybatis-plus"),
        TKMAPPER_BASIC(3, "标准TKMapper"),
        TKMAPPER_FORM(4, "表单TkMapper");
        private Integer code;
        private String value;
        TemplateTypeEnum(Integer code, String value){
            this.code = code;
            this.value = value;
        }
        public Integer getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }
}
