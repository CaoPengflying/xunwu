package com.cpf.xunwu.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * date 2020/5/28
 *
 * @author caopengflying
 */
@Configuration
@MapperScan(value = {"com.cpf.xunwu.mclon.mapper"}
        ,sqlSessionFactoryRef = "sqlSessionFactory1",annotationClass = MclonRepository.class)
public class MclonMybatisPlusConfig {
    @Value("${mclon.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${mclon.datasource.url}")
    private String jdbcUrl;
    @Value("${mclon.datasource.username}")
    private String userName;
    @Value("${mclon.datasource.password}")
    private String password;
/*    *//**
     * 分页插件
     *//*
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }*/
    @Bean(name = "transactionManager1")
    public TransactionManager getTransactionManager(@Qualifier(value = "dataSource1") DataSource dataSource){
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }
    @Bean(name = "dataSource1")
    public DataSource getDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }
    @Bean(name = "sqlSessionFactory1")
    public SqlSessionFactory getSqlSessionFactory(@Qualifier(value = "dataSource1") DataSource dataSource) throws IOException {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactory.setMapperLocations(resolver.getResources("classpath:mclon/*Mapper.xml"));
        try {
            return sqlSessionFactory.getObject();
        } catch (Exception e) {
            return null;
        }
    }

}
