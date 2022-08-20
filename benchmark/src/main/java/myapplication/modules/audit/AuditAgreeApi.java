package myapplication.modules.audit;

import myapplication.base.BaseApi;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.20 13:01
 */
public class AuditAgreeApi extends BaseApi {

    public static class AuditAgreeParam{
        private String method = "api/friend_manage/updateAudit";
        private String audit = "2";
        private String otherid;
        private String token;

        public String getToken() {
            return token;
        }

        public AuditAgreeParam setToken(String token) {
            this.token = token;
            return this;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getAudit() {
            return audit;
        }

        public void setAudit(String audit) {
            this.audit = audit;
        }

        public String getOtherid() {
            return otherid;
        }

        public AuditAgreeParam setOtherid(String otherid) {
            this.otherid = otherid;
            return this;
        }
    }
}
