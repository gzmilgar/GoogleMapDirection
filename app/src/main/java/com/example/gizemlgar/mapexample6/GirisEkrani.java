package com.example.gizemlgar.mapexample6;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

public class GirisEkrani extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private Toolbar toolbar;
    private Button btnAlarm,btnKonum;
    private ImageButton imgBtnBack,settringButton;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        title=(TextView) findViewById(R.id.title);
        title.setText("Proje 2 Giriş");
        imgBtnBack=(ImageButton) findViewById(R.id.backButton);
        btnAlarm=(Button) findViewById(R.id.btnAlarm);
        btnKonum=(Button) findViewById(R.id.btnKonum);

        imgBtnBack.setVisibility(View.INVISIBLE);//geri butonu nu gorunmez yaptık

        settringButton=(ImageButton) findViewById(R.id.settingButton);
        settringButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(GirisEkrani.this, v);
                popupMenu.setOnMenuItemClickListener(GirisEkrani.this);
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.show();
            }
        });

        btnAlarm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intocan = new Intent(GirisEkrani.this, AlarmActivity.class);
                startActivity(intocan);
            }
        });

        btnKonum.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(GirisEkrani.this, GooglePlacesAutocompleteActivity.class);
                startActivity(intent);
            }
        });
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Toolbar");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);  // action bar olarak kullanma
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_hakiinda:
                Intent intocan = new Intent(GirisEkrani.this, HakkindaActivity.class);
                startActivity(intocan);
                return true;
        }
        return true;
    }
}
