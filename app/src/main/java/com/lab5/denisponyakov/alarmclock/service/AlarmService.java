package com.lab5.denisponyakov.alarmclock.service;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import com.lab5.denisponyakov.alarmclock.activity.AlarmRingActivity;
import com.lab5.denisponyakov.alarmclock.model.Alarm;
import com.lab5.denisponyakov.alarmclock.support.AlarmsListParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AlarmService extends Service {

    private AlarmService serviceContext;
    public static boolean alarmIsPlaying = false;
    private PowerManager.WakeLock fullWakeLock;
    private PowerManager.WakeLock partialWakeLock;

    @Override
    public void onCreate() {
        createWakeLocks();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        serviceContext = this;

        Timer timer = new Timer();
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
                wakeDevice();

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

    private void createWakeLocks(){
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        fullWakeLock = powerManager.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "Loneworker - FULL WAKE LOCK");
        partialWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Loneworker - PARTIAL WAKE LOCK");

    }

    public void wakeDevice() {
        fullWakeLock.acquire();

        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("TAG");
        keyguardLock.disableKeyguard();

        fullWakeLock.release();
    }
}
