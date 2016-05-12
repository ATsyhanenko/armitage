package org.armitage.inc.AAInfo.setup.config;

import java.util.concurrent.Executor;

import javax.annotation.PreDestroy;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
@PropertySource("classpath:async.properties")
public class AsyncConfig implements AsyncConfigurer{    
    @Autowired
    private Environment environment;
    private static final ThreadPoolTaskExecutor THREADPOOL = new ThreadPoolTaskExecutor();
    
    @Override
    public Executor getAsyncExecutor() {
        THREADPOOL.setCorePoolSize(environment.getProperty("async.core_pool_size", Integer.class));
        THREADPOOL.setMaxPoolSize(environment.getProperty("async.max_pool_size", Integer.class));
        THREADPOOL.setThreadNamePrefix(environment.getProperty("async.thread_name"));
        THREADPOOL.setDaemon(true);
        THREADPOOL.initialize();
        return THREADPOOL;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
    
    @PreDestroy
    public void cleaner(){
        THREADPOOL.destroy();
    }

}
