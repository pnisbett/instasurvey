package com.xware.instasurvey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import android.view.ViewGroup.LayoutParams;
  // import	android.support.v4.view.PagerAdapter;
     //      import	android.support.v4.app.FragmentPagerAdapter;


import org.w3c.dom.Text;

import common.DBHelper;

public class AddQuestionActivity extends AppCompatActivity {
    private DBHelper mydb ;
  //  private SectionsPagerAdapter mSectionsPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mydb == null)
            mydb = new DBHelper(this);

        setContentView(R.layout.activity_add_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button bNew = (Button) findViewById(R.id.btnSurvey);


        EditText etNum = (EditText) findViewById(R.id.etsid);
        Log.e("etNum  ","etNum IS " + etNum.getText().toString());
       // Bundle b =
                Bundle extras = getIntent().getExtras();
        Integer value=0;
        if (extras != null) {
            value = extras.getInt("surveyId");
        }
      //  etNum.setText(value+"");
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

                EditText fd2 = null;
                EditText fd0 = null;

                Integer surveyId = 0;
                Integer questionSeq = 0;
                try {
                    fd2 = (EditText) findViewById(R.id.etsid);
                    fd0 = (EditText) findViewById(R.id.etqseq);

                    Log.e("FD2 = ", "FD2 IS " + fd2.getText().toString());
                    surveyId = Integer.parseInt(fd2.getText().toString());
                    questionSeq = Integer.parseInt(fd0.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(v.getContext(), "error parsing survey number" + e.getMessage(), Toast.LENGTH_LONG);
                    surveyId = -1;
                }

                EditText fd1 = (EditText) findViewById(R.id.etq);
                EditText fd3 = (EditText) findViewById(R.id.eta1);
                EditText fd4 = (EditText) findViewById(R.id.eta2);
                EditText fd5 = (EditText) findViewById(R.id.eta3);
                EditText fd6 = (EditText) findViewById(R.id.eta4);
                EditText fd7 = (EditText) findViewById(R.id.eta5);
                String f1 = fd1.getText().toString();
                String f2 = fd2.getText().toString();
                String f3 = fd3.getText().toString();
                String f4 = fd4.getText().toString();
                String f5 = fd5.getText().toString();
                String f6 = fd6.getText().toString();
                String f7 = fd7.getText().toString();
                //     boolean f6 = cb.isChecked();

             /*   try {
                    surveyId = Integer.parseInt(f2);
                }
                catch(Exception e){
                    Toast.makeText(v.getContext(),"error parsing survey number"+e.getMessage(),Toast.LENGTH_LONG);
                    surveyId=-1;
                }
                */


                Question q = new Question(0, surveyId, questionSeq, f2, f3, f4, f5, f6, f7);
                int upd = Update(q);
                if (upd > -1) {
                    Context context = v.getContext();
                    Log.i(" paths", "base context path " + getBaseContext() + "");
                    Bundle b = new Bundle();
                    b.putInt("surveyid", surveyId);
                    Intent intent = new Intent(context, AddQuestionActivity.class);
                    //                      intent.putExtras(b);

                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }

            }
        });
     //   mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
      //  mViewPager = (ViewPager) findViewById(R.id.container);
      //  mViewPager.setAdapter(mSectionsPagerAdapter);
       // Button bNew= (Button)findViewById(R.id.btnNew);
      /*  bNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(view.getContext(),com.xware.instasurvey.AddQuestionActivity.class);
                startActivity(i);
// update the page fragment
                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

                // Set up the ViewPager with the sections adapter.
                mViewPager = (ViewPager) findViewById(R.id.container);
                mViewPager.setAdapter(mSectionsPagerAdapter);

            }
        });
        */
    }
    public Integer Update(Question q ) {
        Bundle extras = getIntent().getExtras();

        int Value = q.id ; //("id");
        Integer surveyId =q.surveyId;
        Integer questionSeq=q.getSeqId();
        ArrayList<String> al= q.getAnswers();
        String s1= al.get(0);
        String s2= al.get(1);
        String s3= al.get(2);
        String s4= al.get(3);
        String s5= al.get(4);
        int r=-1;


            r= mydb.updateQuestion(surveyId,questionSeq,q.question,s1,s2,s3,s4,s5);
           q.id=r;


        return r;
    }

}
