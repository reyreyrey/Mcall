package myapplication.modules.delete;

import myapplication.base.BaseApi;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.19 18:51
 */
public class DeleteFriendApi extends BaseApi {

    public static class DeleteFriendParams{
        private String method = "api/friend_manage/deleteFriend";
        private String otherid;
        private String token;

        public String getOtherid() {
            return otherid;
        }

        public DeleteFriendParams setOtherid(String otherid) {
            this.otherid = otherid;
            return this;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getToken() {
            return token;
        }

        public DeleteFriendParams setToken(String token) {
            this.token = token;
            return this;
        }
    }
}
