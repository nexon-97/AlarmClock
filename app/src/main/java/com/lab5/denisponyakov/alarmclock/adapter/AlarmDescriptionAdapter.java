package com.lab5.denisponyakov.alarmclock.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lab5.denisponyakov.alarmclock.R;
import com.lab5.denisponyakov.alarmclock.model.Alarm;
import com.lab5.denisponyakov.alarmclock.support.AlarmsListParser;
import com.lab5.denisponyakov.alarmclock.support.SessionContextData;

import java.util.List;
import java.util.Locale;

public class AlarmDescriptionAdapter extends ArrayAdapter<Alarm> {

    public AlarmDescriptionAdapter(@NonNull Context context, @NonNull List<Alarm> objects) {
        super(context, R.layout.alarm_list_item, objects);
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {
        final Alarm alarm = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.alarm_list_item, parent, false);
        }

        TextView alarmName = (TextView) convertView.findViewById(R.id.alarmName);
        TextView alarmTime = (TextView) convertView.findViewById(R.id.alarmTime);
        ToggleButton activityToggle = (ToggleButton) convertView.findViewById(R.id.alarmToggle);

        String timeText = String.format(Locale.ENGLISH, "%02d:%02d", alarm.getHour(), alarm.getMinute());
        alarmName.setText(alarm.getName());
        alarmTime.setText(timeText);
        activityToggle.setChecked(alarm.getIsActive());

        activityToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToggleButton button = (ToggleButton)view;
                alarm.setIsActive(button.isChecked());

                SessionContextData.getInstance().saveAlarms();
            }
        });

        return convertView;
    }
}
