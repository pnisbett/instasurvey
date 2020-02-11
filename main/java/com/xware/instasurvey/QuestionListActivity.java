package com.xware.instasurvey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
//import android.support.design.widget.LinearLayout;
import android.widget.TextView;
import java.util.Comparator;
import java.util.Collections;

import com.xware.instasurvey.Content.QuestionContent;
import java.util.List;
import java.util.ArrayList;

/**
 * An activity representing a list of Questions. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link QuestionDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class QuestionListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private Integer surveyId;
private int windowwidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
     //   windowwidth =this.getParent().getWindow().getAttributes().width;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
     FrameLayout fl = (FrameLayout)findViewById(R.id.frameLayout);
        windowwidth= 300; //fl.getWidth();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "List of survey answers and totals for each answer on right", Snackbar.LENGTH_LONG)
                        .setAction("List of survey answers and totals for each answer on right", null).show();
            }
        });

        if (findViewById(R.id.question_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        if (windowwidth==0)
               windowwidth=50;
        StringBuilder sb = new StringBuilder();
         /*
       for (int i=0;i<windowwidth -4;i++)
            sb.append("-");

        String line55= sb.toString() ;// "_____________________________________________________" ;
        String pad6="Answers"+"                       ";
       TextView ch= (TextView)findViewById(R.id.colheader);
        TextView lb= (TextView)findViewById(R.id.linebar);
String pad6="Answers"+"                       ";
      ch.setText("Survey Id|Quest Id|"+pad6 +"|Count");
        lb.setText(line55);
     //  ch.setTextColor(R.android.);
      //  ch.setPadding(0,0,0,0);
       ch.setHeight(35);
        lb.setTextColor(R.color.colorPrimaryDark);
        lb.setText(line55);
        lb.setPadding(0,0,0,0);
        lb.setHeight(5);

         */
        //c.moveToFirst();
       // try {
          //  hm.put(0,"Survey Id|Quest Id|"+pad6 +"|Count");
         //  hm.put(1,line55);
        View recyclerView = findViewById(R.id.question_list);
       assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

private List<Question> getQuestions(Integer sid){
    ArrayList<Question> qa = new  ArrayList<Question>();
   common.DBHelper dh= new common.DBHelper(this);
     qa= dh.getQuestions( sid);
    return qa;

}
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        QuestionContent.createQuestionContent(this);
        Collections.sort(QuestionContent.ITEMS,new SortById());
     //   QuestionContent.ITEMS.remove(0);

      recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(new Header(),this, QuestionContent.ITEMS, mTwoPane));
    //    recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, QuestionContent.ITEMS, mTwoPane));

    }
   public class SortById implements Comparator<QuestionContent.QuestionItem>{
       public int compare(QuestionContent.QuestionItem q1,QuestionContent.QuestionItem q2){
           return q1.id-q2.id;

       }
    }
    public class SortByValues implements Comparator<QuestionContent.QuestionItem>{
        public int compare(QuestionContent.QuestionItem q1,QuestionContent.QuestionItem q2){
            return 0 ; //q1.content.-q2.content;

        }
    }

    public class Header {
        //    More fields can be defined here after your need
        private String header;
        private String linebar;

        public Header(){
            String pad6="Answers"+"                       "+"              ";
           setHeader("Survey Id|Quest Id|"+pad6 +"|Count");
            int l=   header.length();
         //   StringBuilder sb =new StringBuilder();
         //   for (int i=0;i<l; i++)
           //     sb.append("-");
       //     linebar=sb.toString();
            setLineBar("---------------------------------------------------------------------------------");
       //   setLineBar("______________________________________");
        }

        public String getHeader() {
            return header;
        }
        public String getLineBar(){
            return linebar;
        }
        public void setHeader(String header) {
            this.header = header;
        }
        public void setLineBar(String linebar) {
            this.linebar = linebar;
        }
    }

    /*
    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {
        */

    public static class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private static final int TYPE_HEADER = 0;
        private static final int TYPE_ITEM = 1;

        Header header;
        private final QuestionListActivity mParentActivity;
        private final List<QuestionContent.QuestionItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          //    int idx=  mValues.indexOf(view.getTag());
             //   QuestionContent.QuestionItem item = (QuestionContent.QuestionItem) view.getTag();
                //Integer i = Integer.parseInt(view.getTag().toString());
                String idx = view.getTag().toString();
             //   QuestionContent.QuestionItem qi = mValues.get(mValues.indexOf(vt));
           //     Integer idx = mValues.indexOf(vt);
                try{
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    //     arguments.putString(QuestionDetailFragment.ARG_ITEM_ID, item.id+"");
                    arguments.putString(QuestionDetailFragment.ARG_ITEM_ID, idx);
                    QuestionDetailFragment fragment = new QuestionDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.question_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, QuestionDetailActivity.class);
                    intent.putExtra(QuestionDetailFragment.ARG_ITEM_ID, idx);
                //    intent.putExtra(QuestionDetailFragment.ARG_ITEM_ID, i + "");


                    context.startActivity(intent);
                }
                }
                catch(Exception e){
                    Log.e("answer click exception",e.getMessage());

                }
            /*    */
            }
        };



        class VHHeader extends RecyclerView.ViewHolder{
             TextView linebar;
             TextView colHeader;
            public VHHeader(View itemView) {
                super(itemView);
                this.colHeader = (TextView)itemView.findViewById(R.id.colheader);
               this.linebar = (TextView)itemView.findViewById(R.id.linebar);
               /*
                StringBuilder sb =new StringBuilder();
int l =this.colHeader.length();
                for (int i=0;i<l; i++)
                    sb.append("-");
                this.linebar.setText(sb.toString());
                //     linebar=sb.toString();
              linebar.setText("------------------------------------------------");
*/
            }
        }

        class VHItem extends RecyclerView.ViewHolder{
            final TextView mContentView;

            public VHItem(View itemView) {
                super(itemView);
                this.mContentView = (TextView)itemView.findViewById(R.id.content);

            }
        }
        SimpleItemRecyclerViewAdapter(Header header,QuestionListActivity parent,
                                      List<QuestionContent.QuestionItem> items,
                                      boolean twoPane) {
            mValues = items;
            this.header = header;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }
/*
        SimpleItemRecyclerViewAdapter(QuestionListActivity parent,
                                      List<QuestionContent.QuestionItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

/*
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.question_list_content, parent, false);
            return new ViewHolder(view);
        }

*/
//@Override
     public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
  /*  View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.question_list_content, parent, false);
    return new ViewHolder(view);
*/
    if (viewType == TYPE_HEADER) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_item, parent, false);
        return new VHHeader(v);
    }
    else if (viewType == TYPE_ITEM) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_list_content, parent, false);
        return new VHItem(v);
    }
       throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
       }
      //  @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
   // holder.itemView.setText("");
            if(holder instanceof VHHeader)
            {
                VHHeader VHheader = (VHHeader)holder;
                VHheader.colHeader.setText(header.getHeader());
               VHheader.linebar.setText(header.getLineBar());
            }
            else if(holder instanceof VHItem)
            {
             /*   ListItem currentItem = getItem(position-1);
                VHItem VHitem = (VHItem)holder;
                VHitem.txtName.setText(currentItem.getName());
                VHitem.iv.setBackgroundResource(currentItem.getId());
                */
                VHItem VHitem = (VHItem)holder;
                VHitem.mContentView.setText(mValues.get(position).content);
             //  holder.mContentView.setText("fuck android");
            // ((VHItem) holder).mContentView
         //        holder.mContentView.setText(mValues.get(position).content);
         //       VHitem.itemView.setTag(mValues.get(position).id+"");
                VHitem.itemView.setTag(mValues.get(position).id+"");
                VHitem.itemView.setOnClickListener(mOnClickListener);
       //        holder.itemView.setTag(mValues.get(position).id+"");
              //  holder.itemView.setOnClickListener(mOnClickListener);
            }
        }


        //    need to override this method
        @Override
        public int getItemViewType(int position) {
            if(isPositionHeader(position))
                return TYPE_HEADER;
            return TYPE_ITEM;
        }

        private boolean isPositionHeader(int position)
        {
            return position == 0;
        }/*
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

          //  holder.mIdView.setText(mValues.get(position).id+"");
            holder.mContentView.setText(mValues.get(position).content);
            holder.itemView.setTag(mValues.get(position).id+"");
            holder.itemView.setOnClickListener(mOnClickListener);
        }
*/
        @Override
        public int getItemCount() {
            return mValues.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            //final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
              //  mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);

            }
            @Override
            public String toString(){
                return super.toString()+"'"+mContentView.getText() +"'";
            }
        }
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
        switch (item.getItemId()) {
            case R.id.action_home:
                home();
                return true;
            case R.id.help:
                showHelp();
                return true;

            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    void home(){
        try{
            Context c =QuestionListActivity.this;
            Intent i= new Intent(c,com.xware.instasurvey.MainActivity.class);
            startActivity(i);
        }
        catch(Exception e){
            Log.e("ShowAnswers", "onClick: ", e);
        }
        Log.e("home_called","home called");
    }
    void showHelp(){

        try{
            Context c =QuestionListActivity.this;
            Intent i= new Intent(c, HelpActivity.class);
            startActivity(i);
        }
        catch(Exception e){
            Log.e("ShowHelp", "onClick: ", e);
        }

        Log.e("show_help","showHelp called");
    }
}
