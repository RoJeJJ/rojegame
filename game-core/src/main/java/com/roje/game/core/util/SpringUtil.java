package com.roje.game.core.util;

import org.springframework.context.ApplicationContext;

public class SpringUtil {
    private static ApplicationContext context;
    public static void setApplicationContext(ApplicationContext applicationContext){
        context = applicationContext;
    }
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
}
