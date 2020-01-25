package com.cpf.xunwu.base;

import com.cpf.xunwu.util.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author caopengflying
 * @time 2020/1/25
 */
public class Md5PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return MD5Util.encode((String)rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(MD5Util.encode((String)rawPassword));

    }
}
