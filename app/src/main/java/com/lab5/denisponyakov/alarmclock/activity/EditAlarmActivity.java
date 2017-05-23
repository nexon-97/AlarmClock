package com.lab5.denisponyakov.alarmclock.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.lab5.denisponyakov.alarmclock.R;
import com.lab5.denisponyakov.alarmclock.model.AlarmDescription;
import com.lab5.denisponyakov.alarmclock.support.SessionContextData;

import java.util.List;

public class EditAlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AlarmDescription alarm = SessionContextData.getInstance().getTempAlarm();
        if (alarm != null) {
            EditText nameView = (EditText)findViewById(R.id.alarmName);
            TimePicker timePicker = (TimePicker)findViewById(R.id.alarmTimePicker);

            nameView.setText(alarm.getName());
            nameView.setSelection(alarm.getName().length());
            timePicker.setCurrentHour(alarm.getHour());
            timePicker.setCurrentMinute(alarm.getMinute());
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

        AlarmDescription alarm = new AlarmDescription(alarmName, hour, minute, true);
        SessionContextData.getInstance().setTempAlarm(alarm);
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
        TextView nameView = (TextView)findViewById(R.id.alarmName);
        TimePicker timePicker = (TimePicker)findViewById(R.id.alarmTimePicker);

        String alarmName = nameView.getText().toString();
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        if (alarmName.length() > 0) {
            List<AlarmDescription> alarmsList = SessionContextData.getInstance().getAlarmsList();

            AlarmDescription newAlarm = new AlarmDescription(alarmName, hour, minute, true);
            alarmsList.add(newAlarm);

            onBackPressed();
        } else {
            nameView.setHint("Specify the alarm name!");
        }
    }
}
