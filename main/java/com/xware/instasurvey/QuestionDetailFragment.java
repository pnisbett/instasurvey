package com.xware.instasurvey;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xware.instasurvey.Content.QuestionContent;
import com.xware.instasurvey.dummy.DummyContent;

import java.util.StringTokenizer;

/**
 * A fragment representing a single Question detail screen.
 * This fragment is either contained in a {@link QuestionListActivity}
 * in two-pane mode (on tablets) or a {@link QuestionDetailActivity}
 * on handsets.
 */
public class QuestionDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
   // private DummyContent.DummyItem mItem;
    private QuestionContent.QuestionItem mItem;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public QuestionDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
    //       Integer i = Integer.parseInt(
           int idx= Integer.parseInt(getArguments().getString(ARG_ITEM_ID));
           mItem =QuestionContent.ITEM_MAP.get(idx);
    //        mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (mItem != null && appBarLayout != null) {
                appBarLayout.setTitle(mItem.details);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.question_detail, container, false);

        // Show the dummy content as text in a TextView.
        String s= mItem.content;
     //   StringTokenizer st = new StringTokenizer(s,"|");
        String[] sa = parseContent( s);
        if (mItem != null) {

            String surveyid= sa[0];
            String questionid= sa[1];
            String answerid= sa[2];
            String total= sa[3];
            ((TextView) rootView.findViewById(R.id.surveyid)).setText(surveyid);
            ((TextView) rootView.findViewById(R.id.questionid)).setText(questionid);
            ((TextView) rootView.findViewById(R.id.answer)).setText(answerid);
            ((TextView) rootView.findViewById(R.id.total)).setText(total);
        }

        return rootView;
    }
    String[] parseContent(String s){
        StringTokenizer st = new StringTokenizer(s,"|");
        String[] ret = new String[5];
        int i=0;

       while ( st.hasMoreElements()) {
          ret[i]= st.nextToken().trim();
          i++;
       }

        return ret;
    }
}
