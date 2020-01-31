package com.cpf.xunwu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cpf.xunwu.base.ServiceResult;
import com.cpf.xunwu.dto.UserDto;
import com.cpf.xunwu.entity.User;
public interface UserService extends IService<User> {
    /**
     * 根据姓名查询用户
     * @param username
     * @return
     */
    User selectUserByName(String username);

    /**
     * 根据id查询明细
     * @param adminId
     * @return
     */
    ServiceResult<UserDto> getDetailById(Long adminId);
}

