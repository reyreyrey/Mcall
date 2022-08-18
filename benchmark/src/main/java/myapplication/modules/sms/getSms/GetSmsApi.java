package myapplication.modules.sms.getSms;

import androidx.annotation.NonNull;

import com.hjq.http.config.IRequestApi;

public class GetSmsApi implements IRequestApi {
    @NonNull
    @Override
    public String getApi() {
        return "api/get_message";
    }

    private String token;
    private String project_id = "23693";
    private String phone_num;
    private double random;

    public double getRandom() {
        return random;
    }

    public GetSmsApi setRandom(double random) {
        this.random = random;
        return this;
    }

    public String getToken() {
        return token;
    }

    public GetSmsApi setToken(String token) {
        this.token = token;
        return this;
    }

    public String getProject_id() {
        return project_id;
    }

    public GetSmsApi setProject_id(String project_id) {
        this.project_id = project_id;
        return this;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public GetSmsApi setPhone_num(String phone_num) {
        this.phone_num = phone_num;
        return this;
    }
}
