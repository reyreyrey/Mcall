package myapplication.modules.groupRemove;

import myapplication.base.BaseApi;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.20 13:46
 */
public class GroupRemoveAPi extends BaseApi {

    public static class GroupRemoveParam{
        private String method = "api/group_member/remove";
        private String token;
        private String group_id;

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getToken() {
            return token;
        }

        public GroupRemoveParam setToken(String token) {
            this.token = token;
            return this;
        }

        public String getGroup_id() {
            return group_id;
        }

        public GroupRemoveParam setGroup_id(String group_id) {
            this.group_id = group_id;
            return this;
        }
    }
}
