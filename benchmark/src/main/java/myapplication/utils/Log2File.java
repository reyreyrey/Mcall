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

    private static boolean  logInit;
    private static BufferedWriter writer, errorwriter;
    private static BufferedReader reader;

    private Log2File()
    {

    }

    /**
     * 初始化Log,创建log文件
     * @param ctx
     * @param fileName
     * @return
     */
    public static boolean init(Context ctx, String fileName)
    {
        if(!logInit)
        {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state))
            {
                File sdDir = Environment.getExternalStoragePublicDirectory("");
                LogUtils.e("---->", sdDir.toString());
                File logDir = new File(sdDir.toString() + "/log2file/" );

                try {
                    if(!logDir.exists())
                    {
                        logDir.mkdirs();
                    }

                    File logFile = new File(logDir, fileName);
                    logFile.createNewFile();
                    File errorfile = new File(logDir, "error.txt");
                    errorfile.createNewFile();

                    reader = new BufferedReader(new FileReader(logFile));

                    writer = new BufferedWriter(new FileWriter(logFile, true));
                    errorwriter = new BufferedWriter(new FileWriter(errorfile, true));
                    logInit = true;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    LogUtils.e("---->", e.toString());
                }

            }else{
                LogUtils.e("---->", "11111111111");
            }

        }

        return logInit;
    }

    /**
     * 写一条log
     * @param msg
     */
    public static void w(String msg)
    {
        if(logInit)
        {
            try {
                Date date = new Date();
                writer.write( msg);
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
        }
    }

    public static void exception(String msg)
    {
        if(logInit)
        {
            try {
                errorwriter.write( msg);
                errorwriter.newLine();
                errorwriter.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
        }
    }

    public static String getLog(){
        if(logInit){
            try{
                StringBuffer sb = new StringBuffer();
                String buffer;
                while((buffer = reader.readLine()) != null){
                    sb.append(buffer);
                }
                return sb.toString();
            }catch (Exception e){}
        }
        return null;
    }
    /**
     * 关闭log
     */
    public static void close()
    {
        if(logInit)
        {
            try {
                writer.close();
                writer = null;
                reader.close();
                reader = null;

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            logInit = false;
        }
    }
}
