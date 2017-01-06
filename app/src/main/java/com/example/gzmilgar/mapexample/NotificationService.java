package com.example.gzmilgar.mapexample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Gizem İlgar on 21.12.2016.
 */

public class NotificationService extends Service {
    Context context ;
    Notification notification;
    Timer timer;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void onCreate() {//Servis startService(); metoduyla çağrıldığında çalışır
        context = getApplicationContext();
        Toast.makeText(this, "Servis Çalıştı.Bu Mesaj Servis Class'dan", Toast.LENGTH_LONG).show();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                bildirimGonder();
            }

        }, 0, 86400);

    }
    public void bildirimGonder(){// Burda servis class dan post edip sunucudan aldığımız değeri bildirim gönderiyoruz.


        int icon = R.drawable.ic_directions_run_white_24dp;//notificationda gösterilecek icon
        long when = System.currentTimeMillis();//notificationın ne zaman gösterileceği
        NotificationManager nm=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent=new Intent(context,GooglePlacesAutocompleteActivity.class);
        PendingIntent  pending= PendingIntent.getActivity(context, 0, intent, 0);//Notificationa tıklanınca açılacak activityi belirliyoruz
        NotificationCompat.Builder b = new NotificationCompat.Builder(context);
        Intent i=new Intent(this,NotificationService.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.stopService(i);
        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_directions_run_white_24dp)
                .setTicker("Hearty365")
                .setContentTitle("Proje II den bildirim var")
                .setContentText("kaç km & kaç dk yol kaldığını buraya yazacak :)")
                .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                .setContentIntent(pending)
                .setContentInfo("Info");
        notification = new Notification(icon, "Yeni Bildirim", when);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;//notificationa tıklanınca notificationın otomatik silinmesi için
        notification.defaults |= Notification.DEFAULT_SOUND;//notification geldiğinde bildirim sesi çalması için
        notification.defaults |= Notification.DEFAULT_VIBRATE;//notification geldiğinde bildirim titremesi için
        nm.notify(1, b.build());

    }
    @Override
    public void onDestroy() {//Servis stopService(); metoduyla durdurulduğunda çalışır
        timer.cancel();
        Toast.makeText(this, "Servis Durduruldu.Bu Mesaj Servis Class'dan", Toast.LENGTH_LONG).show();
    }

}