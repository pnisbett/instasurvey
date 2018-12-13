package com.xware.instasurvey;

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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import android.view.ViewGroup.LayoutParams;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
   private static int questioncount=5;
   private static  ArrayList<Question> qa;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    static int horizontalLoc=0;
    static int tvheight=35;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //ArrayList<Question>();
        qa= getQuestions();
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        Button bNew= (Button)findViewById(R.id.btnNew);
 bNew.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         Intent i= new Intent(view.getContext(),com.xware.instasurvey.AddQuestionActivity.class);
         startActivity(i);

     }
 });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
    private static ArrayList<Question>  getQuestions(){
        ArrayList<Question> qa = new ArrayList<Question>();

        String[] s1= {"1","2","3","4","9"};
        String[] s2= {"Micky","Davey","Peter","Mike"};
        String[] s3= {"Ford","Chevy","Dodge","Subaru","Toyota"};
        String[] s4= {"Under 10","10-20","20-40","over 40"};
        String[] s5={"Ford","Trump","Carter","Bush"};
        ArrayList<String> answers = new ArrayList<String>(Arrays.asList(s1));

        Question q = new Question();
        q.setQuestion("How many planets in our Solar system?");
        q.setAnswers(answers);
        qa.add(q);



        q = new Question("Who is your favorite Monkey?");
        answers = new ArrayList<String>(Arrays.asList(s2));
        q.setAnswers(answers);
        qa.add(q);

        q = new Question("What is your favorite car?");
        answers = new ArrayList<String>(Arrays.asList(s3));
        q.setAnswers(answers);
        qa.add(q);

        q = new Question("How old are you?");
        answers = new ArrayList<String>(Arrays.asList(s4));
        q.setAnswers(answers);
        qa.add(q);

        q = new Question("Current President?");
        answers = new ArrayList<String>(Arrays.asList(s5));
        q.setAnswers(answers);
        qa.add(q);

        return qa;
    }
private int getQuestioncount(){

        //placholer vlaue
    if (qa !=null)
        return qa.size();
    return 0;
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
           // PlaceholderFragment fragment = new PlaceholderFragment();
           //
        //    PlaceholderFragment fragment =PlaceholderFragment.newInstance(sectionNumber,qa.get(sectionNumber));

         //   Bundle args = new Bundle();
         //   args.putInt(ARG_SECTION_NUMBER, sectionNumber);
          //  fragment.setArguments(args);
          //  return fragment;
        //}
        //public static PlaceholderFragment newInstance(int sectionNumber,Question q) {
            // ArrayList<Question> qa = new   ArrayList<Question>();
           // ArrayList<Question> qa =getQuestions();
                    Question q =qa.get(sectionNumber);
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            String[] sa =  q.getAnswers().toArray(new String[q.getAnswers().size()]);
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString("question", q.getQuestion());
            args.putStringArray("answers",sa );
            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int height = 0;
            int width =0;
            int offset = 10;
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        /*    TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            if (horizontalLoc==0)
                horizontalLoc=textView.getTop() + textView.getHeight() + 20;
            else
                horizontalLoc= horizontalLoc+40;
       */
         //     ConstraintLayout cl =(ConstraintLayout) rootView.findViewById(R.id.constraintLayout);
            LinearLayout cl =(LinearLayout) rootView.findViewById(R.id.constraintLayout);
        //    RelativeLayout cl =(RelativeLayout) rootView.findViewById(R.id.constraintLayout);
        //    tv.setText("test");
            String id="q"+R.id.section_label;

            //
        //    ArrayList<Question> qa= getQuestions();//new   ArrayList<Question>();
            int idx=getArguments().getInt(ARG_SECTION_NUMBER);
            Question q= qa.get(getArguments().getInt(ARG_SECTION_NUMBER));
            TextView tv=makeTextView(id,q.question,offset);

            cl.addView(tv);
            String[] sa = q.getAnswers().toArray(new String[q.getAnswers().size()]);
            int qid =q.id;
            RadioGroup rg=makeAnswerGroup(qid,sa );
            cl.addView(rg);



            return rootView;
        }
        private RadioGroup makeAnswerGroup(int qid, String[] sa){
            RadioGroup rg = new RadioGroup(this.getContext());
            int idx = 0;
            LayoutParams lp = new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            for(String s:sa) {
                RadioButton rb = new RadioButton(this.getContext());
                rb.setText(s);
                rb.setId(qid * 100 + idx+1);
                rg.addView(rb,idx,lp);
                idx++;
            }
            return rg;
        }
        private TextView makeTextView(String id ,String val,int offset){
     //       TableRow.LayoutParams lparams = new TableRow.LayoutParams(
       //             TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            LayoutParams lparams = new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            TextView tv=new TextView(this.getContext());
       //     lparams = new TableRow.LayoutParams(
         //   TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        //    lparams = new LayoutParams(
          //          LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lparams.height=tvheight;
            lparams.width=350;
            tv.setGravity(Gravity.LEFT);
         //   lparams.setMargins(5,5,5,5);
          //  lparams.topMargin=offset;
          //  lparams.leftMargin=25;
            tv.setBackgroundColor(Color.WHITE);
            tv.setTextColor(Color.BLACK);
            tv.setText(val);
            tv.setLayoutParams(lparams);

            return tv;

        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
        //    int c = getCount();
          //  if (position< c-1 )
          //  return PlaceholderFragment.newInstance(position + 1);
           // else
                return PlaceholderFragment.newInstance(position);

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            int c =getQuestioncount();
            return c;
        }
    }
}
