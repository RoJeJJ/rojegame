package com.roje.game.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext context;
    public static ApplicationContext getContext(){
        return context;
    }
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanId){
        return (T) context.getBean(beanId);
    }
    public static <T> T getBean(Class<T> tClass){
        return context.getBean(tClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
