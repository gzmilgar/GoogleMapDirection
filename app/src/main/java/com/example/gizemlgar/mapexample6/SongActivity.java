package com.example.gizemlgar.mapexample6;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SongActivity extends Activity {
    MediaPlayer m;
    private ImageButton imgBtnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        imgBtnBack=(ImageButton) findViewById(R.id.backButton);
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intocan = new Intent(SongActivity.this, SplashScreenActivity.class);
                startActivity(intocan);
            }
        });
        m=MediaPlayer.create(getApplicationContext(), R.raw.saka);
        m.start();

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(SongActivity.this);
        dlgAlert.setTitle("Kalan !");
        dlgAlert.setMessage("Zaman doldu!");
        dlgAlert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                m.stop();
                dialog.cancel();
            }
        });
        dlgAlert.show();
    }

}