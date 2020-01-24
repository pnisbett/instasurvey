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
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.util.Comparator;
import java.util.Collections;
import com.xware.instasurvey.dummy.DummyContent;
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
    //    windowwidth =this.getParent().getWindow().getAttributes().width;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        FrameLayout fl = (FrameLayout)findViewById(R.id.frameLayout);
        windowwidth=fl.getWidth();

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
    //    if (windowwidth==0)
     //           windowwidth=50;
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<windowwidth -4;i++)
            sb.append("_");

        String line55= sb.toString() ;// "_____________________________________________________" ;
        String pad6="Answers"+"                       ";
        TextView ch= (TextView)findViewById(R.id.colheader);
        TextView lb= (TextView)findViewById(R.id.linebar);
        ch.setText("Survey Id|Quest Id|"+pad6 +"|Count");
        lb.setText(line55);
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
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, QuestionContent.ITEMS, mTwoPane));
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
    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

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

        SimpleItemRecyclerViewAdapter(QuestionListActivity parent,
                                      List<QuestionContent.QuestionItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.question_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

          //  holder.mIdView.setText(mValues.get(position).id+"");
            holder.mContentView.setText(mValues.get(position).content);
            holder.itemView.setTag(mValues.get(position).id+"");
            holder.itemView.setOnClickListener(mOnClickListener);
        }

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
        }
    }
}
