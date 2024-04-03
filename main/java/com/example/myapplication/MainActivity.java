package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    LocationManager lM;
    TextView lat, lon, curr, tDT;
    TextView add1, add2, add3, tim1, tim2, tim3;
    double latiti, longit;
    List<String> addresses, current;
    Location lastLoc, loc;
    float distance;
    //ListView lV;
    long t, entryTime, sum, exitTime, timeSpent;
    String NewAddress;
    int mul = 0;

    @SuppressLint({"MissingInflatedId", "MissingPermission", "ServiceCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lat = findViewById(R.id.textView2);
        lon = findViewById(R.id.textView3);
        curr = findViewById(R.id.textView4);
        tDT = findViewById(R.id.textView);

        add1 = findViewById(R.id.textView9);
        add2 = findViewById(R.id.textView11);
        add3 = findViewById(R.id.textView7);
        tim1 = findViewById(R.id.textView10);
        tim2 = findViewById(R.id.textView12);
        tim3 = findViewById(R.id.textView8);

        lastLoc = new Location("LastLocation");

        distance = 0.0f;
        //lV = findViewById(R.id.list_1);
        addresses = new ArrayList<String>();
        t = 0;
        current = new ArrayList<String>();

        tDT.setText("Total Distance Travelled: " + distance + " miles");

        lM = (LocationManager)getSystemService(LOCATION_SERVICE);
        lM.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 5, this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            return;
        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onLocationChanged(@NonNull Location location) {
        latiti = location.getLatitude();
        longit = location.getLongitude();

        lat.setText("Latitude: " + latiti);
        lon.setText("Longitude: " + longit);

        loc = new Location("CurrentLocation");

        loc.setLatitude(latiti);
        loc.setLongitude(longit);
        Geocoder g = new Geocoder(MainActivity.this, Locale.US);
        try {
            NewAddress=g.getFromLocation(latiti, longit, 2).get(0).getAddressLine(0);
            String[] s = NewAddress.split(" ", 2);
            s[0] = s[0] + " ";
            curr.setText("Current Address: " + s[1]);

            if (!addresses.isEmpty()) {
                if (!addresses.get(addresses.size() - 1).equals(s[1])) {
                    addresses.add(s[1]);

                    if (entryTime != 0) {
                        exitTime = System.currentTimeMillis();
                        timeSpent = (exitTime - entryTime) / 1000;
                        current.add(timeSpent + " sec");
                    }
                    entryTime = System.currentTimeMillis();
                }
                float distanceInc = loc.distanceTo(lastLoc)/1609;
                distance += distanceInc;
                tDT.setText("Total Distance Traveled: " + distance + " miles");
            }
            else {
                addresses.add(s[1]);
                entryTime = System.currentTimeMillis();
            }
            lastLoc = loc;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        updateAddressTextViews();

        upTime();

    }

    private void updateAddressTextViews() {
        for(int i = 0; i < addresses.size(); i++) {
            if (addresses.get(i) != null) {
                if(i == addresses.size()-1){
                    add1.setText(addresses.get(i));
                }
                if(i == addresses.size()-2){
                    add2.setText(addresses.get(i));
                }
                if(i == addresses.size()-3){
                    add3.setText(addresses.get(i));
                }
            }
        }
    }
    private void upTime() {
        for(int i = 0; i < current.size(); i++) {
            if (current.get(i) != null) {
                if(i == current.size()-1){
                    tim1.setText(current.get(i));
                }
                if(i == current.size()-2){
                    tim2.setText(current.get(i));
                }
                if(i == current.size()-3){
                    tim3.setText(current.get(i));
                }
            }
        }
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lM.removeUpdates(this);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }
}