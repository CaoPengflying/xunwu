package com.cpf.xunwu.config;

import com.cpf.xunwu.base.Md5PasswordEncoder;
import com.cpf.xunwu.security.AuthProvider;
import com.cpf.xunwu.security.LoginFailHandler;
import com.cpf.xunwu.security.LoginUrlEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.annotation.Resource;

/**
 * @author caopengflying
 * @time 2020/1/25
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private AuthProvider authProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 资源访问权限
        http.authorizeRequests()
                .antMatchers("/admin/login").permitAll() // 管理员登录入口
                .antMatchers("/static/**").permitAll() // 静态资源
                .antMatchers("/user/login").permitAll() // 用户登录入口
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/user/**").hasAnyRole("ADMIN",
                "USER")
                .and()
                .formLogin()
                .loginProcessingUrl("/login") // 配置角色登录处理入口
                .failureHandler(authFailHandler())
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logout/page")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(loginUrlEntryPoint())
                .accessDeniedPage("/403");

        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
    }

    @Bean
    public AuthenticationFailureHandler authFailHandler() {
        return new LoginFailHandler(loginUrlEntryPoint());
    }

    /**
     * 自定义鉴权
     *
     * @param auth
     */
    @Autowired
    public void configGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider).eraseCredentials(true);
    }

    @Autowired
    public PasswordEncoder passwordEncoder() {
        return new Md5PasswordEncoder();
    }

    @Bean
    public LoginUrlEntryPoint loginUrlEntryPoint() {
        return new LoginUrlEntryPoint("/user/login");
    }
}
