package com.cpf.xunwu.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

/**
 * @author caopengflying
 * @time 2020/1/21
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {
    @Value("${spring.thymeleaf.cache}")
    private boolean thymeleafCacheEnable = true;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 资源文件解释器
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.thymeleaf")
    public SpringResourceTemplateResolver resourceTemplateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(thymeleafCacheEnable);
        return templateResolver;
    }

    /**
     * 设置方言
     *
     * @return
     */
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.setTemplateResolver(resourceTemplateResolver());
        //设置支持el表达式
        springTemplateEngine.setEnableSpringELCompiler(true);
        //支持spring security方言
        SpringSecurityDialect springSecurityDialect = new SpringSecurityDialect();
        springTemplateEngine.addDialect(springSecurityDialect);
        return springTemplateEngine;
    }

    /**
     * 设置视图解析器
     * @return
     */
    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine(templateEngine());
        return thymeleafViewResolver;
    }
}


