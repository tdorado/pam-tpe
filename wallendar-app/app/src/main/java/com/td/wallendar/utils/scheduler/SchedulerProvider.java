package com.td.wallendar.utils.scheduler;

import io.reactivex.Scheduler;

public interface SchedulerProvider {
    Scheduler io();

    Scheduler computation();

    Scheduler ui();
}
