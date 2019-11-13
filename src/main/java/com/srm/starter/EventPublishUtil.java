package com.srm.starter;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * @author haoran
 */
public class EventPublishUtil implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent(Object obj, String taskCode){
        BaseEvent baseEvent = new BaseEvent(obj,taskCode);
        applicationEventPublisher.publishEvent(baseEvent);
    }


}
