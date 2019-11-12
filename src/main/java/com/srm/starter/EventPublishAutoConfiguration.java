package com.srm.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnWebApplication
public class EventPublishAutoConfiguration {

    @Bean
    public EventPublishUtil eventPublishUtil(){
        return new EventPublishUtil();
    }

    @Bean
    public BaseListener baseListener(){
        return new BaseListener();
    }

}
