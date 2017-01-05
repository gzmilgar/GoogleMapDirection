package com.example.gzmilgar.mapexample;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageButton imgBtnBack,settringButton;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ImageButton bm = (ImageButton) findViewById(R.id.bm);

        bm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String url = "http://bm.bilecik.edu.tr/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        settringButton=(ImageButton) findViewById(R.id.settingButton);
        settringButton.setVisibility(View.INVISIBLE);

        title=(TextView) findViewById(R.id.title);
        title.setText("Hakkında");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Toolbar");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);  // action bar olarak kullanma
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        imgBtnBack=(ImageButton) findViewById(R.id.backButton);
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intocan = new Intent(AboutActivity.this, SplashScreenActivity.class);
                startActivity(intocan);
            }
        });
    }
}
