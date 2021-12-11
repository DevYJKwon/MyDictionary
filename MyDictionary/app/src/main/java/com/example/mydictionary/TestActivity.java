package com.example.mydictionary;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {
    DBmanager dbmanager;
    SQLiteDatabase sqlitedb;
    int i;
    Cursor cursor;



    LinearLayout layout;
    LinearLayout layout_item;

    String str_word;
    String str_noun;
    String str_verb;
    String str_adj;
    String str_adv;
    int wrong;

    String answer_noun;
    String answer_verb;
    String answer_adj;
    String answer_adv;

    EditText et_noun;
    EditText et_verb;
    EditText et_adj;
    EditText et_adv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);

        layout = (LinearLayout)findViewById(R.id.testLayoutList);

        i=0;
        printDB();
    }
    public void onClickAnswer(View v){
        checkAnswer();
        i++;
        printDB();
    }
    public void printDB(){

        try{
            dbmanager = new DBmanager(this);
            sqlitedb = dbmanager.getReadableDatabase();
            cursor = sqlitedb.query("Word",null,null,null,null,null,null);
            if(cursor.moveToPosition(i)) {

                str_word = cursor.getString(cursor.getColumnIndex("word"));
                str_noun = cursor.getString(cursor.getColumnIndex("noun"));
                str_verb = cursor.getString(cursor.getColumnIndex("verb"));
                str_adj = cursor.getString(cursor.getColumnIndex("adj"));
                str_adv = cursor.getString(cursor.getColumnIndex("adv"));
                wrong = cursor.getInt(cursor.getColumnIndex("wrong"));

                layout.removeView(layout_item);

                layout_item = new LinearLayout(this);


                layout_item.setOrientation(LinearLayout.VERTICAL);
                layout_item.setPadding(20, 10, 20, 10);

                TextView tv_word = new TextView(this);
                tv_word.setText(str_word);
                tv_word.setTextSize(30);
                tv_word.setTextColor(Color.argb(255, 0, 0, 0));
                layout_item.addView(tv_word);

                if (!str_noun.isEmpty()) {
                    TextView tv_noun = new TextView(this);
                    tv_noun.setText("명사: ");
                    tv_noun.setTextSize(20);
                    layout_item.addView(tv_noun);

                    et_noun = new EditText(this);
                    et_noun.setHint("답을 입력해주세요.");
                    layout_item.addView(et_noun);
                    et_noun.setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            //Enter key Action
                            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow( et_noun.getWindowToken(), 0);
                                return true;
                            }
                            return false;
                        }
                    });
                }
                if (!str_verb.isEmpty()) {
                    TextView tv_verb = new TextView(this);
                    tv_verb.setText("동사: ");
                    tv_verb.setTextSize(20);
                    layout_item.addView(tv_verb);

                    et_verb = new EditText(this);
                    et_verb.setHint("답을 입력해주세요.");
                    layout_item.addView(et_verb);
                    et_verb.setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            //Enter key Action
                            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow( et_verb.getWindowToken(), 0);
                                return true;
                            }
                            return false;
                        }
                    });
                }
                if (!str_adj.isEmpty()) {
                    TextView tv_adj = new TextView(this);
                    tv_adj.setText("형용사: ");
                    tv_adj.setTextSize(20);
                    layout_item.addView(tv_adj);

                    et_adj = new EditText(this);
                    et_adj.setHint("답을 입력해주세요.");
                    layout_item.addView(et_adj);
                    et_adj.setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            //Enter key Action
                            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow( et_adj.getWindowToken(), 0);
                                return true;
                            }
                            return false;
                        }
                    });
                }
                if (!str_adv.isEmpty()) {
                    TextView tv_adv = new TextView(this);
                    tv_adv.setText("부사: ");
                    tv_adv.setTextSize(20);
                    layout_item.addView(tv_adv);

                    et_adv = new EditText(this);
                    et_adv.setHint("답을 입력해주세요.");
                    layout_item.addView(et_adv);
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
                layout.addView(layout_item);
            }
            else{
                layout.removeView(layout_item);
                TextView tv_finish = (TextView) findViewById(R.id.finishText);
                tv_finish.setVisibility(View.VISIBLE);
                cursor.close();
                sqlitedb.close();
                dbmanager.close();
            }
        }
        catch(SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void checkAnswer(){
        if(cursor.moveToPosition(i)) {
            if(et_noun == null ){
                answer_noun = null;
            }
            else{
                answer_noun = et_noun.getText().toString();
            }
            if(et_verb == null ){
                answer_verb = null;
            }
            else{
                answer_verb = et_verb.getText().toString();
            }
            if(et_adj == null ){
                answer_adj = null;
            }
            else{
                answer_adj = et_adj.getText().toString();
            }
            if(et_adv == null ){
                answer_adv = null;
            }
            else{
                answer_adv = et_adv.getText().toString();
            }

            int wrong_c=0;
            if(!str_noun.isEmpty() && !(str_noun.equals(answer_noun))){
                wrong_c=1;
            }
            if(!str_verb.isEmpty() && !(str_verb.equals(answer_verb))){
                wrong_c=1;
            }
            if(!str_adj.isEmpty() && !(str_adj.equals(answer_adj))){
                wrong_c=1;
            }
            if(!str_adv.isEmpty() && !(str_adv.equals(answer_adv))){
                wrong_c=1;
            }
            if(wrong_c != 0){
                Toast.makeText(this,"틀렸습니다.",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this,"정답입니다.",Toast.LENGTH_SHORT).show();
            }
            try {
                dbmanager = new DBmanager(this);
                sqlitedb = dbmanager.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("wrong", wrong+wrong_c);

                long newRowId = sqlitedb.update("Word", values, "word=?", new String[]{str_word});

            } catch(SQLiteException e) {
                Toast.makeText(this,  e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else{
            return;
        }
    }
}
