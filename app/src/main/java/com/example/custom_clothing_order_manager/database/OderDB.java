package com.example.custom_clothing_order_manager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OderDB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "clothing_database_oder.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "oder";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ITEM_TYPE = "type";
    public static final String COLUMN_MATERIAL = "material";
    public static final String COLUMN_COLOR = "color";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_STREET = "street";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_CUSID = "cusid";
    public static final String COLUMN_TAILOR = "tailorid";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_MEASUREMENT = "measurement";
    public static final String COLUMN_STATUS = "staus";


    public OderDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEM_TYPE + " TEXT, " +
                COLUMN_MATERIAL + " TEXT, " +
                COLUMN_COLOR + " TEXT, " +
                COLUMN_NOTE + " TEXT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_STREET + " TEXT, " +
                COLUMN_CITY + " TEXT, " +
                COLUMN_COUNTRY + " TEXT, " +
                COLUMN_CUSID + "INTEGER, " +
                COLUMN_TAILOR +"INTEGER, "+
                COLUMN_DATE + " TEXT, " +
                COLUMN_MEASUREMENT + " TEXT, " +
                COLUMN_STATUS + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
