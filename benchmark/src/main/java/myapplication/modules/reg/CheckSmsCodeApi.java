package myapplication.modules.reg;

import myapplication.base.BaseApi;

public class CheckSmsCodeApi extends BaseApi {

    public static class CheckSmsCodeParam {
        private String method = "api/sms/check";
        private String mobile ;
        private String event = "mobilelogin";
        private String captcha;

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getMobile() {
            return mobile;
        }

        public CheckSmsCodeParam setMobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public String getEvent() {
            return event;
        }

        public void setEvent(String event) {
            this.event = event;
        }

        public String getCaptcha() {
            return captcha;
        }

        public CheckSmsCodeParam setCaptcha(String captcha) {
            this.captcha = captcha;
            return this;
        }
    }
}
