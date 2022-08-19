package myapplication;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.hjq.http.EasyConfig;
import com.hjq.http.ssl.HttpSslConfig;
import com.hjq.http.ssl.HttpSslFactory;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;

import org.litepal.LitePal;

import java.util.HashMap;
import java.util.List;

import myapplication.base.RequestHandler;
import myapplication.base.RequestServer;
import myapplication.base.UAIntercept;
import myapplication.bean.ConfigBean;
import myapplication.utils.Config;
import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class MyApp extends MultiDexApplication implements Application.ActivityLifecycleCallbacks {
    public static OkHttpClient okHttpClient;
    String  keywords =
            "财务,cw,CW,ZB,zb,主持,管理,机器人,亚泰,招财猫,财财,客服";
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        init();
        registerActivityLifecycleCallbacks(this);
    }
    private static HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    private static String getCacheKey(HttpUrl url) {
        return url.host() + ":" + url.port();
    }
    String PHPSESSID = "";
    void init(){


        HttpSslConfig sslConfig = HttpSslFactory.generateSslConfig();
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new ChuckerInterceptor(getApplicationContext()))
                .addInterceptor(new CookieInterceptor())
                .addInterceptor(new UAIntercept())
//                .cookieJar(new CookieJarImpl(new MemoryCookieStore()))
                .sslSocketFactory(sslConfig.getSslSocketFactory(), sslConfig.getTrustManager())
                .hostnameVerifier(HttpSslFactory.generateUnSafeHostnameVerifier())
                .build();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor("okgo---->");
        interceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient1 = new OkHttpClient.Builder()
//                .addInterceptor(new ChuckerInterceptor(getApplicationContext()))
                .addInterceptor(new CookieInterceptor())
//                .cookieJar(new CookieJarImpl(new MemoryCookieStore()))
                .sslSocketFactory(sslConfig.getSslSocketFactory(), sslConfig.getTrustManager())
                .hostnameVerifier(HttpSslFactory.generateUnSafeHostnameVerifier())
                .build();

        OkGo.getInstance()                      //必须调用初始化
                .setOkHttpClient(okHttpClient1) //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(0)
                .init(this);



        EasyConfig.with(okHttpClient)
                // 是否打印日志
                .setLogEnabled(true)
                // 设置服务器配置
                .setServer(new RequestServer())
                // 设置请求处理策略
                .setHandler(new RequestHandler(this))
                // 设置请求重试次数
                .setRetryCount(3)
                // 添加全局请求参数
                //.addParam("token", "6666666")
                // 添加全局请求头
                //.addHeader("time", "20191030")
                // 启用配置
                .into();

        EasyConfig.getInstance().addHeader("User-Agent", "MCall/1.2.6(iPhone;iOS 15.6;Scale/3.00)");


        if(Config.getConfig() == null){
            ConfigBean configBean = new ConfigBean();
            configBean.setRegCount(10);
            configBean.setGroupJoniTime(30);
            configBean.setNickNameKeyWords(keywords);
            configBean.setConfigid(1);
            configBean.save();
        }

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static FragmentActivity fragmentActivity;

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        fragmentActivity = (FragmentActivity) activity;
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        fragmentActivity = null;
    }
}
