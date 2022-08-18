package myapplication;


import java.io.IOException;

import myapplication.base.RequestHandler;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CookieInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader("cookie", "think_var=zh-Hans; "+ RequestHandler.PHPSESSID)
                .build();

        return  chain.proceed(request);
    }
}
