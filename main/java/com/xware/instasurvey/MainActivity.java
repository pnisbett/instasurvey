package com.xware.instasurvey;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
// import android.support.v4.app.FragmentPagerAdapter;
// import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
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
import java.util.List;
import android.view.ViewGroup.LayoutParams;
import common.DBHelper;
import 	android.content.res.ColorStateList;



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
    public  static ArrayList<String> answers;
    public static ArrayList<Answer> responseAnswer;

    /**

     * The {@link ViewPager} that will host the section contents.

     */

    private ViewPager mViewPager;
    static int horizontalLoc=0;
    static int tvheight=35;
    static DBHelper db;
    public int surveyId=0;


    public void getSurvey(int surveyid,View view){
        qa= getQuestions(surveyid);
        int count   =mSectionsPagerAdapter.getCount();

        if (count>0) {

            for (int i = count - 1; i > -1; i--) {
                mViewPager.removeView(mViewPager.getChildAt(i));
                //     mSectionsPagerAdapter.destroyItem(mViewPager,i, mSectionsPagerAdapter.getItem(i)); //.removeFragment(i);
                Fragment f =mSectionsPagerAdapter.getItem(i);
                mSectionsPagerAdapter.removeFragment(i);
                //     f =null;
                mSectionsPagerAdapter.notifyDataSetChanged();
            }

        }

        Log.e("FRAGMENT ADDED" ,"fragmentlist size after initial removal of all ="+mSectionsPagerAdapter.mFragmentList.size()) ;
        // Set up the ViewPager with the sections adapter.

        mViewPager = (ViewPager) findViewById(R.id.container);
        Integer qid =0;
        Integer sid =0;
        int cnt=0;
        String answer ="";

        Context c = view.getContext();

        if (cnt <0 )
            cnt=0;
        for(Question q:qa) {
            //     qid=q.getId().intValue();
            sid=q.getSurveyId()+cnt;
            qid =q.seqId ; //sid+cnt;
            ArrayList<String> sa = q.getAnswers();
            String[] sAnswer = {"","",""};
            Integer i =1;
            Integer qqid =1;
            String[] saa= getStringArray(sa); //{"","",""};
            RadioGroup rg= PlaceholderFragment.makeAnswerGroup(surveyId,qid,saa,c);
            TextView tv = new TextView(view.getContext());
            tv.setId(qid);
            tv.setText("dynamically created line 125 MainActivity "+q.id+"  "+q.question);
            Fragment f =mSectionsPagerAdapter.getItem(cnt);//new sunday
            mViewPager.addView(tv,cnt);
            mViewPager.addView(rg,cnt);
            mSectionsPagerAdapter.notifyDataSetChanged(); //new sunday
            cnt++;

        }
        mViewPager.setCurrentItem(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        db = new DBHelper(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int sn=1;


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        // pn    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.

        mViewPager = (ViewPager) findViewById(R.id.container);

        mViewPager.setAdapter(mSectionsPagerAdapter);
//adds new survey questin
        Button bNew= (Button)findViewById(R.id.btnNew);

        bNew.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
               newQuestion(view);

            }

        });

        Button bSave= (Button)findViewById(R.id.btnSave);
        bSave.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                db = new DBHelper(view.getContext());
                Integer qid =0;
                Integer sid =0;
                EditText sn=(EditText)findViewById(R.id.surveyNumber);
                String s =sn.getText().toString();
                if (s.equals(""))
                    s="0";
                sid= Integer.parseInt(s);
                String answer ="";
                int cnt =0;
                int i =0;
                saveResponseAnswers(responseAnswer);
                for(Question q:qa) {
                    qid=q.getId().intValue();
                    sid=q.getSurveyId();
                    RadioGroup rg=(RadioGroup)findViewById(q.id);
                    if (rg != null) {
                        int c = rg.getChildCount();
                        for (int ii=0;ii<c;ii++) {
                            RadioButton rb =(RadioButton)rg.getChildAt(ii);
                            if (rb.isChecked())
                                rb.setChecked(false);

                        }
                    }
                }
                mViewPager.setCurrentItem(0);
            }

        });

        Button bSurvey= (Button)findViewById(R.id.bSurvey);

        bSurvey.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                EditText sn=(EditText)findViewById(R.id.surveyNumber);
                String s =sn.getText().toString();
                if (s.equals(""))
                    s="0";
                int surveyid= Integer.parseInt(s);
                getSurvey(surveyid,view) ;

                }

        });


        Button bShowAnswers= (Button)findViewById(R.id.btnShowAnswers);
        bShowAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAnswers(view);

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                Snackbar.make(view, "Click a button to start", Snackbar.LENGTH_LONG)
                        .setAction("Click a button to start", null).show();

            }

        });
        Intent intent = getIntent();
        // if (intent.getIntExtra("surveyNumber",1) )
        sn = intent.getIntExtra("surveyNumber",1);

        if (sn != 0){
            //  Bundle b =savedInstanceState;
            //  sn = (Integer)b.get("surveyNumber");
            getSurvey(sn, bSurvey);
        }

         this.surveyId=sn;
        //  qa = getQuestionsDemo();
        qa= getQuestions(sn);
        // initialize answer array
        answers = new ArrayList<String>();
        responseAnswer = new ArrayList<Answer>();
        for (int i = 0; i < qa.size(); i++){
            answers.add("");
            //   responseAnswer.add("");
        }

    }
    protected void newQuestion(View view) {

        Intent i= new Intent(view.getContext(),com.xware.instasurvey.AddQuestionActivity.class);
     //   EditText esn=(EditText)findViewById(R.id.surveyNumber);
       Integer snum=  this.surveyId ;// Integer.parseInt(esn.getText().toString());
        i.putExtra("surveyId",snum);
        startActivity(i);

// update the page fragment
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }
    protected void showAnswers(View view){
        EditText esn=(EditText)findViewById(R.id.surveyNumber);

        db = new DBHelper(view.getContext());
        Integer qid =0;
        Integer sid =0;
        try{
       //     sid = Integer.parseInt(esn.getText().toString());
            sid =this.surveyId;
            Intent i= new Intent(view.getContext(),com.xware.instasurvey.QuestionListActivity.class);
            startActivity(i);
            i.putExtra("surveyId",sid);
            startActivity(i);
        }
        catch(Exception e){
            Log.e("ShowAnswers", "onClick: ", e);
        }
    }
    public static String[] getStringArray(ArrayList<String> arr)
    {
        // declaration and initialise String Array
        String str[] = new String[arr.size()];
        // ArrayList to Array Conversion
        for (int j = 0; j < arr.size(); j++) {
            // Assign each value to String array
            str[j] = arr.get(j);
        }
        return str;
    }

    ArrayList<String> getAnswers(ViewPager vp){
        int ci;
        ArrayList<String> als = new ArrayList<String>();
        ci=vp.getChildCount();
        for (int i=0;i<ci;i++ ) {
            vp.getChildAt(i);
        }
        return als;
    }

    private static  void AddToAnswers(Integer qid,String an){
        String an1 = an.substring(0,30);
        while ( answers.size()<qid+1)
            answers.add(an1);
        answers.set(qid,an1);
    }

    private  static void AddToResponseAnswers(Answer a){
            responseAnswer.add(a);

    }
     public Integer saveResponseAnswers(ArrayList<Answer> al){
         long l=-1;
        for (int i=0;i<al.size();i++) {
            Answer a = new Answer(al.get(i).surveyId,al.get(i).questionId,al.get(i).answer);
           try {
            l=   db.insertAnswer(a.surveyId, a.questionId, a.answer);
           }
           catch (Exception e){
               Log.e("SAVERESPONSE",e.getMessage());
               return -1;
            }
        }
        return Long.valueOf(l).intValue();
     }


    private static ArrayList<Question>  getQuestions() {
        ArrayList<Question> qa = new ArrayList<Question>();
        qa = db.getQuestions(1);
        return qa ;
    }

    private static ArrayList<Question>  getQuestions(Integer surveyId) {
        ArrayList<Question> qa = new ArrayList<Question>();
        qa = db.getQuestions(surveyId);
        return qa ;
    }

    private static ArrayList<Question>  getQuestions(int surveyid) {
        ArrayList<Question> qa = new ArrayList<Question>();
        qa = db.getQuestions(surveyid);
        return qa ;
    }

    private static ArrayList<Question>  getQuestionsDemo(){
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
        if (qa !=null)
            return qa.size();
        return 0;
    }

    private void answerOnClick(){
      int ci= mViewPager.getCurrentItem();
        if (ci <4)
            mViewPager.setCurrentItem(ci+1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    void showHelp(){
        Log.e("show_help","showHelp called");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.show_answers:
                showAnswers(item.getActionView());
                return true;
            case R.id.new_question:
                newQuestion(item.getActionView());
                return true;
            case R.id.help:
                showHelp();
                return true;
            case  R.id.action_settings :
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }



    /**

     * A placeholder fragment containing a simple view.

     */

    public static class PlaceholderFragment extends Fragment {

        /**

         * The fragment argument representing the section number for this

         * fragment.

         */
        private  int  sectionNumber;
        private String  title;
        String[] answers;
        String question;
        Integer surveyId;
        Integer questionSeq;
        String[] responseanswer;
        public PlaceholderFragment() {

        }



        /**

         * Returns a new instance of this fragment for the given section

         * number.

         */

        public static PlaceholderFragment newInstance(int secNumber) {

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
            //   ARG_SECTION_NUMBER = sectionNumber;
            int sectionNumber =secNumber; //secNumber is the fragment associated witheach question
            Question q =qa.get(secNumber);
         //   q.setId(secNumber);
            //  q.setSurveyId();
            PlaceholderFragment fragment = new PlaceholderFragment();
            // answers.add(new Integer(-1));
            Bundle args = new Bundle();
            String[] sa =  q.getAnswers().toArray(new String[q.getAnswers().size()]);
            args.putInt("ARG_SECTION_NUMBER", secNumber);
            args.putString("title", "section "+secNumber);
            args.putInt("surveyId",q.getSurveyId());
            args.putInt("questionId",q.getId());
            args.putInt("questionSeq",q.getSeqId());
            args.putString("question", q.getQuestion());
            args.putStringArray("answers",sa );
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            sectionNumber = getArguments().getInt("ARG_SECTION_NUMBER", 0);
            title = getArguments().getString("title");

            question= getArguments().getString("question");
            questionSeq= getArguments().getInt("questionSeq");
            surveyId=getArguments().getInt("surveyId");
            answers = getArguments().getStringArray("answers");
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
Log.e("QUESTION CONTAINER","container id = "+container.getParent().toString()+"");
            View v = (View)container.getParent();
            EditText etSnum=(EditText)v.findViewById(R.id.surveyNumber);
            int sid = 1;
            //Integer.parseInt(etSnum.getText().toString());
            Log.e("QUESTION PARENT CONTAINER","container id = "+v.getId()+"");
            Log.e("QUESTION Survey NUMBER =","survey number = "+etSnum.getText().toString()+"");
            int height = 0;
            int width =0;
            int offset = 10;
            responseanswer = new String[6] ;
            for (int i=0;i<6 ;i++){
                responseanswer[i]="";
            }

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            LinearLayout cl =(LinearLayout) rootView.findViewById(R.id.constraintLayout);
            String id="q"+R.id.section_label;

            TextView tvSectionLabel = rootView.findViewById(R.id.section_label);
            tvSectionLabel.setPadding(10,0,0,0);
        //    tvSectionLabel.setTextColor(R.color.colorPrimaryDark);
            tvSectionLabel.setTextColor(R.color.colorBlack);
            tvSectionLabel.setText("Section Number : " + this.sectionNumber);
            Integer.parseInt(R.id.section_label+"");

            TextView tvQuestionSeq = rootView.findViewById(R.id.tvqseq);
            tvQuestionSeq.setTextColor(R.color.colorPrimaryDark);
            tvQuestionSeq.setText(this.questionSeq+"");
            tvQuestionSeq.setPadding(10,0,0,0);
            tvQuestionSeq.setTextColor(R.color.colorBlack);


            TextView tvQuestion = rootView.findViewById(R.id.tvq);
            tvQuestion.setTextColor(R.color.colorPrimaryDark);
            tvQuestion.setText(this.question);
            tvQuestion.setPadding(10,0,0,0);
            tvQuestion.setTextColor(R.color.colorBlack);

            String[]sa =this.answers;
            RadioGroup rg =makeAnswerGroup(sid,this.sectionNumber, sa ,this.getContext());

            //   LinearLayout ll =rootView.findViewById(R.id.container);

            if (rg != null)

                cl.addView(rg);

            //   TextView tvQuestionLabel = rootView.findViewById(R.id.section_label);

       //     tvSectionLabel.setText("Section Num =" + this.sectionNumber);

            //     int idx=getArguments().getInt(ARG_SECTION_NUMBER);

            //    Question q= qa.get(getArguments().getInt(ARG_SECTION_NUMBER));
/*
            if (savedInstanceState != null) {

                Question q = (Question) savedInstanceState.get("ARG_SECTION_NUMBER");



                TextView tv = makeTextView(id, q.question, offset);



                cl.addView(tv);

                sa = q.getAnswers().toArray(new String[q.getAnswers().size()]);

                Integer qid = q.id;

                rg = makeAnswerGroup(qid, sa ,this.getContext());

                cl.addView(rg);



            }
*/
        /*    else{

                Question q = (Question) savedInstanceState.get("ARG_SECTION_NUMBER");



                TextView tv = makeTextView(id, q.question, offset);



                cl.addView(tv);

                String[] sa = q.getAnswers().toArray(new String[q.getAnswers().size()]);

                Integer qid = q.id;

                RadioGroup rg = makeAnswerGroup(qid, sa ,this.getContext());

                cl.addView(rg);

            }

*/

            return rootView;

        }



        public static RadioGroup makeAnswerGroup(Integer sid,Integer qid, String[] sa ,Context c ){
    //   public static RadioGroup makeAnswerGroup(Answer a ,Context c ){
            // RadioGroup rg = new RadioGroup(this.getContext());
            RadioGroup rg = new RadioGroup(c);
            rg.setId(qid);
            int idx = 0;
            final int q2 =  qid.intValue();
            final int fsid = sid.intValue();
            LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            int cur=0;
// Long l =Math.round(Math.random());

            for(String s:sa) {
                ColorStateList colorStateList = new ColorStateList(
                        new int[][]{

                                new int[]{-android.R.attr.state_enabled}, //disabled
                                new int[]{android.R.attr.state_enabled} //enabled
                        },
                        new int[] {

                                Color.GRAY //disabled
                                ,Color.BLACK //enabled

                        }
                );



                //   RadioButton rb = new RadioButton(this.getContext());
                RadioButton rb = new RadioButton(c);
                rb.setButtonTintList(colorStateList);

                rb.setText(s);
                rb.setId(q2 * 100 + idx+1);
                rg.addView(rb,idx,lp);
final String fs =s;
                rb.setOnClickListener(new View.OnClickListener() {

                    @Override

                    public void onClick(View view) {

                        RadioButton rb2 =(RadioButton)view;

                        if (rb2.isChecked()){

                            //    AddToAnswers(q2, rb2.getId());
                            Answer a=Answer.makeAnswer(fsid,q2,fs);
                            AddToResponseAnswers(a);



                        }

                    }

                });

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

    public class SectionsPagerAdapter1 extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter1(FragmentManager manager) {
            super(manager);
        }

        @Override
        public int getItemPosition(Object object){
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f= mFragmentList.get(position);
            if (f==null) {
                f = PlaceholderFragment.newInstance(position);
                addFragment(f, (String)f.getArguments().get("title"),  position);
            }
            return mFragmentList.get(position);
        }


        @Override
        public int getCount() {
            int c =getQuestioncount();
            return mFragmentList.size();
        }


        public void addFragment(Fragment fragment, String title, int position) {
            mFragmentList.add(position, fragment);
            mFragmentTitleList.add(position, title);
        }
        public void removeFragment(Fragment fragment, int position) {
            mFragmentList.remove(position);
            mFragmentTitleList.remove(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {

            super(fm);

        }

        @Override
        public int getItemPosition(Object object){
            return PagerAdapter.POSITION_NONE;
        }


        @Override
        public Fragment getItem(int position) {
            Fragment f;

            if (mFragmentList.size()> position)
                f=  mFragmentList.get(position) ;
            else {
                f = PlaceholderFragment.newInstance(position);
                mFragmentList.add(f);

            }
            return f;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void removeFragment(int position) {
            if (position< mFragmentList.size())
                mFragmentList.remove(position);
        }

        // @Override

        public Fragment getItem1(int position) {

            //We are doing this only for checking the total number of fragments in the fragment manager.

            List<Fragment> fragmentsList = getSupportFragmentManager().getFragments();

            int size = 0;

            if (fragmentsList != null) {

                size = fragmentsList.size();

            }

            Question q =qa.get(position);

            //    Utils.DummyItem dummyItem = mDummyItems.get(position);

            //   Log.i(TAG, "********getItem position:" + position + " size:" + size + " title:" + dummyItem.getImageTitle() + " url:" + dummyItem.getImageUrl());



            //Create a new instance of the fragment and return it.

            //  SampleFragment sampleFragment = (SampleFragment) SampleFragment.getInstance(/*dummyItem.getImageUrl(), dummyItem.getImageTitle()*/);

            PlaceholderFragment pf = (PlaceholderFragment)PlaceholderFragment.newInstance(position);

            //We will not pass the data through bundle because it will not gets updated by calling notifyDataSetChanged()  method. We will do it through getter and setter.

            //  sampleFragment.setDummyItem(dummyItem);

            // return sampleFragment;

            return pf;

        }

    }

}






