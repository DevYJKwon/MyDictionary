package com.example.mydictionary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DBmanager extends SQLiteOpenHelper {
    public DBmanager(Context context){
        super(context,"myDB",null,1);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table Word (word text,noun text,verb text,adj text,adv text,wrong int)");
    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
}
