package myapplication.modules.login;

import myapplication.base.BaseApi;

public class SetNicknameApi extends BaseApi {

    public static class SetNicknameParam{
        private String method = "api/user/profile";
        private String username;
        private String gender = "3";
        private String nickname;
        private String birthday = "";
        private String city = "86";
        private String note_surname = "";
        private String token;

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getNickname() {
            return nickname;
        }

        public SetNicknameParam setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getNote_surname() {
            return note_surname;
        }

        public void setNote_surname(String note_surname) {
            this.note_surname = note_surname;
        }

        public String getToken() {
            return token;
        }

        public SetNicknameParam setToken(String token) {
            this.token = token;
            return this;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getUsername() {
            return username;
        }

        public SetNicknameParam setUsername(String username) {
            this.username = username;
            return this;
        }
    }
}
