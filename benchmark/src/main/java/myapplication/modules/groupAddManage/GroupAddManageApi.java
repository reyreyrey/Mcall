package myapplication.modules.groupAddManage;

import myapplication.base.BaseApi;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.20 13:28
 */
public class GroupAddManageApi extends BaseApi {

    public static class GroupAddManageParam{
        private String manager_uid;
        private String method = "api/group_manager/add";
        private String group_id;
        private String token;

        public String getToken() {
            return token;
        }

        public GroupAddManageParam setToken(String token) {
            this.token = token;
            return this;
        }

        public String getManager_uid() {
            return manager_uid;
        }

        public GroupAddManageParam setManager_uid(String manager_uid) {
            this.manager_uid = manager_uid;
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

        public GroupAddManageParam setGroup_id(String group_id) {
            this.group_id = group_id;
            return this;
        }
    }
}
