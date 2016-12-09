package com.example.gizemlgar.mapexample6;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Gizem İlgar on 24.11.2016.
 */

public class GooglePlacesAutocompleteActivity extends AppCompatActivity implements OnMapReadyCallback {

    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    private GoogleMap map;
    private ImageButton imgBtnBack;
    private Toolbar toolbar;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        title=(TextView) findViewById(R.id.title);
        title.setText("Konum Ayarları");
        imgBtnBack = (ImageButton) findViewById(R.id.backButton);
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intocan = new Intent(GooglePlacesAutocompleteActivity.this, GirisEkrani.class);
                startActivity(intocan);
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final TextView txtPlaceDetails = (TextView) findViewById(R.id.txtPlaceDetails);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("", "Place: " + place.getName());

                String placeDetailsStr = place.getName() + "\n"
                        + place.getId() + "\n"
                        + place.getLatLng().toString() + "\n"
                        + place.getAddress();
                txtPlaceDetails.setText(placeDetailsStr);
                double a=place.getLatLng().latitude;
                double b=place.getLatLng().longitude;
                   LatLng loc = new LatLng(a, b);
                map.addMarker(new MarkerOptions().position(loc).title(place.getName().toString()));
                map.moveCamera(CameraUpdateFactory.newLatLng(loc));

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("", "An error occurred: " + status);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        LatLng sydney = new LatLng(40.1888, 29.9689);
        map.addMarker(new MarkerOptions().position(sydney).title("Bilecik Şeyh Edebali Üniversitesi"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Toolbar");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);  // action bar olarak kullanma
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }
    }
