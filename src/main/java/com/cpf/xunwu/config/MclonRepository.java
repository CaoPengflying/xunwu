/*
 * Copyright (c) 2014-2015, lingang.chen@gmail.com  All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.cpf.xunwu.config;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Reason:	标识MyBatis的Dao,方便{@link org.mybatis.spring.mapper.MapperScannerConfigurer}的扫描。
 *
 * @author Chen LinGang
 * @version $Id: MyBatisRepository, vo 0.1 2015/7/12 12:07
 * @see org.mybatis.spring.mapper.MapperScannerConfigurer
 * @since JDK 1.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface MclonRepository {

    String value() default "";
}
