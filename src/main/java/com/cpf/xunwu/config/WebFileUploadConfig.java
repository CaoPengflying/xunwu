package com.cpf.xunwu.config;

import com.baomidou.mybatisplus.extension.api.R;
import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;

/**
 *
 * 文件上传配置
 * @author caopengflying
 * @time 2020/1/26
 */
@Configuration
@ConditionalOnClass({Servlet.class, StandardServletMultipartResolver.class, MultipartConfigElement.class})
@ConditionalOnProperty(prefix = "spring.http.multipart", name = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(MultipartProperties.class)
public class WebFileUploadConfig {
    private final MultipartProperties multipartProperties;

    public WebFileUploadConfig(MultipartProperties multipartProperties) {
        this.multipartProperties = multipartProperties;
    }
    @Bean
    @ConditionalOnMissingBean
    public MultipartConfigElement multipartConfigElement(){
        return this.multipartProperties.createMultipartConfig();
    }

    @Bean(name = DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME)
    @ConditionalOnMissingBean
    public StandardServletMultipartResolver standardServletMultipartResolver(){
        StandardServletMultipartResolver standardServletMultipartResolver = new StandardServletMultipartResolver();
        standardServletMultipartResolver.setResolveLazily(this.multipartProperties.isResolveLazily());
        return standardServletMultipartResolver;
    }

    /**
     * 设置机房
     * @return
     */
    @Bean
    public com.qiniu.storage.Configuration configuration(){
        Region region = Region.region0();
        return new com.qiniu.storage.Configuration(region);
    }

    @Bean
    public UploadManager uploadManager(){
        return new UploadManager(configuration());
    }

    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Bean
    public Auth auth(){
        return Auth.create(accessKey, secretKey);
    }
    @Bean
    public BucketManager bucketManager(){
        return new BucketManager(auth(), configuration());
    }
    @Bean
    public Gson gson(){
        return new Gson();
    }
}
