package com.srm.starter.handle;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author haoran
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface EventHandle {

    int order();
    String taskCode();
}
