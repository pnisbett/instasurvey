package common;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import android.util.Log;
import android.os.Environment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.stream.Stream;

public class Util {
    private DBHelper db;
    private Context context;
    static String questionFile = "QuestionData.xml";
    static String answerFile = "AnswerData.xml";
    public Util(Context ct) throws Exception {
        super();
          context = ct; // InstrumentationRegistry.getTargetContext();
        //    RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        //    db = new DBHelper(context);
        db = new DBHelper(context);
    }
    public Util() throws Exception {
        super();
      //  context =  InstrumentationRegistry.getTargetContext();
        //    RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        //    db = new DBHelper(context);
        db = new DBHelper(context);
    }

    public static void writeDataToFile(String path,String fileName,String data){
        File f =null;
            try {
                //     FileOutputStream fos = c.openFileOutput(xmlFile, 1);
              f   = new File(path); //+"//"+fileName);
            f.createNewFile();
              if (  f.exists()) {
                  FileWriter fw = new FileWriter(f);
                  BufferedWriter bw = new BufferedWriter(fw);
                  bw.write(data);
                  bw.flush();
                  bw.close();
        //          fw.flush();
          //        fw.close();
              }
              else
                  Log.e("FileNotFoundException","wher the fuck is "+ f.getName() );

            } catch (FileNotFoundException f1) {
                Log.e("FileNotFoundException", "Path=" + f.getPath() + "    name= " +f.getName()+f1.getMessage() +f1.getStackTrace().toString());


            }
            catch (java.io.IOException o) {

                Log.e("IOException","Path=" + f.getPath() + "name= " +f.getName()+o.getMessage() +o.getStackTrace().toString());

            }

    }

    private final String filenameInternal = "";
    private final String filenameExternal = "";




    public void writeFileInternalStorage(String is) {
       // String coupons = "Get upto 20% off mobile @ xyx shop \n Get upto 30% off on appliances @ yuu shop";
        createUpdateFile(filenameInternal, is, false);
    }

    public void appendFileInternalStorage(String is) {
      //  String coupons = "Get upto 50% off fashion @ xyx shop \n Get upto 80% off on beauty @ yuu shop";
        createUpdateFile(filenameInternal, is, true);
    }

    private void createUpdateFile(String fileName, String content, boolean update) {
        FileOutputStream outputStream;

        try {
            if (update) {
                outputStream = openFileOutput(fileName, Context.MODE_APPEND);
            } else {
                outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            }
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readFileInternalStorage() {
        StringBuffer sb=null;
        try {
            FileInputStream fileInputStream = openFileInput(filenameInternal);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));

            sb = new StringBuffer();
            String line = reader.readLine();

            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /*
    public void createTemporaryFile(String fileName) {
        try {
          //  String fileName = "couponstemp";
          //  String coupons = "Get upto 50% off shoes @ xyx shop \n Get upto 80% off on shirts @ yuu shop";

            File file = File.createTempFile(fileName, null, getCacheDir());

            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(fileName.getBytes());
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
        }
    }

    public void deleteFile(String fileName) {
        try {

            File file = File.createTempFile(fileName, null, getCacheDir());

            file.delete();
        } catch (IOException e) {
        }
    }
*/
    public void writeFileExternalStorage(String filenameExternal ,String data) {
     //   String cashback = "Get 2% cashback on all purchases from xyz \n Get 10% cashback on travel from dhhs shop";
        String state = Environment.getExternalStorageState();
        //external storage availability check
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            return;
        }
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), filenameExternal);


        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            //second argument of FileOutputStream constructor indicates whether to append or create new file if one exists
            outputStream = new FileOutputStream(file, true);

            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private File appStorageDirectory(String fname) {
        File appFilesDirectory = context.getFilesDir();
        return new File(appFilesDirectory, fname);
    }
    private FileInputStream openFileInput(String filenameInternal) throws FileNotFoundException{
        File f = appStorageDirectory(filenameInternal);
        FileInputStream fis = new FileInputStream(f);
        return fis;
    }
    private FileOutputStream openFileOutput(String filenameInternal,int mode) throws FileNotFoundException{
        File f = appStorageDirectory(filenameInternal);
        FileOutputStream fis = new FileOutputStream(f);
        return fis;
    }
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
}
