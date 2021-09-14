package com.td.wallendar.utils;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class AndroidSchedulerProvider implements SchedulerProvider{
    @Override
    public Scheduler io() {
        return Schedulers.io();
    }
}
