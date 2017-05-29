package com.lab5.denisponyakov.alarmclock.service;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lab5.denisponyakov.alarmclock.activity.AlarmRingActivity;
import com.lab5.denisponyakov.alarmclock.model.Alarm;
import com.lab5.denisponyakov.alarmclock.support.AlarmsListParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AlarmService extends Service {

    private Timer timer;
    private AlarmService serviceContext;
    public static boolean alarmIsPlaying = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        serviceContext = this;

        timer = new Timer();
        timer.scheduleAtFixedRate(new AlarmTimerTask(), 0, 2000);

        return super.onStartCommand(intent, flags, startId);
    }

    private void checkAlarmState() {
        if (alarmIsPlaying)
            return;

        AlarmsListParser parser = new AlarmsListParser();
        List<Alarm> alarms = new ArrayList<>();
        parser.load(alarms);

        Date currentTime = new Date();
        int currentHour = currentTime.getHours();
        int currentMinute = currentTime.getMinutes();

        for (int i = 0; i < alarms.size(); i++) {
            Alarm alarm = alarms.get(i);

            if (alarm.getIsActive() && alarm.getHour() == currentHour && alarm.getMinute() == currentMinute) {
                PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
                PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
                wakeLock.acquire();
                wakeLock.release();

                KeyguardManager keyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
                KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("TAG");
                keyguardLock.disableKeyguard();

                Intent alarmIntent = new Intent(this, AlarmRingActivity.class);
                alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                alarmIntent.putExtra("alarmUri", alarm.getSongUri().toString());
                alarmIntent.putExtra("alarmId", i);

                startActivity(alarmIntent);
                alarmIsPlaying = true;

                break;
            }
        }
    }

    private class AlarmTimerTask extends TimerTask
    {
        public void run()
        {
            serviceContext.checkAlarmState();
        }
    }
}
