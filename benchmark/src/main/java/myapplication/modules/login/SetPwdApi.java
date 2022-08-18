package myapplication.modules.login;

import myapplication.base.BaseApi;

public class SetPwdApi extends BaseApi {

    //{"confirmPwd":"666888aa..","method":"api\/user\/setPwd","password":"666888aa.."}
    public static class SetPwdParam {
        private String method = "api/user/setPwd";
        private String confirmPwd = "666888aa..";
        private String password = "666888aa..";
        private String token;

        public String getToken() {
            return token;
        }

        public SetPwdParam setToken(String token) {
            this.token = token;
            return this;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getConfirmPwd() {
            return confirmPwd;
        }

        public void setConfirmPwd(String confirmPwd) {
            this.confirmPwd = confirmPwd;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
