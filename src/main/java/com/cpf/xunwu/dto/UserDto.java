package com.cpf.xunwu.dto;

import lombok.*;

/**
 * @author caopengflying
 * @time 2020/1/28
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String avatar;
    private String phoneNumber;
    private String lastLoginTime;
}
