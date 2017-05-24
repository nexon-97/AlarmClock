package com.lab5.denisponyakov.alarmclock.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.lab5.denisponyakov.alarmclock.R;
import com.lab5.denisponyakov.alarmclock.adapter.AlarmDescriptionAdapter;
import com.lab5.denisponyakov.alarmclock.model.AlarmDescription;
import com.lab5.denisponyakov.alarmclock.support.AlarmsListClickListener;
import com.lab5.denisponyakov.alarmclock.support.AlarmsListLongClickListener;
import com.lab5.denisponyakov.alarmclock.support.SessionContextData;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<AlarmDescription> alarmsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        alarmsListAdapter.notifyDataSetChanged();
    }

    public void onAddClockButtonPressed(View view) {
        // Reset temp alarm
        SessionContextData.getInstance().setTempAlarm(null);

        Intent intent = new Intent(this, EditAlarmActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }
}
