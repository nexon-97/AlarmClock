package com.lab5.denisponyakov.alarmclock.support;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.lab5.denisponyakov.alarmclock.R;
import com.lab5.denisponyakov.alarmclock.model.AlarmDescription;

public class AlarmsListLongClickListener implements AdapterView.OnItemLongClickListener  {

    private Activity activity;

    public AlarmsListLongClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3)
    {
        AlarmDescription alarm = (AlarmDescription)arg0.getItemAtPosition(position);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.activity);
        alertDialog.setTitle("Pick action");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this.activity, android.R.layout.select_dialog_item);
        arrayAdapter.add("Remove");
        arrayAdapter.add("Edit");

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(null);
                builderInner.setMessage(strName);
                builderInner.setTitle("Your Selected Item is");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();*/
            }
        });
        alertDialog.show();

        return true;
    }
}
