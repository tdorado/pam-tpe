package com.td.wallendar.utils;

import io.reactivex.Scheduler;

public interface SchedulerProvider {
    Scheduler io();
}
