package com.lab5.denisponyakov.alarmclock.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lab5.denisponyakov.alarmclock.R;
import com.lab5.denisponyakov.alarmclock.adapter.AlarmDescriptionAdapter;
import com.lab5.denisponyakov.alarmclock.model.AlarmDescription;
import com.lab5.denisponyakov.alarmclock.support.AlarmsListClickListener;
import com.lab5.denisponyakov.alarmclock.support.AlarmsListLongClickListener;
import com.lab5.denisponyakov.alarmclock.support.AlarmsListParser;
import com.lab5.denisponyakov.alarmclock.support.CrudContainer;
import com.lab5.denisponyakov.alarmclock.support.SessionContextData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static Activity instance;

    public final static String alarmsStorageLocation = "alarms.json";
    private ArrayAdapter<AlarmDescription> alarmsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;

        List<AlarmDescription> alarmsList = SessionContextData.getInstance().getAlarmsList();
        alarmsListAdapter = new AlarmDescriptionAdapter(this, alarmsList);

        ListView alarmsListView = (ListView)findViewById(R.id.AlarmsListView);
        alarmsListView.setAdapter(alarmsListAdapter);

        alarmsListView.setOnItemClickListener(new AlarmsListClickListener(this));
        alarmsListView.setOnItemLongClickListener(new AlarmsListLongClickListener(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshAlarmsList();
    }

    @Override
    protected void onPause() {
        new AlarmsListParser().save(SessionContextData.getInstance().getAlarmsList());

        super.onPause();
    }

    public void onAddClockButtonPressed(View view) {
        CrudContainer<AlarmDescription> alarmContainer = new CrudContainer<>(AlarmDescription.class).setCreateMode();
        Date currentTime = new Date();
        alarmContainer.getObject().setHour(currentTime.getHours());
        alarmContainer.getObject().setMinute(currentTime.getMinutes());

        SessionContextData.getInstance().setCurrentAlarm(alarmContainer);

        Intent intent = new Intent(this, AlarmActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    public void refreshAlarmsList() {
        alarmsListAdapter.notifyDataSetChanged();
    }

    public static Activity get() {
        return instance;
    }
}
