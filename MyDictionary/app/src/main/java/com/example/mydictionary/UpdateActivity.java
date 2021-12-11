package com.example.mydictionary;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {
    DBmanager dbmanager;
    SQLiteDatabase sqlitedb;
    String str_word;
    String str_noun;
    String str_verb;
    String str_adj;
    String str_adv;
    int wrong;

    TextView tv_word;
    EditText et_noun;
    EditText et_verb;
    EditText et_adj;
    EditText et_adv;
    TextView tv_wrong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_layout);

        Intent it = getIntent();
        str_word = it.getStringExtra("it_word");

        try {
            dbmanager = new DBmanager(this);
            sqlitedb = dbmanager.getReadableDatabase();

            Cursor cursor = sqlitedb.query("Word", null, "word = ?", new String[]{str_word}, null, null, null, null);

            if (cursor.moveToNext()) {
                str_noun = cursor.getString(cursor.getColumnIndex("noun"));
                str_verb = cursor.getString(cursor.getColumnIndex("verb"));
                str_adj = cursor.getString(cursor.getColumnIndex("adj"));
                str_adv = cursor.getString(cursor.getColumnIndex("adv"));
                wrong   = cursor.getInt(cursor.getColumnIndex("wrong"));
            }

            sqlitedb.close();
            dbmanager.close();
        } catch(SQLiteException e) {
            Toast.makeText(this,  e.getMessage(), Toast.LENGTH_LONG).show();
        }

        tv_word = (TextView)findViewById(R.id.update_word);
        tv_word.setEnabled(false);
        tv_word.setText(str_word);


        et_noun = (EditText)findViewById(R.id.update_nInput);
        et_noun.setText(str_noun);

        et_verb = (EditText)findViewById(R.id.update_vInput);
        et_verb.setText(str_verb);

        et_adj = (EditText)findViewById(R.id.update_adjInput);
        et_adj.setText(str_adj);

        et_adv = (EditText)findViewById(R.id.update_advInput);
        et_adv.setText(str_adv);

        tv_wrong = (TextView) findViewById(R.id.update_wrong);
        tv_wrong.setText(""+wrong);


    }
    public void onClickUpdate(View v){
        str_noun = et_noun.getText().toString();
        str_verb = et_verb.getText().toString();
        str_adj = et_adj.getText().toString();
        str_adv = et_adv.getText().toString();

            if(!str_noun.isEmpty() || !str_verb.isEmpty() || !str_adj.isEmpty() || !str_adv.isEmpty()){

            }
            else{
                Toast.makeText(this,"제대로 입력해주세요.",Toast.LENGTH_LONG).show();
                return;
            }

        try {
            dbmanager = new DBmanager(this);
            sqlitedb = dbmanager.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("noun", str_noun);
            values.put("verb", str_verb);
            values.put("adj", str_adj);
            values.put("adv", str_adv);
            values.put("wrong", wrong);

            long newRowId = sqlitedb.update("Word", values, "word=?", new String[]{str_word});

            sqlitedb.close();
            dbmanager.close();
            Intent it = new Intent(this, WordActivity.class);
            startActivity(it);
            finish();
        } catch(SQLiteException e) {
            Toast.makeText(this,  e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
    public void onClickReset(View v){
        wrong = 0;
        tv_wrong.setText(""+wrong);
        Toast.makeText(this,"수정 버튼을 눌러야 적용이 됩니다.",Toast.LENGTH_LONG).show();
    }
    public void onClickRemove(View v){
        try {
            dbmanager = new DBmanager(this);
            sqlitedb = dbmanager.getWritableDatabase();


            long newRowId = sqlitedb.delete("Word", "word=?", new String[]{str_word});

            sqlitedb.close();
            dbmanager.close();
            Intent it = new Intent(this, WordActivity.class);
            startActivity(it);
            finish();
        } catch(SQLiteException e) {
            Toast.makeText(this,  e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
