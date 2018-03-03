package com.beginner.hikerwatch;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity implements LocationListener {
    LocationManager locationManager;
    String provider;
    TextView lat1;
    TextView lng1;
    TextView alt;
    TextView spe;
    TextView bea;
    TextView add;
    TextView acc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lat1 = (TextView) findViewById(R.id.textViewlat);
        lng1 = (TextView) findViewById(R.id.textViewlong);
        alt = (TextView) findViewById(R.id.textViewaltitude);
        spe = (TextView) findViewById(R.id.textViewspeed);
        bea = (TextView) findViewById(R.id.textViewbearing);
        acc = (TextView) findViewById(R.id.textViewaccur);
        add = (TextView) findViewById(R.id.textViewaddress);
        ImageView imageView=findViewById(R.id.imageView);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            onLocationChanged(location);

        } else {
            Log.i("location info", "location not found");
        }



    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    public void onLocationChanged(Location location) {

            Double lat = location.getLatitude();
            Double lang = location.getLongitude();
            Double alt1 = location.getAltitude();
            Float bear1 = location.getBearing();
            Float speed1 = location.getSpeed();
            Float accu1 = location.getAccuracy();
            Geocoder geo = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                List<Address> addressList = geo.getFromLocation(lat, lang, 1);
                if (addressList != null && addressList.size() > 0) {
                    String addressholder = "";
                    for (int i = 0; i <= addressList.get(0).getMaxAddressLineIndex(); i++) {
                        addressholder += addressList.get(0).getAddressLine(i) + "\n";

                    }
                    add.setText("Address: \n" + addressholder);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            lat1.setText("Latitude: " + lat.toString());
            lng1.setText("Longitude: " + lang.toString());
            alt.setText("Altitude: " + alt1.toString() + "m");
            bea.setText("Bearing: " + bear1.toString());
            spe.setText("Speed: " + speed1.toString() + "m/s");
            acc.setText("Accuracy: " + accu1.toString() + "m");
         {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(provider, 400, 1, this);
        }



    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
