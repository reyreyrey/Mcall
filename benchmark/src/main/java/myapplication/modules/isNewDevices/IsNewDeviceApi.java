package myapplication.modules.isNewDevices;

import myapplication.base.BaseApi;
import myapplication.base.Cons;
import myapplication.bean.ConfigBean;
import myapplication.utils.Config;
import myapplication.utils.MD5Utils;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.20 11:50
 */
public class IsNewDeviceApi extends BaseApi {


    public static class IsNewDeviceParam{
        private String username;
        private String password ;
        private String method = "api/userInfo/getIsNewDevice";
        private String deviceId = Cons.main_deviceId;

        public String getUsername() {
            return username;
        }

        public IsNewDeviceParam setUsername(String username) {
            this.username = username;
            return this;
        }

        public String getPassword() {
            return password;
        }

        public IsNewDeviceParam setPassword(String password) {
            this.password = password;
            return this;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }
    }
}
