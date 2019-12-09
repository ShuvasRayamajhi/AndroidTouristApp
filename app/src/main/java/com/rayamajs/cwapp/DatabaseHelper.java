package com.rayamajs.cwapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Review.db";
    public static final String TABLE_NAME = "review_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "PLACENAME";
    public static final String COL_3 = "REVIEW";
    public static final String COL_4 = "RATING";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT , PLACENAME TEXT, REVIEW TEXT, RATING TEXT)" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
