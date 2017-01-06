package com.example.gzmilgar.mapexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Gizem İlgar on 28.11.2016.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Zaman doldu!!!!", Toast.LENGTH_LONG).show();

        Intent i=new Intent(context,NotificationService.class);
               i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               context.startService(i);

    }

}