package com.srm.starter;

import com.srm.starter.properties.EventThreadPoolProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author haoran
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(EventThreadPoolProperties.class)
public class EventPublishAutoConfiguration {

    @Autowired
    EventThreadPoolProperties eventThreadPoolProperties;

    @Bean
    public EventPublishUtil eventPublishUtil(){
        return new EventPublishUtil();
    }

    @Bean
    public BaseListener baseListener(){
        return new BaseListener(eventThreadPoolExecutor());
    }

    @Bean
    public ThreadPoolExecutor eventThreadPoolExecutor(){

        return new ThreadPoolExecutor(eventThreadPoolProperties.getCorePoolSize(),
                eventThreadPoolProperties.getMaximumPoolSize(),
                eventThreadPoolProperties.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(eventThreadPoolProperties.getWorkQueueSize()));
    }

}
