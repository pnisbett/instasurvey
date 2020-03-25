package common;

import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.InstrumentationRegistry;
import android.test.*;
import android.util.Log;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.*;
import java.util.stream.Stream;

import android.content.Context;
import android.widget.LinearLayout;

@RunWith(AndroidJUnit4.class)
public class DBHelperTest  extends  AndroidTestCase{
    private DBHelper db;
    private Context context;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        context =  InstrumentationRegistry.getTargetContext();
    //    RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
    //    db = new DBHelper(context);
        db = new DBHelper(context);
    }
    @After
    public void finish() {
        db.close();
    }
    @Test
    public void onCreate() {
    }

    @Test
    public void onUpgrade() {
    }

    @Test
    public void updateQuestion() {
    }

    @Test
    public void insertAnswer() {
    }

    @Test
    public void getQuestions() {
      //  DBHelper db = new DBHelper(this);
    }

    @Test
    public void getAllQuestions() {
    }

    @Test
    public void getResponses() {
    }

    @Test
    public void getAllAnswers() {
    }

    @Test
    public void getAllResponses() {
    }

    @Test
    public void writeAllAnswersToXML() {
        Log.i("WriteAllAnswers ", "all answers!!!!");
        try {
            //          setUp();
            //       RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
            //      db = new DBHelper(context);
            String s = db.writeAllAnswersToXML();
            Log.i("WriteallAnswers ", s);
        }  catch(Exception e){

            Log.e("WriteallQeustionsException ", e.getMessage());
        }
    }

    @Test
    public void writeAllQuestionsToXML() {
       try {
 //          setUp();
    //       RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
     //      db = new DBHelper(context);
           String s = db.writeAllQuestionsToXML();
           Log.i("WriteallQeustions ", s);
       }  catch(Exception e){

           Log.e("WriteallQeustionsException ", e.getMessage());
           }
    }

    @Test
    public void makePadArray() {
    }
    private File appStorageDirectory(String fname) {
        File appFilesDirectory = context.getFilesDir();
        return new File(appFilesDirectory, fname);
    }
    @Test
    public void writeXMLFile() {
     //   File file = new File(DBHelperTest.this.getFilesDir(), "text");
   //     this.getFilesDir();
        String s = db.writeAllQuestionsToXML();
        Log.i("WriteallQuestions inwritexmal file", s);
        File f = appStorageDirectory("QuestionData.xml");
      // File f2 =  new File(appStorageDirectory("QuestionData"));
        Util.writeDataToFile(f.getAbsolutePath(),"QuestionData.xml",s);
        //    Util.writeDataToFile("/data/data/com.xware.instasurvey","QuestionData",s);
        //  String f = "//data//data//com.xware.instasurvey"+ "//QuestionData" ;
        int i =0;
        try {
            Reader r = new FileReader(f);
            BufferedReader br = new BufferedReader(r);
        //   i =r.read();
        String    ss=  br.readLine();
          while (ss!=null){
              Log.i("Line ",ss);
              ss=  br.readLine();

            }


           Stream<String> bs =br.lines();
           while (bs.iterator().hasNext()){
               Log.i("Line ",bs.toString());
           }
           bs.close();
            r.close();
        }
        catch (FileNotFoundException ff) {
            Log.e(" writeXMLFile",ff.getMessage()) ;

        }
        catch (java.io.IOException o) {
            Log.e(" writeXMLFile",o.getMessage()) ;
        }
        Log.i("Questiondata length" , "length= "+i+" ");
    }
  //  @Test
    public void readXMLFile() {

    /*    String s = db.writeAllQuestionsToXML();
        Log.i("WriteallQuestions inwritexmal file", s);
        File f =appStorageDirectory("QuestionData");
        Util.writeDataToFile(f.getAbsolutePath(),"QuestionData",s);
        */
        //    Util.writeDataToFile("/data/data/com.xware.instasurvey","QuestionData",s);
        //  String f = "//data//data//com.xware.instasurvey"+ "//QuestionData" ;
        File f =appStorageDirectory("QuestionData");
        try {
            Reader r = new FileReader(f);
            BufferedReader br = new BufferedReader(r);
            int i =r.read();
            r.close();
        }
        catch (FileNotFoundException ff) {


        }
        catch (java.io.IOException o) {

        }
    }

}