package com.example.custom_clothing_order_manager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.custom_clothing_order_manager.models.Customer;
import com.example.custom_clothing_order_manager.models.Tailor;

public class CustomerDB extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "clothing_db.db";
    public static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "Customer";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_PASSWORD = "password";

    public CustomerDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT UNIQUE, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createTableQuery);

        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COLUMN_ID + ") VALUES (249);");
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = 249;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertCustomer(Customer customer) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, customer.getName());
        String lowercaseEmail = customer.getEmail().toLowerCase();
        values.put(COLUMN_EMAIL, lowercaseEmail);
        values.put(COLUMN_PHONE, customer.getPhone());
        values.put(COLUMN_PASSWORD, customer.getPassword());

        try {
            return db.insertOrThrow(TABLE_NAME, null, values);
        } catch (android.database.SQLException e) {
            return -1;
        }
    }

    public boolean customerCheck(String email, String password) {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        boolean userExists = cursor.moveToFirst();

        cursor.close();
        return userExists;
    }

    public Customer getCustomerByEmail(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Customer customer = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        if (cursor != null && cursor.moveToFirst()) {
            try {
                int idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                int nameIndex = cursor.getColumnIndexOrThrow(COLUMN_NAME);
                int emailIndex = cursor.getColumnIndexOrThrow(COLUMN_EMAIL);
                int phoneIndex = cursor.getColumnIndexOrThrow(COLUMN_PHONE);
                int passwordIndex = cursor.getColumnIndexOrThrow(COLUMN_PASSWORD);

                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                String emailFromDB = cursor.getString(emailIndex);
                String phone = cursor.getString(phoneIndex);
                String password = cursor.getString(passwordIndex);

                customer = new Customer(id, name, emailFromDB, phone, password);
            } catch (IllegalArgumentException e) {
                Log.e("TailorDatabaseHelper", "Column not found", e);
            } finally {
                cursor.close();
            }
        }
        return customer;
    }


    public Customer getCustomerById(int customerID) {
        SQLiteDatabase db = getReadableDatabase();
        Customer customer = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(customerID)});

        if (cursor != null && cursor.moveToFirst()) {
            try {
                int idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                int nameIndex = cursor.getColumnIndexOrThrow(COLUMN_NAME);
                int emailIndex = cursor.getColumnIndexOrThrow(COLUMN_EMAIL);
                int phoneIndex = cursor.getColumnIndexOrThrow(COLUMN_PHONE);
                int passwordIndex = cursor.getColumnIndexOrThrow(COLUMN_PASSWORD);

                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                String email = cursor.getString(emailIndex);
                String phone = cursor.getString(phoneIndex);
                String password = cursor.getString(passwordIndex);


                customer = new Customer(id, name, email, phone, password);
            } catch (IllegalArgumentException e) {
                Log.e("Customer", "Column not found", e);
            } finally {
                cursor.close();
            }
        }

        return customer;
    }
}
