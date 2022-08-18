package myapplication.modules.searchUser;

import myapplication.base.BaseApi;

public class SearchUserApi extends BaseApi {

    public static class SearchUserParam {
        private String method = "api/user_info/search";
        private String field ;
        private String type = "2";
        private String token;

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getField() {
            return field;
        }

        public SearchUserParam setField(String field) {
            this.field = field;
            return this;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getToken() {
            return token;
        }

        public SearchUserParam setToken(String token) {
            this.token = token;
            return this;
        }
    }
}
