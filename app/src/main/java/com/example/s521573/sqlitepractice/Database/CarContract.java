package com.example.s521573.sqlitepractice.Database;

import android.provider.BaseColumns;

public class CarContract {

    public static final class Carlist implements BaseColumns {

        public static final String TABLE_NAME = "carlist";
        public static final String COLUMN_MANUFACTURE = "manufacture";
        public static final String COLUMN_MODEL = "model";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
