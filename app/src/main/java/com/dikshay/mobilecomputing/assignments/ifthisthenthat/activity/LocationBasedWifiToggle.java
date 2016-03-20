package com.dikshay.mobilecomputing.assignments.ifthisthenthat.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.R;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.service.GetLocationService;

public class LocationBasedWifiToggle extends AppCompatActivity {

    Button getLocation;
    GetLocationService loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_based_wifi_toggle);

        getLocation = (Button) findViewById(R.id.show_location);

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getCurrentContext(), GetLocationService.class);
                startService(intent);
                loc = new GetLocationService(LocationBasedWifiToggle.this);
                if (loc.canGetLocation()) {

                    double latitude = loc.getLatitude();
                    double longitude = loc.getLongitude();

                    Toast.makeText(getApplicationContext(), "Dude, your location is - \n Lat: " +
                            latitude + "\nLon: " + longitude, Toast.LENGTH_LONG).show();
                } else {
                    loc.showSettingsAlert();
                }
            }
        });
    }

    private Context getCurrentContext(){
        return getApplicationContext();
    }
}
