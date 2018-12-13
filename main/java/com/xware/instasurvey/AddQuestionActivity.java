package com.xware.instasurvey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import common.DBHelper;

public class AddQuestionActivity extends AppCompatActivity {
    private DBHelper mydb ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mydb == null)
            mydb = new DBHelper(this);
        setContentView(R.layout.activity_add_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button bNew = (Button) findViewById(R.id.btnSurvey);
        bNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), com.xware.instasurvey.MainActivity.class);
                startActivity(i);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Enter question and up to 5 possible answers", Snackbar.LENGTH_LONG)
                        .setAction("Enter question and up to 5 possible answers", null).show();
            }
        });


        Button addButton = (Button) findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText fd1 = (EditText) findViewById(R.id.editText1);
                EditText fd2 = (EditText) findViewById(R.id.editText2);
                EditText fd3 = (EditText) findViewById(R.id.editText3);
                EditText fd4 = (EditText) findViewById(R.id.editText4);
                EditText fd5 = (EditText) findViewById(R.id.editText5);
                EditText fd6 = (EditText) findViewById(R.id.editText6);

                String f1 = fd1.getText().toString();
                String f2 = fd2.getText().toString();
                String f3 = fd3.getText().toString();
                String f4 = fd4.getText().toString();
                String f5 = fd5.getText().toString();
                String f6 = fd6.getText().toString();
                //     boolean f6 = cb.isChecked();


                String response = "good";
                // temp diabled smarty streets
          /*      if (add.length()>3 )
                    response=validateAdress(add);
            */
                if (response.equals("good")) {

                    Question q = new Question(0L, f1, f2, f3, f4, f5, f6);
                    boolean upd = Update(q);
                    if (upd) {
                        Context context = v.getContext();
                        Log.i(" paths", "base context path " + getBaseContext() + "");

                        Intent intent = new Intent(context, MainActivity.class);
                        //                      intent.putExtras(b);

                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public boolean Update(Question q ) {
        Bundle extras = getIntent().getExtras();

        long Value = q.id ; //("id");
        ArrayList<String> al= q.getAnswers();
        String s1= al.get(0);
        String s2= al.get(1);
        String s3= al.get(2);
        String s4= al.get(3);
        String s5= al.get(4);
        if(Value>0){

           // String s1= al.get(5);
            if(mydb.updateQuestion(q.id,q.question,s1,s2,s3,s4)==0){
                return true;

            }
            else{
                return false;

            }
        } else{

            long id =mydb.insertQuestion(q.id,q.question,s1,s2,s3,s4);
            if(id > -1){
                q.id=id;
                return true;

            }
            else{
                return false;

            }
;
        }
    }

}
