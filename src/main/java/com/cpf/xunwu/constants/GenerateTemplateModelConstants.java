package com.cpf.xunwu.constants;

/**
 * @author caopengflying
 * @time 2020/1/31
 */
public class GenerateTemplateModelConstants {
    /**
     * 曼卡龙项目名称
     */
    public static final String MCLON_PROJECT_NAME = "mclon";
    /**
     * 曼卡龙项目前缀
     */
    public static final String MCLON_TABLE_PRE = "mkl";

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
