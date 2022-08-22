package myapplication.base;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;

import myapplication.utils.Log2File;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.21 12:02
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static volatile CrashHandler crashHandler;

    private Context context;

    private CrashHandler(){}

    public void init(Context context){
        this.context = context;
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
        // 提示信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Log2File.exception(e.toString());
                Looper.loop();
            }
        }.start();

    }
}

