package com.lab5.denisponyakov.alarmclock.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.lab5.denisponyakov.alarmclock.R;
import com.lab5.denisponyakov.alarmclock.model.AlarmDescription;

import java.util.List;
import java.util.Locale;

public class AlarmDescriptionAdapter extends ArrayAdapter<AlarmDescription> {

    public AlarmDescriptionAdapter(@NonNull Context context, @NonNull List<AlarmDescription> objects) {
        super(context, R.layout.alarm_list_item, objects);
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {
        final AlarmDescription alarmDescription = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.alarm_list_item, parent, false);
        }

        TextView alarmName = (TextView) convertView.findViewById(R.id.alarmName);
        TextView alarmTime = (TextView) convertView.findViewById(R.id.alarmTime);
        ToggleButton activityToggle = (ToggleButton) convertView.findViewById(R.id.alarmToggle);

        String timeText = String.format(Locale.ENGLISH, "%02d:%02d", alarmDescription.getHour(), alarmDescription.getMinute());
        alarmName.setText(alarmDescription.getName());
        alarmTime.setText(timeText);
        activityToggle.setChecked(alarmDescription.getIsActive());

        activityToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                alarmDescription.setIsActive(b);
            }
        });

        return convertView;
    }
}
