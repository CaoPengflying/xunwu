package com.mclon.facade.service.api.templateModule.enums;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author TemplateCreate
 * @version fileUtilVersion
 * @description TemplateDesc 枚举
 * @date Created in TemplateCreateDate
 */
public class TemplateNameEnum {
    /**
     * 状态枚举的map
     */
    public static Map<Integer, String> statusMap = Maps.newHashMap();
    /**
     * 字段枚举的map
     */
    public static Map<String, String> columnsMap = Maps.newHashMap();

    static {
        StatusEnum[] statusList = StatusEnum.values();
        for (StatusEnum statusEnum : statusList){
            statusMap.put(statusEnum.getCode(), statusEnum.getName());
        }
        ColumnsEnum[] columnsList = ColumnsEnum.values();
        for (ColumnsEnum columnEnum : columnsList){
            columnsMap.put(columnEnum.getCode(), columnEnum.getName());
        }
    }

    /**
     * 根据状态枚举的code获取中文
     *
     * @param code
     * @return
     */
    public static String getStatusEnumNameByCode(int code) {
        return TemplateNameEnum.statusMap.get(code);
    }

    /**
     * 状态枚举
     */
    public enum StatusEnum {
        DELETE(-1, "已过期");

        private Integer code;
        private String name;

        StatusEnum(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 字段枚举
     */
    
    public enum ColumnsEnum {
__REPLACE_CONTENT

        private String code;
        private String name;

        ColumnsEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }
}
