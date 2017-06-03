package com.example.musta.nanosoft_test;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import com.example.musta.nanosoft_test.db.SQLiteDBHelper;
import com.example.musta.nanosoft_test.maps.MapsActivity;
import com.example.musta.nanosoft_test.maps.MultipleMarkerPlottingOnMapsActivity;
import com.example.musta.nanosoft_test.viewpager.ViewPagerActivity;
import com.github.clans.fab.FloatingActionMenu;
import com.github.clans.fab.FloatingActionButton;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionMenu floatingActionMenu = null;
    private FloatingActionButton fbAddEntry = null;
    private FloatingActionButton fbViewPagerSample = null;
    private FloatingActionButton fbCurrentLocation = null;
    private FloatingActionButton fbSavedLocationPlotting = null;
    private FloatingActionButton fbWebViewExample = null;
    private ListView listView = null;
    private String newEntryName = null;
    private String newEntryAge = null;
    private String newEntryLatitude = null;
    private String newEntryLongitude = null;
    ArrayAdapter<String> arrayAdapter = null;
    private ArrayList<String> userEntries = null;
    SQLiteDBHelper dbHelper;
    private boolean isFragmentOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MainActivity", "onCreate");
        setContentView(R.layout.activity_main);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        userEntries = new ArrayList<>();
        dbHelper = new SQLiteDBHelper(this);
        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.fab_menu);
        fbAddEntry = (FloatingActionButton) findViewById(R.id.menu_item_add_entry);
        fbViewPagerSample = (FloatingActionButton) findViewById(R.id.menu_item_viewpager);
        fbCurrentLocation = (FloatingActionButton) findViewById(R.id.menu_item_current_location);
        fbSavedLocationPlotting = (FloatingActionButton) findViewById(R.id.menu_item_saved_location);
        fbWebViewExample = (FloatingActionButton) findViewById(R.id.menu_item_webview);
        fbWebViewExample.setOnClickListener(this);
        fbSavedLocationPlotting.setOnClickListener(this);
        fbCurrentLocation.setOnClickListener(this);
        fbViewPagerSample.setOnClickListener(this);
        floatingActionMenu.setClosedOnTouchOutside(true);
        listView = (ListView) findViewById(R.id.list_items);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userEntries);
        updateSQLiteDB();
        reloadListOfEntries();
        floatingActionMenu.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.toggle(true);
            }
        });
        fbAddEntry.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.menu_item_add_entry: {
                floatingActionMenu.toggle(true);
                addEntry();
                break;
            }
            case R.id.menu_item_viewpager: {
                floatingActionMenu.toggle(true);
                startActivity(new Intent(this, ViewPagerActivity.class));
                break;
            }
            case R.id.menu_item_current_location: {
                floatingActionMenu.toggle(true);
                startActivity(new Intent(this, MapsActivity.class));
                break;
            }
            case R.id.menu_item_saved_location: {
                floatingActionMenu.toggle(true);
                Intent intent = new Intent(this, MultipleMarkerPlottingOnMapsActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.menu_item_webview: {
                Log.i("webview", "wrong?");
                floatingActionMenu.toggle(true);
                startActivity(new Intent(this, WebViewActivity.class));
                break;
            }
        }
    }

    private void addEntry() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View inflator = layoutInflater.inflate(R.layout.custom_dialog_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add user info");
        builder.setView(inflator);
        final EditText name = (EditText) inflator.findViewById(R.id.dialog_name);
        final EditText age = (EditText) inflator.findViewById(R.id.dialog_age);
        final EditText latitude = (EditText) inflator.findViewById(R.id.dialog_latitude);
        final EditText longitude = (EditText) inflator.findViewById(R.id.dialog_longitude);
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newEntryName = name.getText().toString();
                newEntryAge = age.getText().toString();
                newEntryLatitude = latitude.getText().toString();
                newEntryLongitude = longitude.getText().toString();
                boolean insertion = dbHelper.addNewEntry(newEntryName, newEntryAge, newEntryLatitude, newEntryLongitude);
                if (insertion)
                    Toast.makeText(MainActivity.this, "Inserted", Toast.LENGTH_SHORT).show();
                else Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                updateSQLiteDB();
                reloadListOfEntries();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void updateSQLiteDB() {
        Cursor data = dbHelper.getSQLiteData();
        userEntries.clear();
        while (data.moveToNext()) {
            //Log.i("" + data.getString(1), " " + data.getInt(0));
            userEntries.add(data.getString(1));
        }
    }

    private void reloadListOfEntries() {
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "Clicked ", Toast.LENGTH_SHORT).show();
                if (floatingActionMenu.isOpened())
                    floatingActionMenu.toggle(true);
                listView.setVisibility(View.GONE);
                findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
                Fragment fragment = new InformationFragment();
                Bundle bundle = new Bundle();
                //String name, age, latitude, longitude;
                Cursor data = dbHelper.getNameColumn(position + 1);
                if (data.moveToFirst()) {
                    do {
                        bundle.putString("name", data.getString(data.getColumnIndex("NAME")));
                        bundle.putString("age", data.getString(data.getColumnIndex("AGE")));
                        bundle.putString("latitude", data.getString(data.getColumnIndex("LATITUDE")));
                        bundle.putString("longitude", data.getString(data.getColumnIndex("LONGITUDE")));
                    } while (data.moveToNext());
                }
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment, "User information")
                        .commit();
                isFragmentOpened = true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (floatingActionMenu.isOpened())
            floatingActionMenu.toggle(true);
        else if (isFragmentOpened) {
            isFragmentOpened = false;
            listView.setVisibility(View.VISIBLE);
            findViewById(R.id.fragment_container).setVisibility(View.GONE);
        } else super.onBackPressed();
    }
}
