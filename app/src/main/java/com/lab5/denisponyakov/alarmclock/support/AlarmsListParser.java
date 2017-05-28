package com.lab5.denisponyakov.alarmclock.support;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import com.lab5.denisponyakov.alarmclock.activity.MainActivity;
import com.lab5.denisponyakov.alarmclock.model.AlarmDescription;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class AlarmsListParser {

    public void save(List<AlarmDescription> alarms) {
        JSONArray alarmsArray = new JSONArray();

        List<AlarmDescription> alarmsList = SessionContextData.getInstance().getAlarmsList();
        for (AlarmDescription alarm : alarmsList) {
            JSONObject alarmObject = new JSONObject();

            try {
                alarmObject.put("name", alarm.getName());
                alarmObject.put("hour", alarm.getHour());
                alarmObject.put("minute", alarm.getMinute());
                alarmObject.put("songUri", alarm.getSongUri().toString());
                alarmObject.put("isActive", alarm.getIsActive());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            alarmsArray.put(alarmObject);
        }

        try {
            Activity activity = MainActivity.get();
            FileOutputStream outputStream = activity.openFileOutput(MainActivity.alarmsStorageLocation, Context.MODE_PRIVATE);
            outputStream.write(alarmsArray.toString().getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(List<AlarmDescription> alarms) {
        alarms.clear();

        try {
            Activity activity = MainActivity.get();
            FileInputStream inputStream = activity.openFileInput(MainActivity.alarmsStorageLocation);

            byte[] buffer = new byte[(int) inputStream.getChannel().size()];
            inputStream.read(buffer);

            try {
                JSONArray alarmsListJson = new JSONArray(new String(buffer));

                int count = alarmsListJson.length();
                for (int i = 0; i < count; i++) {
                    AlarmDescription alarm = new AlarmDescription();
                    JSONObject alarmObject = (JSONObject)alarmsListJson.get(i);

                    alarm.setName(alarmObject.getString("name"));
                    alarm.setHour(alarmObject.getInt("hour"));
                    alarm.setMinute(alarmObject.getInt("minute"));
                    alarm.setSongUri(Uri.parse(alarmObject.getString("songUri")));
                    alarm.setIsActive(alarmObject.getBoolean("isActive"));

                    alarms.add(alarm);
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
