package com.example.locationapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    Button btnLocation;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLocation = findViewById(R.id.btnLocation);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        btnLocation.setOnClickListener(v -> getLocation());
    }

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            showAlert(lat, lon);
        } else {
            showMessage("Location not available. Turn ON GPS.");
        }
    }

    private void showAlert(double lat, double lon) {
        new AlertDialog.Builder(this)
                .setTitle("Current Location")
                .setMessage("Latitude: " + lat + "\nLongitude: " + lon)
                .setPositiveButton("OK", null)
                .show();
    }

    private void showMessage(String msg) {
        new AlertDialog.Builder(this)
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .show();
    }
}