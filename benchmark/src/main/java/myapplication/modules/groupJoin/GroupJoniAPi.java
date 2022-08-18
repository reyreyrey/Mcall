package myapplication.modules.groupJoin;

import myapplication.base.BaseApi;

public class GroupJoniAPi extends BaseApi {

    public static class GroupJoniParam{
        private String uids;
        private String method = "api/group_member/join";
        private String group_id;
        private String token;

        public String getToken() {
            return token;
        }

        public GroupJoniParam setToken(String token) {
            this.token = token;
            return this;
        }

        public String getUids() {
            return uids;
        }

        public GroupJoniParam setUids(String uids) {
            this.uids = uids;
            return this;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getGroup_id() {
            return group_id;
        }

        public GroupJoniParam setGroup_id(String group_id) {
            this.group_id = group_id;
            return this;
        }
    }
}
