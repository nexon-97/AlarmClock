package com.lab5.denisponyakov.alarmclock.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.lab5.denisponyakov.alarmclock.R;
import com.lab5.denisponyakov.alarmclock.model.AlarmDescription;
import com.lab5.denisponyakov.alarmclock.support.CrudAction;
import com.lab5.denisponyakov.alarmclock.support.CrudContainer;
import com.lab5.denisponyakov.alarmclock.support.SessionContextData;

import java.util.List;

public class AlarmActivity extends AppCompatActivity {

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
        TextView nameView = (TextView)findViewById(R.id.alarmName);
        TimePicker timePicker = (TimePicker)findViewById(R.id.alarmTimePicker);

        String alarmName = nameView.getText().toString();
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        if (alarmName.length() > 0) {
            CrudContainer<AlarmDescription> alarmContainer = SessionContextData.getInstance().getCurrentAlarm();
            AlarmDescription alarm = alarmContainer.getObject();

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
        } else {
            nameView.setHint(getResources().getString(R.string.hint_specify_alarm_name));
        }
    }
}
