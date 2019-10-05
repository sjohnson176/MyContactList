package com.example.mycontactlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.view.View;
import android.location.Address;

import java.io.IOException;
import java.util.List;
import android.os.Bundle;
import android.os.Build;
import com.google.android.material.snackbar.Snackbar;
import android.widget.TextView;

public class ContactMapActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener gpsListener;
    LocationListener networkListener;
    Location currentBestLocation;
    final int PERMISSION_REQUEST_LOCATION = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_map);
        initListButton();
        initMapButton();
        initSettingsButton();
        initGetLocationButton();

    }

    @Override
    public void onPause() {
        super.onPause();

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getBaseContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getBaseContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;

        }

        try {
            locationManager.removeUpdates(gpsListener);
            locationManager.removeUpdates(networkListener);

        }
        catch (Exception e) {
            e.printStackTrace();

        }

    }

    private void initListButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(ContactMapActivity.this, ContactListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }

        });

    }

    private void initMapButton() {

        ImageButton ibMap = (ImageButton) findViewById(R.id.imageButtonMap);
        ibMap.setEnabled(false);

    }

    private void initSettingsButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonSettings);
        ibList.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(ContactMapActivity.this, ContactSettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }

        });

    }

    // 7.1 CODE TO LOOK UP COORDINATES, 7.3 CODE TO GET COORDINATES
    private void initGetLocationButton() {
        Button locationButton = (Button) findViewById(R.id.buttonGetLocation);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                try {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (ContextCompat.checkSelfPermission(ContactMapActivity.this,
                                Manifest.permission.ACCESS_FINE_LOCATION) !=
                                PackageManager.PERMISSION_GRANTED) {

                            if (ActivityCompat.shouldShowRequestPermissionRationale(
                                    ContactMapActivity.this,
                                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                                Snackbar.make(findViewById(R.id.activity_contact_map),
                                        "MyContactList requires this permission to locate " +
                                                "your contacts", Snackbar.LENGTH_INDEFINITE).
                                        setAction("OK", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                ActivityCompat.requestPermissions(
                                                        ContactMapActivity.this,
                                                        new String[] {
                                                                android.Manifest.permission.ACCESS_FINE_LOCATION},
                                                        PERMISSION_REQUEST_LOCATION);
                                            }

                                        })
                                        .show();

                            }
                            else {
                                ActivityCompat.requestPermissions(ContactMapActivity.this,
                                        new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        PERMISSION_REQUEST_LOCATION);
                            }

                        }
                        else {
                            startLocationUpdates();

                        }

                    }
                    else {
                        startLocationUpdates();

                    }

             /*   // 7.1 CODE TO LOOK UP COORDINATES
                EditText editAddress = (EditText) findViewById(R.id.editAddress);
                EditText editCity = (EditText) findViewById(R.id.editCity);
                EditText editState = (EditText) findViewById(R.id.editState);
                EditText editZipCode = (EditText) findViewById(R.id.editZipCode);
                String address = editAddress.getText().toString() + ", " +
                                editCity.getText().toString() + ", " +
                                editState.getText().toString() + ", " +
                                editZipCode.getText().toString();
                List<Address> addresses = null;
                Geocoder geo = new Geocoder(ContactMapActivity.this);
                try {
                    addresses = geo.getFromLocationName(address, 1);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                TextView txtLat = (TextView) findViewById(R.id.editLatitude);
                TextView txtLong = (TextView) findViewById(R.id.editLongitude);
                txtLat.setText(String.valueOf(addresses.get(0).getLatitude()));
                txtLong.setText(String.valueOf(addresses.get(0).getLongitude()));
            */

                }
                catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Error requesting permission",
                            Toast.LENGTH_LONG).show();
                }

            }

        });

    }

    // 7.5 ADDITIONAL CODE FOR STARTLOCATIONUPDATE
    private void startLocationUpdates() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getBaseContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getBaseContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;

        }

        // 7.3 CODE TO GET COORDINATES
        try {
            locationManager = (LocationManager)getBaseContext().
                    getSystemService(Context.LOCATION_SERVICE);
            gpsListener = new LocationListener() {
                public void onLocationChanged(Location location) {

                    if (isBetterLocation(location)) {
                        currentBestLocation = location;

                        TextView txtLatitude = (TextView) findViewById(R.id.editBestLocationLatitude);
                        TextView txtLongitude = (TextView) findViewById(R.id.editBestLocationLongitude);
                        TextView txtAccuracy = (TextView) findViewById(R.id.editBestLocationAccuracy);
                        txtLatitude.setText(String.valueOf(location.getLatitude()));
                        txtLongitude.setText(String.valueOf(location.getLongitude()));
                        txtAccuracy.setText(String.valueOf(location.getAccuracy()));

                    }

                        TextView txtLatitude = (TextView) findViewById(R.id.editGPSLatitude);
                        TextView txtLongitude = (TextView) findViewById(R.id.editGPSLongitude);
                        TextView txtAccuracy = (TextView) findViewById(R.id.editGPSAccuracy);
                        txtLatitude.setText(String.valueOf(location.getLatitude()));
                        txtLongitude.setText(String.valueOf(location.getLongitude()));
                        txtAccuracy.setText(String.valueOf(location.getAccuracy()));

                }

                public void onStatusChanged(String provider, int status, Bundle extras) {}
                public void onProviderEnabled(String provider) {}
                public void onProviderDisabled(String provider) {}

            };

            networkListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    if (isBetterLocation(location)) {
                        currentBestLocation = location;

                        TextView txtLatitude = (TextView) findViewById(R.id.editBestLocationLatitude);
                        TextView txtLongitude = (TextView) findViewById(R.id.editBestLocationLongitude);
                        TextView txtAccuracy = (TextView) findViewById(R.id.editBestLocationAccuracy);
                        txtLatitude.setText(String.valueOf(location.getLatitude()));
                        txtLongitude.setText(String.valueOf(location.getLongitude()));
                        txtAccuracy.setText(String.valueOf(location.getAccuracy()));

                    }

                        TextView txtLatitude = (TextView) findViewById(R.id.editNetworkLatitude);
                        TextView txtLongitude = (TextView) findViewById(R.id.editNetworkLongitude);
                        TextView txtAccuracy = (TextView) findViewById(R.id.editNetworkAccuracy);
                        txtLatitude.setText(String.valueOf(location.getLatitude()));
                        txtLongitude.setText(String.valueOf(location.getLongitude()));
                        txtAccuracy.setText(String.valueOf(location.getAccuracy()));

                }

                public void onStatusChanged(String provider, int status, Bundle extras) {}
                public void onProviderEnabled(String provider) {}
                public void onProviderDisabled(String provider) {}

            };

            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 0, 0, gpsListener);

            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 0, 0, networkListener);

        }
        catch (Exception e) {
            Toast.makeText(getBaseContext(), "Error, Location not available",
                    Toast.LENGTH_LONG).show();
        }

    }

    // 7.8 ISBETTERLOCATION METHOD
    private boolean isBetterLocation(Location location) {
        boolean isBetter = false;
        if (currentBestLocation == null) {
            isBetter = true;

        }
        else if (location.getAccuracy() <= currentBestLocation.getAccuracy()) {
            isBetter = true;

        }
        else if (location.getTime() - currentBestLocation.getTime() > 5 * 60 * 1000) {
            isBetter = true;

        }
        return isBetter;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {

        switch (requestCode) {
            case PERMISSION_REQUEST_LOCATION: {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startLocationUpdates();

                }
                else {
                    Toast.makeText(ContactMapActivity.this,
                            "MyContactList will not locate your contacts.",
                            Toast.LENGTH_LONG).show();

                }

            }

        }

    }

}
