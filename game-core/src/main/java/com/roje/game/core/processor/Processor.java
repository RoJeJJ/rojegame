package com.roje.game.core.processor;


import com.google.protobuf.Message;
import com.roje.game.core.thread.ThreadType;
import com.roje.game.message.action.Action;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Processor {
    Action action();
    ThreadType thread() default ThreadType.io;
    boolean forward() default false;
}
