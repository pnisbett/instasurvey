package common;



/**
 * Created by paul on 1/2/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import com.xware.instasurvey.Question;


public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACTS_TABLE_NAME = "survey";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }



     static String[] padArray = makePadArray();
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
     //    db.execSQL("DROP TABLE IF EXISTS questions");
     //   db.execSQL("DROP TABLE IF EXISTS answers");
        db.execSQL(
                "create table if not exists questions " +
                        "(id INTEGER primary key autoincrement,survey_id INTEGER,question_seq INTEGER,question TEXT,answer1 TEXT,answer2 TEXT,answer3 TEXT,answer4 TEXT,answer5 TEXT )"
        );

        db.execSQL(
                "create table if not exists answers " +
                        "(id INTEGER primary key autoincrement,survey_id INTEGER,question_seq INTEGER,question INTEGER,answer1 TEXT)"
        );
        //  db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        //   don't fucking over write the goddamn db!!!!
        //   db.execSQL("DROP TABLE IF EXISTS contacts");
   //     db.execSQL("DROP TABLE IF EXISTS questions");
     //     db.execSQL("DROP TABLE IF EXISTS answers");
      //     onCreate(db);
    }
//temporary mirror of insert
    public Integer updateQuestion(Integer surveyId,Integer questionSeq,String question, String answer1, String answer2, String answer3, String answer4,String answer5) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("survey_id", surveyId);
        contentValues.put("question_seq", questionSeq);
        contentValues.put("question", question);
        contentValues.put("answer1", answer1);
        contentValues.put("answer2", answer2);
        contentValues.put("answer3", answer3);
        contentValues.put("answer4", answer4);
        contentValues.put("answer5", answer5);
        String[] sa= {surveyId+"",questionSeq+""};
        Cursor c= db.rawQuery("select count(*) from questions where survey_id = ? and question_seq=?",sa);
        c.moveToFirst();
        int cnt=c.getInt(0);
        long id=-1;
        if (cnt > 0) {

            id = db.update("questions", contentValues, "survey_id=? and question_seq=?", sa);
        }
        else{
           id= db.insert("questions", null, contentValues);
        }
       // long id= db.insert("questions", null, contentValues);
     return (int)id;
   /*     if (id < 0)
            return -1 ;
        //        return  getLastInserted();

        return 0;
        *
    */
    }
    /*
    public long insertQuestion(Integer questionid,Integer surveyId,String question, String answer1, String answer2, String answer3, String answer4,String answer5) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put ("survey_id",surveyId);
        contentValues.put("question", question);
        contentValues.put("answer1", answer1);
        contentValues.put("answer2", answer2);
        contentValues.put("answer3", answer3);
        contentValues.put("answer4", answer4);
        contentValues.put("answer5", answer5);

        long id= db.insert("questions", null, contentValues);
        if (id < 0)
            return -1L ;
        //        return  getLastInserted();

       return id;
    }
*/

    public Integer insertAnswer(Integer questionid,Integer surveyId,Integer question, String answer1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put ("survey_id",surveyId);
        contentValues.put("question", question);
        contentValues.put("answer", answer1);


        long id= db.insert("answers", null, contentValues);
        if (id < 0)
            return -1 ;
        //        return  getLastInserted();

       return 0;
    }

    public ArrayList<Question> getQuestions(int surveyId){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] sa= {surveyId+""};
        int ct=-1;
     //  Cursor c= db.rawQuery("select * from questions where survey_id = ?",sa);
        Cursor c= db.rawQuery("select * from questions",null);// where survey_id = ?",sa);
        if (c != null) {
         ct= c.getCount();
        }
       // primary key autoincrement,survey_id INTEGER,question TEXT,answer1 TEXT,answer2 TEXT,answer3 TEXT,answer4 TEXT,answer5 TEXT

       int cc = c.getColumnCount();
       Question[] qa = new Question[ct];
       ArrayList<Question> alq = new ArrayList<Question>();
       c.moveToFirst();
        try {
            while (!c.isAfterLast()) {
                int id=c.getInt(0);
                int sid=c.getInt(1);
                int qsid=c.getInt(2);
            //    int i3=c.getInt(2);
                String sq =c.getString(3);
                String sa1 =c.getString(4);
                String sa2 =c.getString(5);
                String sa3 =c.getString(6);
                String sa4 =c.getString(7);
                String sa5 =c.getString(8);
                Question q = new Question(id,sid,qsid,sq,sa1,sa2,sa3,sa4,sa5);

                alq.add(q);
               c.moveToNext();
            }
        } finally {
            c.close();
        }
        return alq;
        //return alq.toArray(qa);

    }


    public ArrayList<Question> getResponses(int surveyId){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] sa= {surveyId+""};
        Cursor c= db.rawQuery("select * from answers where survey_id = ?",sa);

        int ct=c.getCount();
        int cc = c.getColumnCount();
        Question[] qa = new Question[ct];
        ArrayList<Question> alq = new ArrayList<Question>();
        c.moveToFirst();
        try {
            while (!c.isAfterLast()) {
                int i1=c.getInt(0);
                int i2=c.getInt(1);
                int i3=c.getInt(2);
                //    int i3=c.getInt(2);
                String sq =c.getString(3);
                String sa1 =c.getString(4);
                String sa2 =c.getString(5);
                String sa3 =c.getString(6);
                String sa4 =c.getString(7);
                String sa5 =c.getString(8);
                Question q = new Question(i1,i2,i3,sq,sa1,sa2,sa3,sa4,sa5);

                alq.add(q);
                c.moveToNext();
            }
        } finally {
            c.close();
        }
        return alq;
        //return alq.toArray(qa);

    }
    /*
    public  HashMap<Integer,String> getAllQuestions(){
        SQLiteDatabase db = this.getWritableDatabase();
        //     String[] sa= {surveyId+""};
        Cursor c= db.rawQuery("select * from questions ",null);

// cols = id INTEGER primary key autoincrement,survey_id INTEGER,question INTEGER,answer1 TEXT";
        int ct=c.getCount();
        int cc = c.getColumnCount();
        HashMap<Integer,String> hm = new HashMap<Integer,String>();
        int i=0;
        c.moveToFirst();
        try {
            while (!c.isAfterLast()) {
                int sid=c.getInt(0);
                int sidoffset = 6-sid;

                int quid =c.getInt(1);
                int quidoffset = 6-quid;
                String answer ="answer" ; //c.getString(2);
                int  aoffset = 10 - answer.length();
                int qcount =c.getInt(3);
                int coffset= 7-qcount;
                String ret = "|" + padArray[sidoffset]+sid+"|"+ padArray[quidoffset]+quid+"|"+ padArray[aoffset]+answer+"|"+padArray[coffset]+"|"  ;
                // Question q = new Question(i1,i2,sq,sa1,sa2,sa3,sa4,sa5);

                hm.put(i,ret);
                c.moveToNext();
            }
        } finally {
            c.close();
        }
        return hm;
        //return alq.toArray(qa);

    }

     */
    public  HashMap<Integer,String> getAllResponses(){
        SQLiteDatabase db = this.getWritableDatabase();
   //     String[] sa= {surveyId+""};
        Cursor c= db.rawQuery("select survey_id, question ,answer1, count(answer1) cnt from answers group by survey_id,question,answer1",null);
// cols = id INTEGER primary key autoincrement,survey_id INTEGER,question INTEGER,answer1 TEXT";
        int ct=c.getCount();
        int cc = c.getColumnCount();
        HashMap<Integer,String> hm = new HashMap<Integer,String>();
        int i=0;
        c.moveToFirst();
        try {
            hm.put(i,"survey id|quest id|answer|count ");
            while (!c.isAfterLast()) {
                Integer sid=c.getInt(0);
                Integer sidoffset = 6-sid.toString().length();

                Integer quid =c.getInt(1);
                Integer quidoffset = 6-quid.toString().length();
                String answer =c.getString(2);
                Integer  aoffset = 10 - answer.length();
                Integer qcount =c.getInt(3);
                Integer coffset= 7-qcount.toString().length();
                String ret = "|" + padArray[sidoffset]+sid+"|"+ padArray[quidoffset]+quid+"|"+ padArray[aoffset]+answer+"|"+padArray[coffset]+qcount +"|" ;
            // Question q = new Question(i1,i2,sq,sa1,sa2,sa3,sa4,sa5);

                hm.put(i,ret);
                i++;
                c.moveToNext();
            }
        } finally {
            c.close();
        }
        return hm;
        //return alq.toArray(qa);

    }

    private String makePadding(int offset){
        StringBuilder sb = new StringBuilder();

        for(int i=0;i< offset;i++)
        sb.append(" ");

        return  sb.toString();

    }
   static  String[] makePadArray(){
        String[] sa= new String[100];
        StringBuilder sb = new StringBuilder();

        for (int i=1;i<101;i++){
            sb.append(" ");

            sa[i-1] = sb.toString();
        }
        return sa;
    }
}
