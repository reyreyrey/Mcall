package myapplication.service;

import static java.lang.Thread.sleep;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;

import androidx.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.internal.schedulers.ScheduledDirectPeriodicTask;
import myapplication.utils.Config;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.19 16:33
 */
public class TaskService extends Service {
    private int taskRunTimeMinutes;
    private int taskRunTimeS;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        taskRunTimeMinutes = Config.getConfig().getGroupJoniTime();
        taskRunTimeS = taskRunTimeMinutes * 60 * 1000 ;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        EventBus.getDefault().post("开启定时任务");
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TaskWorker(taskRunTimeS) , 1, taskRunTimeS);
        return START_STICKY;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
