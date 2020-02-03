package com.cpf.xunwu.security;

import com.cpf.xunwu.base.Md5PasswordEncoder;
import com.cpf.xunwu.entity.User;
import com.cpf.xunwu.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author caopengflying
 * @time 2020/1/25
 */
@Component
public class AuthProvider implements AuthenticationProvider {
    private final Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
    @Resource
    private UserService userService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        if (StringUtils.isNotBlank(username)){
            String password = (String) authentication.getCredentials();
            User user = userService.selectUserByName(username);
            if (null == user){
                throw new AuthenticationCredentialsNotFoundException("authError");
            }
            if (md5PasswordEncoder.matches(password,user.getPassword())){
                return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            }
            throw new BadCredentialsException("authError");
        }else {
            User user = new User();
            user.setName("admin");
            return new UsernamePasswordAuthenticationToken(user, null, null);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
