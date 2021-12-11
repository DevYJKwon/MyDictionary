package com.example.mydictionary;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WordActivity extends AppCompatActivity implements View.OnClickListener {
    DBmanager dbmanager;
    SQLiteDatabase sqlitedb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_layout);

        LinearLayout layout = (LinearLayout)findViewById(R.id.wordLayoutList);


        try{
            dbmanager = new DBmanager(this);
            sqlitedb = dbmanager.getReadableDatabase();
            Cursor cursor = sqlitedb.query("Word",null,null,null,null,null,null);

            int i =0;
            while(cursor.moveToNext()) {
                String str_word = cursor.getString(cursor.getColumnIndex("word"));
                String str_noun = cursor.getString(cursor.getColumnIndex("noun"));
                String str_verb = cursor.getString(cursor.getColumnIndex("verb"));
                String str_adj = cursor.getString(cursor.getColumnIndex("adj"));
                String str_adv = cursor.getString(cursor.getColumnIndex("adv"));
                int wrong = cursor.getInt(cursor.getColumnIndex("wrong"));


                LinearLayout layout_item = new LinearLayout(this);
                layout_item.setOrientation(LinearLayout.VERTICAL);
                layout_item.setPadding(20, 10, 20, 10);
                layout_item.setId(i);
                layout_item.setTag(str_word);

                TextView tv_word = new TextView(this);
                tv_word.setText(str_word);
                tv_word.setTextSize(30);
                if(wrong < 10)
                tv_word.setBackgroundColor(Color.argb(50, 0, 255, 0));
                else {
                    tv_word.setTextColor(Color.argb(255, 255, 0, 0));
                    tv_word.setBackgroundColor(Color.argb(50, 255, 0, 127));
                }

                layout_item.addView(tv_word);

                 if (!str_noun.isEmpty()) {
                    TextView tv_noun = new TextView(this);
                    tv_noun.setText("명사: " + str_noun);
                    tv_noun.setTextSize(20);
                    layout_item.addView(tv_noun);
                    }
                if (!str_verb.isEmpty()) {
                    TextView tv_verb = new TextView(this);
                    tv_verb.setText("동사: " + str_verb);
                    tv_verb.setTextSize(20);
                    layout_item.addView(tv_verb);
                }
                if (!str_adj.isEmpty()) {
                    TextView tv_adj = new TextView(this);
                    tv_adj.setText("형용사: " + str_adj);
                    tv_adj.setTextSize(20);
                    layout_item.addView(tv_adj);
                }
                if (!str_adv.isEmpty()) {
                    TextView tv_adv = new TextView(this);
                    tv_adv.setText("부사: " + str_adv);
                    tv_adv.setTextSize(20);
                    layout_item.addView(tv_adv);
                }
                TextView tv_wrong = new TextView(this);
                tv_wrong.setText("틀린 횟수: " + wrong);
                tv_wrong.setTextSize(20);
                layout_item.addView(tv_wrong);

                layout_item.setOnClickListener(this);

                layout.addView(layout_item);
                i++;
            }
            cursor.close();
            sqlitedb.close();
            dbmanager.close();
        }
        catch(SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void onClick(View v){
        int id = v.getId();
        LinearLayout layout_item = (LinearLayout) findViewById(id);
        String str_word = (String)layout_item.getTag();

        Intent it = new Intent(this, UpdateActivity.class);
        it.putExtra("it_word", str_word);
        startActivity(it);
        finish();
    }

}
