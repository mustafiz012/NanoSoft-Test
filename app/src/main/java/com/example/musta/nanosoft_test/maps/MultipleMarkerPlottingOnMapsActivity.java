package com.example.musta.nanosoft_test.maps;

import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.musta.nanosoft_test.R;
import com.example.musta.nanosoft_test.db.SQLiteDBHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MultipleMarkerPlottingOnMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SQLiteDBHelper dbHelper;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_marker_plotting_on_maps);

        dbHelper = new SQLiteDBHelper(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // Add a marker in Dhaka and move the camera
            LatLng currentLocation = new LatLng(23, 90);
            Cursor data = dbHelper.getSQLiteData();
            while (data.moveToNext()) {
                double lat = Double.parseDouble(data.getString(data.getColumnIndex("LATITUDE")));
                double lng = Double.parseDouble(data.getString(data.getColumnIndex("LONGITUDE")));
                currentLocation = new LatLng(lat, lng);
                Geocoder geocoder = new Geocoder(getApplicationContext());
                List<Address> addressList = null;
                try {
                    addressList = geocoder.getFromLocation(lat, lng, 1);
                    String selectedLocation = addressList.get(0).getLocality() + ", ";
                    selectedLocation += addressList.get(0).getCountryName();
                    mMap.addMarker(new MarkerOptions().position(currentLocation).title(selectedLocation));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        } else {
            Intent enableGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(enableGPS);
        }
    }
}
