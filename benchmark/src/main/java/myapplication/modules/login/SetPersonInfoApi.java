package myapplication.modules.login;

import myapplication.base.BaseApi;

public class SetPersonInfoApi extends BaseApi {

    public static class SetPersonInfoParam{

        private String token;
        private String method = "api/user/setPersonInfo";
        private String nickname ;
        private String surname ;
        private String mobile;
        private String avatar = "http://oss-cn-hongkong.aliyuncs.com/userAvatar/avatar/6.png";

        public String getToken() {
            return token;
        }

        public SetPersonInfoParam setToken(String token) {
            this.token = token;
            return this;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getNickname() {
            return nickname;
        }

        public SetPersonInfoParam setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public String getSurname() {
            return surname;
        }

        public SetPersonInfoParam setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public String getMobile() {
            return mobile;
        }

        public SetPersonInfoParam setMobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
