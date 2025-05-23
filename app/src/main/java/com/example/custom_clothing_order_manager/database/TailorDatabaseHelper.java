package com.example.custom_clothing_order_manager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.custom_clothing_order_manager.models.Tailor;

import java.util.ArrayList;
import java.util.List;

public class TailorDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "clothing_database.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "tailors";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_SHOP_NAME = "shop_name";
    public static final String COLUMN_SPECIALIZATIONS = "specializations";
    public static final String COLUMN_IMAGE_PATH = "image_path";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";

    public TailorDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_SHOP_NAME + " TEXT, " +
                COLUMN_SPECIALIZATIONS + " TEXT, " +
                COLUMN_IMAGE_PATH + " TEXT, " +
                COLUMN_LATITUDE + " REAL, " +
                COLUMN_LONGITUDE + " REAL)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertTailor(Tailor tailor) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, tailor.getName());
        values.put(COLUMN_EMAIL, tailor.getEmail());
        values.put(COLUMN_PASSWORD, tailor.getPassword());
        values.put(COLUMN_SHOP_NAME, tailor.getShopName());
        values.put(COLUMN_SPECIALIZATIONS, tailor.getSpecializations());
        values.put(COLUMN_IMAGE_PATH, tailor.getImagePath());
        values.put(COLUMN_LATITUDE, tailor.getLatitude());
        values.put(COLUMN_LONGITUDE, tailor.getLongitude());
        return db.insert(TABLE_NAME, null, values);
    }

    public List<Tailor> getAllTailors() {
        List<Tailor> tailors = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null); // Select all

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int idIndex = cursor.getColumnIndex(COLUMN_ID);
                    int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
                    int emailIndex = cursor.getColumnIndex(COLUMN_EMAIL);
                    int passwordIndex = cursor.getColumnIndex(COLUMN_PASSWORD);
                    int shopNameIndex = cursor.getColumnIndex(COLUMN_SHOP_NAME);
                    int specializationsIndex = cursor.getColumnIndex(COLUMN_SPECIALIZATIONS);
                    int imagePathIndex = cursor.getColumnIndex(COLUMN_IMAGE_PATH);
                    int latitudeIndex = cursor.getColumnIndex(COLUMN_LATITUDE);
                    int longitudeIndex = cursor.getColumnIndex(COLUMN_LONGITUDE);


                    int id = cursor.getInt(idIndex);
                    String name = cursor.getString(nameIndex);
                    String email = cursor.getString(emailIndex);
                    String password = cursor.getString(passwordIndex);
                    String shopName = cursor.getString(shopNameIndex);
                    String specializations = cursor.getString(specializationsIndex);
                    String imagePath = cursor.getString(imagePathIndex);
                    double latitude = cursor.getDouble(latitudeIndex);
                    double longitude = cursor.getDouble(longitudeIndex);

                    Tailor tailor = new Tailor(id, name, email, password, shopName, specializations, imagePath, latitude, longitude);
                    tailors.add(tailor);

                    Log.d("TailorData", "Retrieved tailor: " + tailor.getName());

                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return tailors;
    }

    public boolean userCheck(String email, String password) {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        boolean userExists = cursor.moveToFirst();

        cursor.close();
        return userExists;
    }

    public Tailor getTailorByEmail(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Tailor tailor = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        if (cursor != null && cursor.moveToFirst()) {
            try {
                int idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                int nameIndex = cursor.getColumnIndexOrThrow(COLUMN_NAME);
                int emailIndex = cursor.getColumnIndexOrThrow(COLUMN_EMAIL);
                int passwordIndex = cursor.getColumnIndexOrThrow(COLUMN_PASSWORD);
                int shopNameIndex = cursor.getColumnIndexOrThrow(COLUMN_SHOP_NAME);
                int specializationsIndex = cursor.getColumnIndexOrThrow(COLUMN_SPECIALIZATIONS);
                int imagePathIndex = cursor.getColumnIndexOrThrow(COLUMN_IMAGE_PATH);
                int latitudeIndex = cursor.getColumnIndexOrThrow(COLUMN_LATITUDE);
                int longitudeIndex = cursor.getColumnIndexOrThrow(COLUMN_LONGITUDE);

                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                String emailFromDB = cursor.getString(emailIndex);
                String password = cursor.getString(passwordIndex);
                String shopName = cursor.getString(shopNameIndex);
                String specializations = cursor.getString(specializationsIndex);
                String imagePath = cursor.getString(imagePathIndex);
                double latitude = cursor.getDouble(latitudeIndex);
                double longitude = cursor.getDouble(longitudeIndex);

                tailor = new Tailor(id, name, emailFromDB, password, shopName, specializations, imagePath, latitude, longitude);
            } catch (IllegalArgumentException e) {
                Log.e("TailorDatabaseHelper", "Column not found", e);
            } finally {
                cursor.close();
            }
        }

        return tailor;
    }

    public Tailor getTailorById(int tailorId) {
        SQLiteDatabase db = getReadableDatabase();
        Tailor tailor = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(tailorId)});

        if (cursor != null && cursor.moveToFirst()) {
            try {
                int idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                int nameIndex = cursor.getColumnIndexOrThrow(COLUMN_NAME);
                int emailIndex = cursor.getColumnIndexOrThrow(COLUMN_EMAIL);
                int passwordIndex = cursor.getColumnIndexOrThrow(COLUMN_PASSWORD);
                int shopNameIndex = cursor.getColumnIndexOrThrow(COLUMN_SHOP_NAME);
                int specializationsIndex = cursor.getColumnIndexOrThrow(COLUMN_SPECIALIZATIONS);
                int imagePathIndex = cursor.getColumnIndexOrThrow(COLUMN_IMAGE_PATH);
                int latitudeIndex = cursor.getColumnIndexOrThrow(COLUMN_LATITUDE);
                int longitudeIndex = cursor.getColumnIndexOrThrow(COLUMN_LONGITUDE);

                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                String email = cursor.getString(emailIndex);
                String password = cursor.getString(passwordIndex);
                String shopName = cursor.getString(shopNameIndex);
                String specializations = cursor.getString(specializationsIndex);
                String imagePath = cursor.getString(imagePathIndex);
                double latitude = cursor.getDouble(latitudeIndex);
                double longitude = cursor.getDouble(longitudeIndex);

                tailor = new Tailor(id, name, email, password, shopName, specializations, imagePath, latitude, longitude);
            } catch (IllegalArgumentException e) {
                Log.e("TailorDatabaseHelper", "Column not found", e);
            } finally {
                cursor.close();
            }
        }

        return tailor;
    }



}
