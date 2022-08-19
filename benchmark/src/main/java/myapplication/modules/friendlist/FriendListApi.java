package myapplication.modules.friendlist;

import myapplication.base.BaseApi;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.19 18:51
 */
public class FriendListApi extends BaseApi {

    public static class FriendListParams{
        private String method = "api/friend_manage/friendList";
        private String token;

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getToken() {
            return token;
        }

        public FriendListParams setToken(String token) {
            this.token = token;
            return this;
        }
    }
}
