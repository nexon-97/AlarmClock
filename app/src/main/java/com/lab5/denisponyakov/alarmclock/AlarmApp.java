package com.lab5.denisponyakov.alarmclock;

import android.app.Application;
import android.content.Intent;

import com.lab5.denisponyakov.alarmclock.service.AlarmService;

public class AlarmApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        startService(new Intent(this, AlarmService.class));
    }
}
