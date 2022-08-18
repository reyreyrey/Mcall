package myapplication.modules.addFriend;

import myapplication.base.BaseApi;

public class AddFriendApi extends BaseApi {

    public static class AddFriendParam{
        private String method = "api/friend_manage/addFriend";
        private String token;
        private String otherid;

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getToken() {
            return token;
        }

        public AddFriendParam setToken(String token) {
            this.token = token;
            return this;
        }

        public String getOtherid() {
            return otherid;
        }

        public AddFriendParam setOtherid(String otherid) {
            this.otherid = otherid;
            return this;
        }
    }
}
