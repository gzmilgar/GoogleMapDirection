package com.example.gzmilgar.mapexample;


import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Button;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AddPlaceRequest;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private ImageButton imgBtnBack,settringButton;
    private TextView title;
    private Button addTodoBtn;
    private EditText descEditText;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        title=(TextView) findViewById(R.id.title);
        title.setText("Konum Ayarları");
        settringButton=(ImageButton) findViewById(R.id.settingButton);
        settringButton.setVisibility(View.INVISIBLE);

        imgBtnBack=(ImageButton) findViewById(R.id.backButton);
        imgBtnBack = (ImageButton) findViewById(R.id.backButton);
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intocan = new Intent(LocationActivity.this, SplashScreenActivity.class);
                startActivity(intocan);
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_location);
        autocompleteFragment.setHint("Konum seçmek için tıklayınız");
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                double a=place.getLatLng().latitude;
                double b=place.getLatLng().longitude;
                LatLng loc = new LatLng(a, b);
                map.addMarker(new MarkerOptions().position(loc).title(place.getName().toString()));
                map.moveCamera(CameraUpdateFactory.newLatLng(loc));
                map.animateCamera(CameraUpdateFactory.zoomTo(15));
                Log.i("", "Place: " + place.getName());
                final String name = loc.toString();
                final String desc =  place.getName().toString();
                dbManager.insert(name, desc);

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("", "An error occurred: " + status);
            }
        });




        setTitle("Konum Ekleyiniz");
        descEditText = (EditText) findViewById(R.id.description_edittext);
        addTodoBtn = (Button) findViewById(R.id.add_record);
        dbManager = new DBManager(this);
        dbManager.open();
        addTodoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              //  final String name = subjectEditText.getText().toString();
               // final String desc = descEditText.getText().toString();
               // dbManager.insert(name, desc);
                Intent main = new Intent(LocationActivity.this, CountryListActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main);
            }
        });




    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }
}
