package myapplication.base;

import android.util.Log;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.30 14:06
 */
public class TokenAuthenticator implements Authenticator {
    @Override
    public Request authenticate(Route route, Response response) throws IOException {


        Request request = response.request();
        Log.e("---->TokenAuthenticator header", request.headers().toString());
        Log.e("---->TokenAuthenticator body", request.body().toString());
        Log.e("---->TokenAuthenticator response", response.body().string());
        // 去获取新的 token，采用同步请求方式
//        String newToken = service.refreshToken.execute().body();
//        // 保存新的 token
//        Config.saveToken(newToken);
        return response.request().newBuilder()
//                .addHeader("Authorization",newToken)
                .build();
    }
}
