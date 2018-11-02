package com.roje.game.core.redis.message;

import com.roje.game.core.thread.ThreadType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RedisMsgProcessor {
    String channel();
    ThreadType thread() default ThreadType.io;
}
