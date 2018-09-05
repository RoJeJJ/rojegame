package com.roje.game.core.thread;

import java.util.concurrent.Executor;

public abstract class ExecutorPool implements Executor {
    public abstract void stop();
}
