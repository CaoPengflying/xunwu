package com.cpf.xunwu.util;

import com.cpf.xunwu.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author caopengflying
 * @time 2020/1/26
 */
public class LoginUserUtil {
    /**
     * 获取登陆人
     * @return
     */
    public static User load(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User){
            return (User) principal;
        }else {
            return null;
        }
    }

    /**
     * 获取登陆人id
     * @return
     */
    public static Integer getLoginUserId(){
        User load = load();
        if (null == load){
            return -1;
        }
        return load.getId();
    }
}
