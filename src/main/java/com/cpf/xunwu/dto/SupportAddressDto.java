package com.cpf.xunwu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class SupportAddressDto implements Serializable {
    private Integer id;
    @JsonProperty("belong_to")
    private String belongTo;
    @JsonProperty("en_name")
    private String enName;
    @JsonProperty("cn_name")
    private String cnName;
    private String level;
}
