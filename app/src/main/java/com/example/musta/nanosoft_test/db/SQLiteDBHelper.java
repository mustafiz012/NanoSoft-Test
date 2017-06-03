package com.example.musta.nanosoft_test.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by musta on 6/2/2017.
 */

public class SQLiteDBHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "user_info";
    private static final String COL1 = "ID";
    private static final String NAME_COLUMN = "NAME";
    private static final String AGE_COLUMN = "AGE";
    private static final String LATITUDE_COLUMN = "LATITUDE";
    private static final String LONGITUDE_COLUMN = "LONGITUDE";

    public SQLiteDBHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME_COLUMN + " TEXT, " + AGE_COLUMN + " TEXT, " + LATITUDE_COLUMN + " TEXT, " + LONGITUDE_COLUMN + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addNewEntry(String name, String age, String latitude, String longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_COLUMN, name);
        contentValues.put(AGE_COLUMN, age);
        contentValues.put(LATITUDE_COLUMN, latitude);
        contentValues.put(LONGITUDE_COLUMN, longitude);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else return true;
    }

    public Cursor getSQLiteData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getNameColumn(int rowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME+" WHERE ID = "+rowId;
        Cursor data = db.rawQuery(query, null);
        /*if (data.moveToFirst()){
            do {

            }while (data.moveToNext());
        }*/
        return data;
    }
}
