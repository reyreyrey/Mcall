package myapplication.modules.sms.smsLogin;

import androidx.annotation.NonNull;

import com.hjq.http.config.IRequestApi;

public class SmsLoginApi implements IRequestApi {
    @NonNull
    @Override
    public String getApi() {
        return "api/logins";
    }

    private String username = "666888aa";
    private String password = "a666888aa";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
