package myapplication.modules.inviteJoinGroup;

import myapplication.base.BaseApi;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.19 19:13
 */
public class InviteJoinGroupApi extends BaseApi {

    public static class inviteJoinGroupParams{
        private String uids;
        private String method = "api/group_member/join";
        private String group_id;
        private String token;

        public String getToken() {
            return token;
        }

        public inviteJoinGroupParams setToken(String token) {
            this.token = token;
            return this;
        }

        public String getUids() {
            return uids;
        }

        public inviteJoinGroupParams setUids(String uids) {
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

        public inviteJoinGroupParams setGroup_id(String group_id) {
            this.group_id = group_id;
            return this;
        }
    }
}
