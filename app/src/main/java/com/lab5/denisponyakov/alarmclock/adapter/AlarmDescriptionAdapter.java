package com.lab5.denisponyakov.alarmclock.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lab5.denisponyakov.alarmclock.R;
import com.lab5.denisponyakov.alarmclock.model.AlarmDescription;

import java.util.List;

public class AlarmDescriptionAdapter extends ArrayAdapter<AlarmDescription> {

    public AlarmDescriptionAdapter(@NonNull Context context, @NonNull List<AlarmDescription> objects) {
        super(context, R.layout.alarm_list_item, objects);
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {
        AlarmDescription alarmDescription = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.alarm_list_item, parent, false);
        }

        TextView alarmName = (TextView) convertView.findViewById(R.id.alarmName);
        TextView alarmTime = (TextView) convertView.findViewById(R.id.alarmTime);
        ToggleButton activityToggle = (ToggleButton) convertView.findViewById(R.id.alarmToggle);

        alarmName.setText(alarmDescription.getName());
        alarmTime.setText(String.valueOf(alarmDescription.getHour()) + ":" + String.valueOf(alarmDescription.getMinute()));
        activityToggle.setChecked(alarmDescription.getIsActive());

        return convertView;
    }
}
