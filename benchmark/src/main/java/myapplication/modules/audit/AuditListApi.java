package myapplication.modules.audit;

import myapplication.base.BaseApi;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.20 12:59
 */
public class AuditListApi extends BaseApi {

    public static class AuditListParam {
        private String token;
        private String method="api/friend_manage/auditList";

        public String getToken() {
            return token;
        }

        public AuditListParam setToken(String token) {
            this.token = token;
            return this;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }
    }
}
