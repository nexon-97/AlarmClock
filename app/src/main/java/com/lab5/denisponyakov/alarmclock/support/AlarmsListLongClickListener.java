package com.lab5.denisponyakov.alarmclock.support;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.lab5.denisponyakov.alarmclock.R;
import com.lab5.denisponyakov.alarmclock.activity.AlarmActivity;
import com.lab5.denisponyakov.alarmclock.activity.AlarmRingActivity;
import com.lab5.denisponyakov.alarmclock.activity.MainActivity;
import com.lab5.denisponyakov.alarmclock.model.Alarm;

public class AlarmsListLongClickListener implements AdapterView.OnItemLongClickListener  {

    private Activity activity;
    private AlertDialog pickActionDialog;
    private int selectedPosition;

    public AlarmsListLongClickListener(Activity activity) {
        this.activity = activity;

        pickActionDialog = buildPickActionDialog();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3)
    {
        selectedPosition = position;
        pickActionDialog.show();

        return true;
    }

    private AlertDialog buildPickActionDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this.activity);
        dialog.setTitle(getResourceString(R.string.pick_action));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this.activity, android.R.layout.select_dialog_item);
        arrayAdapter.add(getResourceString(R.string.remove));
        arrayAdapter.add(getResourceString(R.string.edit));

        dialog.setNegativeButton(getResourceString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            final int removeAlarmId = 0;
            final int editAlarmId = 1;

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case removeAlarmId:
                        onRemoveAlarmAction();
                        break;
                    case editAlarmId:
                        onEditAlarmAction();
                        break;
                }
            }
        });

        return dialog.create();
    }

    private void onEditAlarmAction() {
        Alarm alarm = SessionContextData.getInstance().getAlarmsList().get(selectedPosition);
        CrudContainer<Alarm> alarmContainer = new CrudContainer<>(Alarm.class).setUpdateMode(alarm);
        SessionContextData.getInstance().setCurrentAlarm(alarmContainer);

        Intent intent = new Intent(activity, AlarmActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    private void onRemoveAlarmAction() {
        SessionContextData.getInstance().getAlarmsList().remove(selectedPosition);
        MainActivity mainActivity = (MainActivity)activity;
        mainActivity.refreshAlarmsList();
    }

    @NonNull
    private String getResourceString(int resourceId) {
        return activity.getResources().getString(resourceId);
    }
}
