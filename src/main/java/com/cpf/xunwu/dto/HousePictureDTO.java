package com.cpf.xunwu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author caopengflying
 * @time 2020/1/26
 */
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HousePictureDTO {
    private Long id;

    @JsonProperty(value = "house_id")
    private Long houseId;

    private String path;

    @JsonProperty(value = "cdn_prefix")
    private String cdnPrefix;

    private Integer width;

    private Integer height;

}
