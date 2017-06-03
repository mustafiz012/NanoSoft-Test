package com.example.musta.nanosoft_test;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by musta on 6/2/2017.
 */

public class InformationFragment extends Fragment {
    private String name, age, latitude, longitude;
    private TextView userName, userAge, userLatitude, userLongitude = null;
    public InformationFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.i("onCreateView", "InformationFragment");
        name = getArguments().getString("name");
        age = getArguments().getString("age");
        latitude = getArguments().getString("latitude");
        longitude = getArguments().getString("longitude");
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("onViewCreated", "InformationFragment");
        userName = (TextView) view.findViewById(R.id.user_name);
        userAge = (TextView) view.findViewById(R.id.user_age);
        userLatitude = (TextView) view.findViewById(R.id.user_latitude);
        userLongitude = (TextView) view.findViewById(R.id.user_longitude);
        userName.setText("Name: "+name);
        userAge.setText("Age: "+age);
        userLatitude.setText("Latitude: "+latitude);
        userLongitude.setText("Longitude: "+longitude);
    }
}
