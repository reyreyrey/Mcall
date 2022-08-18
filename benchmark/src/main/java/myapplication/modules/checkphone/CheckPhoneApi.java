package myapplication.modules.checkphone;

import myapplication.base.BaseApi;

public class CheckPhoneApi extends BaseApi {

    public static class CheckPhoneParams{
        private String field;
        private String method = "api/user/selectMobile";

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getField() {
            return field;
        }

        public CheckPhoneParams setField(String field) {
            this.field = field;
            return this;
        }
    }
}
