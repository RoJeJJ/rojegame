package com.roje.game.core.processor;


import com.roje.game.core.thread.ThreadType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Processor {
    int mid();
    ThreadType thread() default ThreadType.io;
    boolean forward() default false;
}
