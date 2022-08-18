package myapplication.modules.login;

import myapplication.base.BaseApi;

public class SetUsernameApi extends BaseApi {

    public static class SetUsernameParam{
        private String method = "api/user/profile";
        private String username;
        private String token;

        public String getToken() {
            return token;
        }

        public SetUsernameParam setToken(String token) {
            this.token = token;
            return this;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getUsername() {
            return username;
        }

        public SetUsernameParam setUsername(String username) {
            this.username = username;
            return this;
        }
    }
}
