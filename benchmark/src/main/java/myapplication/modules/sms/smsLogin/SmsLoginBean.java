package myapplication.modules.sms.smsLogin;

import java.io.Serializable;
import java.util.List;

public class SmsLoginBean implements Serializable {

    private List<Data> data;

    private String message;

    private String token;

    public List<Data> getData() {
        return this.data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class Data implements Serializable {
        private String leixing;

        private String money;

        private String ip;

        private String id;

        private String money_1;

        public String getLeixing() {
            return this.leixing;
        }

        public void setLeixing(String leixing) {
            this.leixing = leixing;
        }

        public String getMoney() {
            return this.money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getIp() {
            return this.ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMoney_1() {
            return this.money_1;
        }

        public void setMoney_1(String money_1) {
            this.money_1 = money_1;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "leixing='" + leixing + '\'' +
                    ", money='" + money + '\'' +
                    ", ip='" + ip + '\'' +
                    ", id='" + id + '\'' +
                    ", money_1='" + money_1 + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SmsLoginBean{" +
                "data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
