package com.example.musta.nanosoft_test.maps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.musta.nanosoft_test.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    boolean isLocationPermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.i("LocationPerm", "disabled");
                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            } else {
                isLocationPermissionGranted = true;
            }
        } else {
            isLocationPermissionGranted = true;
        }
        if (isLocationPermissionGranted) {
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                Log.i("NETWORK_PROVIDER", "enabled");
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        LatLng latLng = new LatLng(latitude, longitude);
                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        try {
                            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                            String selectedLocation = addressList.get(0).getLocality() + ", ";
                            selectedLocation += addressList.get(0).getCountryName();
                            mMap.addMarker(new MarkerOptions().position(latLng).title(selectedLocation));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            //Toast.makeText(MapsActivity.this, "" + selectedLocation, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
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
                });
            } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Log.i("GPS_PROVIDER", "enabled");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        LatLng latLng = new LatLng(latitude, longitude);
                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        try {
                            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                            String selectedLocation = addressList.get(0).getLocality() + ", ";
                            selectedLocation += addressList.get(0).getCountryName();
                            mMap.addMarker(new MarkerOptions().position(latLng).title(selectedLocation));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            //Toast.makeText(MapsActivity.this, "" + selectedLocation, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
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
                });
            } else {
                Intent enableGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(enableGPS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 101: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isLocationPermissionGranted = true;
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                        Intent enableGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(enableGPS);
                    }
                    //Toast.makeText(this, "Restart to reload current location", Toast.LENGTH_SHORT).show();
                } else {
                    ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                }
                break;
            }
        }
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

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(23, 90);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Dhaka"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MapsActivity.this.finish();
    }
}
