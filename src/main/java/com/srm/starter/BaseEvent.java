package com.srm.starter;

import org.springframework.context.ApplicationEvent;

public  class BaseEvent extends ApplicationEvent {

    protected String eventData;

    public BaseEvent(Object source, String eventData){
        super(source);
        this.eventData = eventData;
    }

    public BaseEvent(String eventData){
        super(eventData);
    }

    public String getEventData() {
        return eventData;
    }
    public void setEventData(String eventData) {
        this.eventData = eventData;
    }

}
