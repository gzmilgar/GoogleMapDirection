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

import java.text.DecimalFormat;
import java.text.NumberFormat;
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
    GPSTracker gps;
   public String knm;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void onCreate() {//Servis startService(); metoduyla çağrıldığında çalışır
        context = getApplicationContext();
        Toast.makeText(this, "Servis Çalıştı.Bu Mesaj Servis Class'dan", Toast.LENGTH_LONG).show();

        gpsLocation();
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
                .setSmallIcon(icon)
                .setTicker("Hearty365")
                .setContentTitle("Alarm GPS varış süreniz")
                .setContentText(""+knm)
                .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                .setContentIntent(pending)
                .setContentInfo("Info");
        notification = new Notification(icon, "Yeni Bildirim", when);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;//notificationa tıklanınca notificationın otomatik silinmesi için
        notification.defaults |= Notification.DEFAULT_SOUND;//notification geldiğinde bildirim sesi çalması için
        notification.defaults |= Notification.DEFAULT_VIBRATE;//notification geldiğinde bildirim titremesi için
        nm.notify(1, b.build());

    }

    public void gpsLocation()
    {
        gps = new GPSTracker(context);

        if(gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            knm="" + CalculationByDistance(latitude,longitude,40.6123375,42.976752799999986);
            Toast.makeText(context, "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            gps.showSettingsAlert();
        }
    }


    public String CalculationByDistance(double lat1, double lon1,double lat2, double lon2) {
        double Radius = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double temp = Radius * c;
        temp=temp*0.621*2;
        DecimalFormat df=new DecimalFormat("0.00");
        String formate = df.format(temp);

        double t=temp/100;
        String str;
        int i = (int) Double.parseDouble(String.valueOf(t));
        int sa=  (int) Double.parseDouble(String.valueOf(t));
        int dk=(int)temp%60;
        int day=0;

        if(i>59)
        {
            sa=i/60;
        }

         str=formate+" km "+sa+" sa " +dk+" dk";
        if(sa<1)
        {
            str=formate+" km "+dk+" dk";
        }

        if(sa>23)
        {
            day=sa/24;
            sa=sa%24;
        }
        if(day>0)
        {
            str=formate+" km "+day+" gün"+sa+" sa " +dk+" dk";
        }
        return str;
    }


    @Override
    public void onDestroy() {//Servis stopService(); metoduyla durdurulduğunda çalışır
        timer.cancel();
        Toast.makeText(this, "Servis Durduruldu.Bu Mesaj Servis Class'dan", Toast.LENGTH_LONG).show();
    }

}