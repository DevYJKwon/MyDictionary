package com.example.mydictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Picture;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.LinearLayout;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    DBmanager dbmanager;
    SQLiteDatabase sqlitedb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickWord(View view) {
        Intent it = new Intent(this, WordActivity.class);
        startActivity(it);
    }
    public void onClickAdd(View view){
        Intent it = new Intent(this, AddActivity.class);
        startActivity(it);
    }
    public void onClickTest(View view){
        Intent it = new Intent(this, TestActivity.class);
        startActivity(it);
    }
    public void onClickExit(View view) {
        finish();
    }
}
