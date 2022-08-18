package myapplication.modules.login;


import myapplication.base.BaseApi;
import myapplication.utils.MD5Utils;

public final class LoginApi extends BaseApi {


    public static class LoginRequestParams  {

        private String method = "api/userInfo/login";

        private String password = "6ac04d927d383b939d9ee801cd22644f";

        private String appVersion = "1.2.6";

        private String clientId = "6DAAA90A-BDF9-4605-B22A-7DF11B1FCEE9";

        private String iosPushClientId = "543a23d75879b1036baa08d8b9e400cf3b22803146ef0c59333a020dd94df1dd";

        private String model = "iPhone12";

        private String device = "2";

        private String deviceId = "3D12B822-1D0F-4C78-B61A-984B58D37C22";

        private String systemVersion = "15.5";

        private String account = "asd123hhh";

        private String platform = "1";

        public String getPassword() {
            return this.password;
        }

        public LoginRequestParams setPassword(String password) {
            this.password = MD5Utils.encode(password);
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

        public LoginApi.LoginRequestParams setClientId(String clientId) {
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

        public LoginApi.LoginRequestParams setDeviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public String getSystemVersion() {
            return this.systemVersion;
        }

        public void setSystemVersion(String systemVersion) {
            this.systemVersion = systemVersion;
        }

        public String getAccount() {
            return this.account;
        }

        public LoginRequestParams setAccount(String account) {
            this.account = account;
            return this;
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
