package com.cpf.xunwu.security;

import com.google.common.collect.Maps;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author caopengflying
 * @time 2020/1/25
 */
public class LoginUrlEntryPoint extends LoginUrlAuthenticationEntryPoint {

    private final Map<String, String> authEntryPointMap;

    private PathMatcher pathMatcher = new AntPathMatcher();

    public LoginUrlEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
        authEntryPointMap = Maps.newHashMap();
        authEntryPointMap.put("/user/**", "/user/login");
        authEntryPointMap.put("/admin/**", "/admin/login");
    }

    @Override
    protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        String uri = request.getRequestURI().replace(request.getContextPath(), "");
        for (Map.Entry<String, String> authEntry : authEntryPointMap.entrySet()) {
            if (pathMatcher.match(authEntry.getKey(),uri)){
                return authEntry.getValue();
            }
        }
        return super.determineUrlToUseForThisRequest(request, response, exception);
    }
}
