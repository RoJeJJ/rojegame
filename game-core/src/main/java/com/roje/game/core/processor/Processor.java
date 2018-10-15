package com.roje.game.core.processor;


import com.google.protobuf.MessageLite;
import com.roje.game.core.thread.ThreadType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Processor {
    Class<? extends MessageLite> clazz() default ;
    ThreadType thread() default ThreadType.io;
    boolean forward() default false;
}
