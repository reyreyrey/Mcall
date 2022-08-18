package myapplication.modules.login;


import myapplication.base.BaseApi;
import myapplication.utils.MD5Utils;

public final class LoginApiByPhoneNum extends BaseApi {


    public static class LoginRequestParams  {
        private String clientId = "6DAAA90A-BDF9-4605-B22A-7DF11B1FCEE9";
        private String mobile ;
        private String iosPushClientId = "543a23d75879b1036baa08d8b9e400cf3b22803146ef0c59333a020dd94df1dd";
        private String deviceId = "3D12B822-1D0F-4C78-B61A-984B58D37C22";
        private String platform = "1";
        private String appVersion = "1.2.6";
        private String city = "86";
        private String captcha ;
        private String systemVersion = "15.5";
        private String device = "2";
        private String model = "iPhone12";

        private String method = "api/userInfo/login";

        public String getMobile() {
            return mobile;
        }

        public LoginRequestParams setMobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCaptcha() {
            return captcha;
        }

        public LoginRequestParams setCaptcha(String captcha) {
            this.captcha = captcha;
            return this;
        }

        public String getAppVersion() {
            return this.appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

        public String getClientId() {
            return this.clientId;
        }

        public LoginApiByPhoneNum.LoginRequestParams setClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }


        public String getIosPushClientId() {
            return this.iosPushClientId;
        }

        public void setIosPushClientId(String iosPushClientId) {
            this.iosPushClientId = iosPushClientId;
        }

        public String getModel() {
            return this.model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getDevice() {
            return this.device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public String getDeviceId() {
            return this.deviceId;
        }

        public LoginApiByPhoneNum.LoginRequestParams setDeviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public String getSystemVersion() {
            return this.systemVersion;
        }

        public void setSystemVersion(String systemVersion) {
            this.systemVersion = systemVersion;
        }




        public String getPlatform() {
            return this.platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }
    }


}
