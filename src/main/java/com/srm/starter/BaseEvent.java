package com.srm.starter;

import org.springframework.context.ApplicationEvent;

/**
 * @author haoran
 */
public  class BaseEvent extends ApplicationEvent {

    protected String taskCode;

    public BaseEvent(Object source, String taskCode){
        super(source);
        this.taskCode = taskCode;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }
}
