package com.roje.game.core.event;

import java.time.LocalTime;

public interface TimerEventHandler {
    default void secondHandler(LocalTime time){}
    default void minuteHandler(LocalTime time){}
    default void hourHandler(LocalTime time){}
}
