package com.lab5.denisponyakov.alarmclock.support;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.lab5.denisponyakov.alarmclock.R;
import com.lab5.denisponyakov.alarmclock.activity.AlarmActivity;
import com.lab5.denisponyakov.alarmclock.model.Alarm;

public class AlarmsListClickListener implements AdapterView.OnItemClickListener {

    private Activity activity;

    public AlarmsListClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
    {
        Alarm alarm = (Alarm)arg0.getItemAtPosition(position);
        CrudContainer<Alarm> alarmContainer = new CrudContainer<>(Alarm.class).setUpdateMode(alarm);
        SessionContextData.getInstance().setCurrentAlarm(alarmContainer);

        Intent intent = new Intent(activity, AlarmActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }
}
