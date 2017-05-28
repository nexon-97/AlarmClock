package com.lab5.denisponyakov.alarmclock.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.lab5.denisponyakov.alarmclock.R;
import com.lab5.denisponyakov.alarmclock.model.AlarmDescription;
import com.lab5.denisponyakov.alarmclock.support.CrudAction;
import com.lab5.denisponyakov.alarmclock.support.CrudContainer;
import com.lab5.denisponyakov.alarmclock.support.SessionContextData;

import java.util.List;

public class AlarmActivity extends AppCompatActivity {

    final int PickMusicRequest = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AlarmDescription alarm = SessionContextData.getInstance().getCurrentAlarm().getObject();
        if (alarm != null) {
            EditText nameView = (EditText)findViewById(R.id.alarmName);
            TimePicker timePicker = (TimePicker)findViewById(R.id.alarmTimePicker);

            nameView.setText(alarm.getName());
            nameView.setSelection(alarm.getName().length());
            timePicker.setCurrentHour(alarm.getHour());
            timePicker.setCurrentMinute(alarm.getMinute());
            updateSongInfoLabel(alarm.getSongUri());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        EditText nameView = (EditText)findViewById(R.id.alarmName);
        TimePicker timePicker = (TimePicker)findViewById(R.id.alarmTimePicker);

        String alarmName = nameView.getText().toString();
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        AlarmDescription alarm = SessionContextData.getInstance().getCurrentAlarm().getObject();
        alarm.setName(alarmName);
        alarm.setHour(hour);
        alarm.setMinute(minute);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.slide_out_down, R.anim.slide_in_down);
    }

    public void onCancelPressed(View view) {
        onBackPressed();
    }

    public void onConfirmPressed(View view) {
        CrudContainer<AlarmDescription> alarmContainer = SessionContextData.getInstance().getCurrentAlarm();
        AlarmDescription alarm = alarmContainer.getObject();

        TextView nameView = (TextView)findViewById(R.id.alarmName);
        String alarmName = nameView.getText().toString();

        if (alarmName.length() == 0) {
            Toast.makeText(this, "No alarm name specified!", Toast.LENGTH_SHORT).show();
        } else if (alarm.getSongUri() == null) {
            Toast.makeText(this, "No alarm song specified!", Toast.LENGTH_SHORT).show();
        } else {
            TimePicker timePicker = (TimePicker)findViewById(R.id.alarmTimePicker);
            int hour = timePicker.getCurrentHour();
            int minute = timePicker.getCurrentMinute();

            // Update object data
            alarm.setName(alarmName);
            alarm.setHour(hour);
            alarm.setMinute(minute);

            if (alarmContainer.getAction().equals(CrudAction.Create)) {
                List<AlarmDescription> alarmsList = SessionContextData.getInstance().getAlarmsList();
                alarm.setIsActive(true);
                alarmsList.add(alarmContainer.getObject());
            } else if (alarmContainer.getAction().equals(CrudAction.Update)) {
                alarmContainer.commit();
            }

            onBackPressed();
        }
    }

    public void onMusicChooseClick(View view) {
        Intent mediaIntent = new Intent(Intent.ACTION_GET_CONTENT);
        mediaIntent.setType("audio/*");
        startActivityForResult(mediaIntent, PickMusicRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PickMusicRequest && resultCode == Activity.RESULT_OK) {
            Uri audioUri = data.getData();
            SessionContextData.getInstance().getCurrentAlarm().getObject().setSongUri(audioUri);

            updateSongInfoLabel(audioUri);
        }
    }

    private void updateSongInfoLabel(Uri audioUri) {
        TextView alarmSongUrlView = (TextView)findViewById(R.id.alarmSongView);

        if (audioUri == null) {
            alarmSongUrlView.setText(getResources().getString(R.string.audio_not_set));
        } else {
            MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
            metaRetriever.setDataSource(this, audioUri);

            String artist =  metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            String title = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);

            if (artist == null && title == null) {
                alarmSongUrlView.setText(audioUri.toString());
            } else {
                alarmSongUrlView.setText(String.format("%s - %s", artist, title));
            }
        }
    }
}
