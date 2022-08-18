package myapplication.base;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.15 17:38
 */
public class UAIntercept implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "MCall/1.2.6(iPhone;iOS 15.6;Scale/3.00)")
                .build();
        return chain.proceed(request);
    }
}
