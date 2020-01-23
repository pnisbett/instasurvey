package com.xware.instasurvey.Content;

import java.time.chrono.IsoEra;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import common.DBHelper;


/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class QuestionContent  {

    /**
     * An array of sample (dummy) items.
     * /
    public static final List<QuestionContent.QuestionItem> ITEMS = new ArrayList<QuestionContent.QuestionItem>();

    / **
     * A map of sample (dummy) items, by ID.
     * /
    public static final Map<String, QuestionContent.QuestionItem> ITEM_MAP = new HashMap<String, QuestionContent.QuestionItem>();
*/
    public static List<QuestionContent.QuestionItem> ITEMS = new ArrayList<QuestionContent.QuestionItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static  Map<Integer, QuestionContent.QuestionItem> ITEM_MAP = new HashMap<Integer, QuestionContent.QuestionItem>();
/*
    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createQuestionItem(i));
        }
    }

*/

    public static Map<Integer, String> createQuestionContent(Context c){
            ITEMS.clear();
            ITEM_MAP.clear();
        DBHelper dh = new DBHelper(c);
    //    dh.getQuestions();
     //  Map<Integer, QuestionContent.QuestionItem> qc=dh.getAllQuestions();
        Map<Integer, String> ac= dh.getAllResponses();
        int cnt =ac.size();
        System.out.println("create quesiton map size -" +cnt);
      Set<Map.Entry<Integer,String>> es = ac.entrySet();


    Iterator<Map.Entry<Integer,String>> it= es.iterator();

        addItem(createQuestionItem(0,"survey id|quest id|answer|count "));
    while (it.hasNext()) {
        Map.Entry<Integer,String> e =it.next();
        Integer i = e.getKey();

                //   Integer i = it.next().getKey();
        String s="";
        try {
           s = e.getValue();
        }
        catch(java.util.NoSuchElementException ex){
            s= " record number i throws " + "NoSuchElement Exception";
        }
        addItem(createQuestionItem(i,s));
    }

        return ac;
    }
    private static void addItem(QuestionItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static QuestionContent.QuestionItem createQuestionItem(int position,String content) {
        String details = makeDetails(position);
        return new QuestionContent.QuestionItem(position, content,  details);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class QuestionItem {
        public final Integer id;
        public final String content;
        public final String details;

        public QuestionItem(Integer id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
