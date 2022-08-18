package myapplication.modules.groupList;

import myapplication.base.BaseApi;

public class GroupListApi extends BaseApi {

    public static class GroupRequestPram {

        private String token;
        private String method = "api/groups/lists";

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getToken() {
            return token;
        }

        public GroupRequestPram setToken(String token) {
            this.token = token;
            return this;
        }
    }
}
