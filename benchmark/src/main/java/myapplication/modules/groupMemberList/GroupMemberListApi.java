package myapplication.modules.groupMemberList;

import myapplication.base.BaseApi;

public class GroupMemberListApi extends BaseApi {

    public static class GroupMemberListRequestParam {
        private String size = "500";
        private String method = "api/group_member/lists";
        private String group_id;
        private String member_type = "0";

        public String getToken() {
            return token;
        }

        public GroupMemberListRequestParam setToken(String token) {
            this.token = token;
            return this;
        }

        private String token;

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
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

        public GroupMemberListRequestParam setGroup_id(String group_id) {
            this.group_id = group_id;
            return this;
        }

        public String getMember_type() {
            return member_type;
        }

        public void setMember_type(String member_type) {
            this.member_type = member_type;
        }
    }
}
