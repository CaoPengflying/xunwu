package com.cpf.xunwu.config;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * date 2020/7/19
 *
 * @author caopengflying
 */
@Configuration
public class ExecutorConfig {
    @Bean("executorService")
    public ThreadPoolTaskExecutor getExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数目
        executor.setCorePoolSize(10);
//指定最大线程数
        executor.setMaxPoolSize(32);
//队列中最大的数目
        executor.setQueueCapacity(800);
//线程名称前缀
        executor.setThreadNamePrefix("executorService-");
//rejection-policy：当pool已经达到max size的时候，如何处理新任务 //CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行 //对拒绝task的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
//线程空闲后的最大存活时间
        executor.setKeepAliveSeconds(60);
//加载
        executor.initialize();
        return executor;

    }
}
