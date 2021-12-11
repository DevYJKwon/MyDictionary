package com.example.mydictionary;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    DBmanager dbmanager;
    SQLiteDatabase sqlitedb;

    EditText et_word;
    EditText et_noun;
    EditText et_verb;
    EditText et_adj;
    EditText et_adv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);
        et_word = (EditText)findViewById(R.id.wordInput);
        et_noun = (EditText)findViewById(R.id.nInput);
        et_verb = (EditText)findViewById(R.id.vInput);
        et_adj = (EditText)findViewById(R.id.adjInput);
        et_adv= (EditText)findViewById(R.id.advInput);
        et_word.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    et_noun.requestFocus();
                    return true;
                }
                return false;
            }
        });
        et_noun.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    et_verb.requestFocus();
                    return true;
                }
                return false;
            }
        });
        et_verb.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    et_adj.requestFocus();
                    return true;
                }
                return false;
            }
        });
        et_adj.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    et_adv.requestFocus();
                    return true;
                }
                return false;
            }
        });
        et_adv.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( et_adv.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

    }
    public void onClickAdd(View v){
        String str_word = et_word.getText().toString();
        String str_noun = et_noun.getText().toString();
        String str_verb = et_verb.getText().toString();
        String str_adj = et_adj.getText().toString();
        String str_adv = et_adv.getText().toString();
        if(str_word.isEmpty()){
                Toast.makeText(this,"제대로 입력해주세요.",Toast.LENGTH_LONG).show();
                return;
        }
        else{
            if(!str_noun.isEmpty() || !str_verb.isEmpty() || !str_adj.isEmpty() || !str_adv.isEmpty()){

            }
            else{
                Toast.makeText(this,"제대로 입력해주세요.",Toast.LENGTH_LONG).show();
                return;
            }
        }

        try{
            dbmanager = new DBmanager(this);
            sqlitedb = dbmanager.getReadableDatabase();
            Cursor cursor = sqlitedb.query("Word", null, "word = ?", new String[]{str_word}, null, null, null, null);
            if(cursor.getCount() > 0){
                Toast.makeText(this,"이미 값이 있습니다.",Toast.LENGTH_SHORT).show();
                sqlitedb.close();
                dbmanager.close();
            }
            else {
                sqlitedb = dbmanager.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("word", str_word);
                values.put("noun", str_noun);
                values.put("verb", str_verb);
                values.put("adj", str_adj);
                values.put("adv", str_adv);
                values.put("wrong", 0);

                long newRowId = sqlitedb.insert("Word", null, values);

                sqlitedb.close();
                dbmanager.close();
                finish();
            }
        }
        catch(SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
