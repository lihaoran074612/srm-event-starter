package com.srm.starter;

import org.springframework.context.ApplicationEvent;

public  class BaseEvent<T> extends ApplicationEvent {

    protected T eventData;

    public BaseEvent(Object source, T eventData){
        super(source);
        this.eventData = eventData;
    }

    public BaseEvent(T eventData){
        super(eventData);
    }

    public T getEventData() {
        return eventData;
    }
    public void setEventData(T eventData) {
        this.eventData = eventData;
    }

}
