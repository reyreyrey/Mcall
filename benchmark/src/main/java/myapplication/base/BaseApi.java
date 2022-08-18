package myapplication.base;

import androidx.annotation.NonNull;

import com.hjq.http.config.IRequestApi;

public class BaseApi implements IRequestApi {
    @NonNull
    @Override
    public String getApi() {
        return "";
    }

    private String param;




    public String getParam() {
        return param;
    }

    public BaseApi setParam(String param) {
        this.param = param;
        return this;
    }
}
