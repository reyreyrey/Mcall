package myapplication.utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.16 14:05
 */
public class Log2File {


    private Log2File()
    {

    }

    private static String getBasePath(){
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)){
            File sdDir = Environment.getExternalStoragePublicDirectory("");
            File logDir = new File(sdDir.toString() + "/log2file/" );
            if(!logDir.exists())
            {
                logDir.mkdirs();
            }
            return logDir.getAbsolutePath();
        }
        return "";
    }

    public static boolean write(String message, String Fullpath){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(Fullpath), true));
            bw.write( message);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            LogUtils.e("---->", "write error:"+e.toString());
            return false;
        }
        return true;
    }

    public static String read(String Fullpath){
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(Fullpath)));
            StringBuffer sb = new StringBuffer();
            String buffer;
            while((buffer = br.readLine()) != null){
                sb.append(buffer);
            }
            return sb.toString();
        } catch (IOException e) {
            LogUtils.e("---->", "read error:"+e.toString());
        }
        return "";
    }

    public static boolean writeRegUser(String s){
        return write(s, new File(getBasePath(), "log.txt").getAbsolutePath());
    }
    public static String readRegUser(){
        return read(new File(getBasePath(), "log.txt").getAbsolutePath());
    }

    public static boolean writeFindUser(String s){
        return write(s, new File(getBasePath(), "search_users.txt").getAbsolutePath());
    }
    public static String readFindUser(){
        return read(new File(getBasePath(), "search_users.txt").getAbsolutePath());
    }
    public static void newFindUserFile(){
        File f = new File(getBasePath(), "search_users.txt");
        f.delete();
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
