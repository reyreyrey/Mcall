package myapplication.modules.config;


import myapplication.base.BaseApi;

public class ConfigApi extends BaseApi {

    public static class ConfigRequestParams{
        private String method = "api/common/zhopush";

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        private String token;


        public String getToken() {
            return token;
        }

        public ConfigRequestParams setToken(String token) {
            this.token = token;
            return this;
        }
    }

}
