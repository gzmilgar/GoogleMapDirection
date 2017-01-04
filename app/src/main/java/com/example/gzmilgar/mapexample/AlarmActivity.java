package com.example.gzmilgar.mapexample;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    private Toolbar toolbar;
    TimePicker TimePicker;
    android.widget.DatePicker DatePicker;
    Button Setalarm;
    private ImageButton imgBtnBack,settringButton;

    final static int RQS_1 = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        settringButton=(ImageButton) findViewById(R.id.settingButton);
        settringButton.setVisibility(View.INVISIBLE);
        DatePicker =(DatePicker)findViewById(R.id.datePicker1);
        TimePicker = (TimePicker) findViewById(R.id.timePicker1);
        Calendar now = Calendar.getInstance();

        DatePicker.setVisibility(View.INVISIBLE);
        TimePicker.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
        TimePicker.setCurrentMinute(now.get(Calendar.MINUTE));
        TimePicker.setIs24HourView(DateFormat.is24HourFormat(this));

        DatePicker.init(
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH),
                null);
        Setalarm = (Button) findViewById(R.id.Setalarm);
        Setalarm.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Calendar cal = Calendar.getInstance();
                cal.set(DatePicker.getYear(),
                        DatePicker.getMonth(),
                        DatePicker.getDayOfMonth(),
                        TimePicker.getCurrentHour(),
                        TimePicker.getCurrentMinute(),
                        00);
                setAlarm(cal);
                Intent intent = new Intent(getApplicationContext(),NotificationService.class);
                startService(intent);//Servisi başlatır
            }
        });
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Toolbar");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);  // action bar olarak kullanma
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        imgBtnBack=(ImageButton) findViewById(R.id.backButton);
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intocan = new Intent(AlarmActivity.this, SplashScreenActivity.class);
                startActivity(intocan);
            }
        });

    }


    private void setAlarm(Calendar targetCal) {

        Toast.makeText(AlarmActivity.this, "Alarm ayarlandı " + targetCal.getTime(),
                Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);

    }
}