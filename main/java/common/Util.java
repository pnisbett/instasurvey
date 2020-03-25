package common;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import android.util.Log;

public class Util {

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

}
