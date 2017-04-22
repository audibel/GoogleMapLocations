package com.example.googlemaplocations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LouisAudibert on 21/04/2017.
 */

public class LocationsDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "locationsdb";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "locationstable";
    private static final String KEY_PRIMARY = "primaryKey";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_ZOOMLVL = "zoomLvl";

    public LocationsDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                KEY_PRIMARY + " INTEGER PRIMARY KEY, " +
                KEY_LATITUDE + ", " +
                KEY_LONGITUDE + ", " +
                KEY_ZOOMLVL + ")";
        db.execSQL(CREATE_CATEGORIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public void insertNewLocation(double latitude, double longitude, int zoomLvl) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LATITUDE, latitude);
        values.put(KEY_LONGITUDE, longitude);
        values.put(KEY_ZOOMLVL, zoomLvl);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<LocationModel> getAllLocations() {
        List<LocationModel> locations = new ArrayList<LocationModel>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                locations.add(new LocationModel(cursor.getFloat(1), cursor.getFloat(2), cursor.getInt(3)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return locations;
    }

    public void deleteAllLocations() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

}
