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




    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        //  db.execSQL("DROP TABLE IF EXISTS contacts");
        db.execSQL(
                "create table if not exists questions " +
                        "(id INTEGER primary key autoincrement,survey_id INTEGER,question TEXT,answer1 TEXT,answer2 TEXT,answer3 TEXT,answer4 TEXT,answer5 TEXT )"
        );

        //  db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        //   don't fucking over write the goddamn db!!!!
        //   db.execSQL("DROP TABLE IF EXISTS contacts");
        //   onCreate(db);
    }
//temporary mirror of insert
    public Long updateQuestion(Long questionid,String question, String answer1, String answer2, String answer3, String answer4,String answer5) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
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
    public Long insertQuestion(Long questionid,String question, String answer1, String answer2, String answer3, String answer4,String answer5) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
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








}
