package com.lab5.denisponyakov.alarmclock.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.lab5.denisponyakov.alarmclock.R;
import com.lab5.denisponyakov.alarmclock.model.Alarm;
import com.lab5.denisponyakov.alarmclock.service.AlarmService;
import com.lab5.denisponyakov.alarmclock.support.SessionContextData;

import java.io.IOException;
import java.util.List;

public class AlarmRingActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private int alarmId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ring);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String alarmUri = getIntent().getStringExtra("alarmUri");
        alarmId = getIntent().getIntExtra("alarmId", -1);
        Uri myUri = Uri.parse(alarmUri);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(this, myUri);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        // Turn alarm off
        if (alarmId != -1) {
            List<Alarm> alarmList = SessionContextData.getInstance().getAlarmsList();
            Alarm alarm = alarmList.get(alarmId);
            alarm.setIsActive(false);

            SessionContextData.getInstance().saveAlarms();
        }

        AlarmService.alarmIsPlaying = false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onStopPressed(View view) {
        onBackPressed();
    }
}
