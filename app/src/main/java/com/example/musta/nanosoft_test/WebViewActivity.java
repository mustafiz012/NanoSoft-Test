package com.example.musta.nanosoft_test;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.musta.nanosoft_test.db.SQLiteDBHelper;

public class WebViewActivity extends AppCompatActivity {
    WebView mWebView = null;
    private String name, age, latitude, longitude;
    SQLiteDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        dbHelper = new SQLiteDBHelper(this);
        Cursor data = dbHelper.getNameColumn(1);
        if (data.moveToFirst()) {
            do {
                name = data.getString(data.getColumnIndex("NAME"));
                age = data.getString(data.getColumnIndex("AGE"));
                latitude = data.getString(data.getColumnIndex("LATITUDE"));
                longitude = data.getString(data.getColumnIndex("LONGITUDE"));
            } while (data.moveToNext());
        }
        mWebView = (WebView) findViewById(R.id.webView);
        String htmlString = "<html><body>" +
                "<h2>Name: " + name + "</h2>\n" +
                "<p>Age: " + age + "</p>\n" +
                "\n" +
                "<h4>Following is the overview of " + name + "</h4>\n" +
                "<table border=\"1\" width=\"100%\">\n" +
                "  <tr>\n" +
                "    <td>Latitude: " + latitude + "</td>\n" +
                "    <td>Longitude: " + longitude + "</td>\t\t\n" +
                "  </tr>\n" +
                "</table>" +
                "</body></html>";
        mWebView.loadData(htmlString, "text/html", null);
    }
}
