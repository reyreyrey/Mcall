package myapplication.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import myapplication.utils.Log2File;
import myapplication.utils.LogUtils;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.21 12:02
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static volatile CrashHandler crashHandler;

    private Context context;

    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    private CrashHandler(){}

    public void init(Context context){
        this.context = context;
        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();//获取当前默认ExceptionHandler，保存在全局对象
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static CrashHandler getCrashHander(){
        if (crashHandler == null){
            synchronized (CrashHandler.class){
                if (crashHandler == null){
                    crashHandler = new CrashHandler();
                }
            }
        }
        return crashHandler;
    }

    @Override
    public void uncaughtException(Thread t, final Throwable e) {
        dumpToSDCard(t, e);//dump trace 信息到sd卡
        e.printStackTrace();
        if (mDefaultExceptionHandler != null) { //交给系统的UncaughtExceptionHandler处理
            mDefaultExceptionHandler.uncaughtException(t, e);
        } else {
            Process.killProcess(Process.myPid()); //主动杀死进程
        }
    }

    private void dumpToSDCard(final Thread t, final Throwable e) {
        LogUtils.e("---->", e.toString());
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            LogUtils.e("---->", "111111");
            return;
        }
        LogUtils.e("---->", "222222");
        String mLodPath  = Environment.getExternalStoragePublicDirectory("").getAbsolutePath() + "/log2file/";
        File file = new File(mLodPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        String time = new SimpleDateFormat("yyyy-mm-dd-HH:mm:ss", Locale.CHINA).format(new Date(System.currentTimeMillis()));
        File logFile = new File(mLodPath, time + ".txt");


        try {
            logFile.createNewFile();
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(logFile)));
            pw.println(time);
            dumpPhoneInfo(pw);
            pw.println();
            e.printStackTrace(pw);
            pw.close();

        } catch (IOException e1) {
            e1.printStackTrace();
            LogUtils.e("---->2", e1.toString());
        }
    }

    private void dumpPhoneInfo(PrintWriter pw) {
        //应用的版本名称和版本号
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                pw.print("App Version: ");
                pw.print(pi.versionName);
                pw.print('_');
                pw.println(pi.versionCode);

                //android版本号
                pw.print("OS Version: ");
                pw.print(Build.VERSION.RELEASE);
                pw.print("_");
                pw.println(Build.VERSION.SDK_INT);

                //手机制造商
                pw.print("Vendor: ");
                pw.println(Build.MANUFACTURER);

                //手机型号
                pw.print("Model: ");
                pw.println(Build.MODEL);

                //cpu架构
                pw.print("CPU ABI: ");
                pw.println(Build.CPU_ABI);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

}

