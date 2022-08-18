package myapplication.modules.sms.getPhoneNum;

import java.io.Serializable;

public class GetPhoneNumBean implements Serializable {

    private String message;
    private String mobile;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
