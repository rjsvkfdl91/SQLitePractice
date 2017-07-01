package com.example.s521573.sqlitepractice.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class CarDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "carlist.db";
    public static final int DATABASE_VERSION = 1;

    public CarDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_TABLE = "CREATE TABLE " + CarContract.Carlist.TABLE_NAME + " (" +
                CarContract.Carlist._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CarContract.Carlist.COLUMN_MANUFACTURE + " TEXT NOT NULL, " +
                CarContract.Carlist.COLUMN_MODEL + " TEXT NOT NULL, " +
                CarContract.Carlist.COLUMN_PRICE + " INTEGER NOT NULL, " +
                CarContract.Carlist.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final String DROP_TABLE = "DROP TABLE IF EXISTS " + CarContract.Carlist.TABLE_NAME;
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }
}
