package com.roje.game.hall;

import com.roje.game.core.util.SpringUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppHall {
    public static void main(String[] args) {
        SpringUtil.setApplicationContext(new ClassPathXmlApplicationContext("classpath:hall-context.xml"));

    }
}
