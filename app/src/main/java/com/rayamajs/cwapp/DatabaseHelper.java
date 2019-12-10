package com.rayamajs.cwapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    //declaring database, table and columns
    public static final String DATABASE_NAME = "Review.db";
    public static final String TABLE_NAME = "review_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "PLACENAME";
    public static final String COL_3 = "REVIEW";
    public static final String COL_4 = "RATING";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null, 1); //create database
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //create table
        sqLiteDatabase.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT ,PLACENAME TEXT, REVIEW TEXT, RATING TEXT)" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //doesn't create a new table if it already exists
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public boolean insertData(String placename, String review, String rating) {
        SQLiteDatabase db = this.getWritableDatabase(); //writing into the database
        ContentValues contentValues = new ContentValues();//put values inside columns
        contentValues.put(COL_2, placename);
        contentValues.put(COL_3, review);
        contentValues.put(COL_4, rating);

        long success = db.insert(TABLE_NAME, null, contentValues); //insert the values that were put

        if (success == -1){
            return false;
        }
        else {
            return true;
        }
    }
}
