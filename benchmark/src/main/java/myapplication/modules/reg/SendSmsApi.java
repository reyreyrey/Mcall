package myapplication.modules.reg;

import myapplication.base.BaseApi;

public class SendSmsApi extends BaseApi {

    public static class RegParams {
        private String mobile;
        private String event = "mobilelogin";
        private String method = "api/sms/send";


        public String getMobile() {
            return mobile;
        }

        public RegParams setMobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public String getEvent() {
            return event;
        }

        public void setEvent(String event) {
            this.event = event;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }


    }
}
