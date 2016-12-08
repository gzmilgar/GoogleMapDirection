package com.example.gizemlgar.mapexample6;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * Created by Gizem İlgar on 28.11.2016.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Zaman doldu!!!!", Toast.LENGTH_LONG).show();
        Vibrator vib=(Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);    //for Vibration
        vib.vibrate(2000);

        Intent i=new Intent(context,Song.class);  //Song class contain media Song
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}