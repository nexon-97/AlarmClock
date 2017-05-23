package com.lab5.denisponyakov.alarmclock.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lab5.denisponyakov.alarmclock.R;
import com.lab5.denisponyakov.alarmclock.adapter.AlarmDescriptionAdapter;
import com.lab5.denisponyakov.alarmclock.model.AlarmDescription;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<AlarmDescription> alarmsList = new ArrayList<>();
    private ArrayAdapter<AlarmDescription> alarmsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.AlarmsListView);
        alarmsListAdapter = new AlarmDescriptionAdapter(this, alarmsList);
        listView.setAdapter(alarmsListAdapter);
    }

    public void testButton(View view) {
        Intent intent = new Intent(this, EditAlarmActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }
}
