package com.roje.game.core.processor;

import com.roje.game.core.thread.ThreadType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface HttpProcessor {
    String path() default "";
    ThreadType thread() default ThreadType.io;
}
