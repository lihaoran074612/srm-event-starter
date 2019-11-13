package com.srm.starter;

import com.srm.starter.handle.BaseHandle;
import com.srm.starter.handle.EventHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BaseListener implements ApplicationListener<BaseEvent> , ApplicationContextAware {

    Logger logger = LoggerFactory.getLogger(BaseListener.class);

    private ApplicationContext applicationContext;

    /**
     * 线程池； 核心线程数5 最大10 队列10 空闲时间30s
     */
    ThreadPoolExecutor executor = new ThreadPoolExecutor(5,10,30, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(10));

    /**
     * 监听器执行
     * @param baseEvent
     */
    @Override
    public void onApplicationEvent(BaseEvent baseEvent) {
        String TaskCode = (String)baseEvent.getSource();
        String[] beanNamesForType = applicationContext.getBeanNamesForType(BaseHandle.class);

        //根据事件发布的编码，获取对应事件的处理方法
        // eventCode--> Event1Handle --> List<> evet1Handle
        List<BaseHandle> baseHandles = new ArrayList<>();
        for (String s: beanNamesForType) {
            BaseHandle bean = applicationContext.getBean(s, BaseHandle.class);
            EventHandle annotation = bean.getClass().getAnnotation(EventHandle.class);
            String code = annotation.TaskCode();
            if (code != null && code.equals(TaskCode)){
                baseHandles.add(bean);
            }
        }

        //事件的处理器执行(根据order排序)
        Queue<BaseHandle> baseHandleSort = sortBaseHandle(baseHandles);
        execute(baseHandleSort);
        //不受事件处理影响，直接往下执行（异步处理）
        logger.info("登录成功，当前线程:"+Thread.currentThread().getName()+"  监听到了事件 IP地址为: "+"localhost:");
    }

    /**
     *  事件执行
     * @param baseHandleSort
     * @param
     */
    public void execute(Queue<BaseHandle> baseHandleSort){
       Runnable runnable= new Runnable(){
            @Override
            public void run() {
                baseHandleSort.stream().forEach(baseHandle -> {
                    Method[] methods = baseHandle.getClass().getDeclaredMethods();
                    Arrays.stream(methods).forEach(method->{
                        try {
                            Thread.sleep(2000);
                            method.invoke(baseHandle);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                });
            }
        };
        executor.execute(runnable);
    }

    /**
     * 给BaseHandle排序（根据order注解）
     */
    public Queue<BaseHandle> sortBaseHandle(List<BaseHandle> list){
        Queue<BaseHandle> baseHandles = new LinkedList<>();
        if (list.size()!=0) {
            Map<Integer, BaseHandle> map = new HashMap<>();
            list.stream().forEach(baseHandle -> {
                EventHandle annotation = baseHandle.getClass().getAnnotation(EventHandle.class);
                int order = annotation.order();
                map.put(order, baseHandle);
            });
            Set<Integer> integers = map.keySet();
            List<Integer> sort = new ArrayList<>();
            integers.stream().forEach(integer -> sort.add(integer));
            Collections.sort(sort);
            sort.stream().forEach(integer -> {
                baseHandles.offer(map.get(integer));
            });
            return baseHandles;
        }
        return baseHandles;
    }

    /**
     * 给当前类注入ioc容器
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
