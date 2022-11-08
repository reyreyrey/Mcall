package myapplication.modules.login;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class LoginBean extends LitePalSupport implements Serializable{

    private String clientid;
    private String deviceid;

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    private String birthday;

    private Integer voice_remind;

    private Integer gender;

    private String desKey;

    private Integer last_login_time;

    private Integer city;

    private String bio;

    private Integer group_voice_remind;

    private Integer score;

    private Integer msg_remind;

    private Integer send_msg;

    private String nickname;

    private Integer id;

    private Integer group_msg_remind;

    private Integer expires_in;

    private Integer expiretime;

    private Integer createtime;

    private String mobile;

    private String avatar;

    private String note_surname;

    private Cloudchat cloudchat;

    private Integer bgimage_id;

    private String token;

    private Integer user_id;

    private String loginip;

    private String username;

    private Integer register;

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getVoice_remind() {
        return this.voice_remind;
    }

    public void setVoice_remind(Integer voice_remind) {
        this.voice_remind = voice_remind;
    }

    public Integer getGender() {
        return this.gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getDesKey() {
        return this.desKey;
    }

    public void setDesKey(String desKey) {
        this.desKey = desKey;
    }

    public Integer getLast_login_time() {
        return this.last_login_time;
    }

    public void setLast_login_time(Integer last_login_time) {
        this.last_login_time = last_login_time;
    }

    public boolean isfeng(){
        return user_id == -1;
    }

    public Integer getCity() {
        return this.city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String getBio() {
        return this.bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Integer getGroup_voice_remind() {
        return this.group_voice_remind;
    }

    public void setGroup_voice_remind(Integer group_voice_remind) {
        this.group_voice_remind = group_voice_remind;
    }

    public Integer getScore() {
        return this.score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getMsg_remind() {
        return this.msg_remind;
    }

    public void setMsg_remind(Integer msg_remind) {
        this.msg_remind = msg_remind;
    }

    public Integer getSend_msg() {
        return this.send_msg;
    }

    public void setSend_msg(Integer send_msg) {
        this.send_msg = send_msg;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroup_msg_remind() {
        return this.group_msg_remind;
    }

    public void setGroup_msg_remind(Integer group_msg_remind) {
        this.group_msg_remind = group_msg_remind;
    }

    public Integer getExpires_in() {
        return this.expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    public Integer getExpiretime() {
        return this.expiretime;
    }

    public void setExpiretime(Integer expiretime) {
        this.expiretime = expiretime;
    }

    public Integer getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Integer createtime) {
        this.createtime = createtime;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNote_surname() {
        return this.note_surname;
    }

    public void setNote_surname(String note_surname) {
        this.note_surname = note_surname;
    }

    public Cloudchat getCloudchat() {
        return this.cloudchat;
    }

    public void setCloudchat(Cloudchat cloudchat) {
        this.cloudchat = cloudchat;
    }

    public Integer getBgimage_id() {
        return this.bgimage_id;
    }

    public void setBgimage_id(Integer bgimage_id) {
        this.bgimage_id = bgimage_id;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUser_id() {
        return this.user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getLoginip() {
        return this.loginip;
    }

    public void setLoginip(String loginip) {
        this.loginip = loginip;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRegister() {
        return this.register;
    }

    public void setRegister(Integer register) {
        this.register = register;
    }

    public static class Cloudchat implements Serializable {
        private String msg;

        private Data data;

        private Object ok;

        private Integer userId;

        private Integer status;

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Data getData() {
            return this.data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public Object getOk() {
            return this.ok;
        }

        public void setOk(Object ok) {
            this.ok = ok;
        }

        public Integer getUserId() {
            return this.userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getStatus() {
            return this.status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public static class Data implements Serializable {
            private String rtc_token;

            private String im_token;

            public String getRtc_token() {
                return this.rtc_token;
            }

            public void setRtc_token(String rtc_token) {
                this.rtc_token = rtc_token;
            }

            public String getIm_token() {
                return this.im_token;
            }

            public void setIm_token(String im_token) {
                this.im_token = im_token;
            }

            @Override
            public String toString() {
                return "Data{" +
                        "rtc_token='" + rtc_token + '\'' +
                        ", im_token='" + im_token + '\'' +
                        '}';
            }
        }
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "birthday='" + birthday + '\'' +
                ", voice_remind=" + voice_remind +
                ", gender=" + gender +
                ", desKey='" + desKey + '\'' +
                ", last_login_time=" + last_login_time +
                ", city=" + city +
                ", bio='" + bio + '\'' +
                ", group_voice_remind=" + group_voice_remind +
                ", score=" + score +
                ", msg_remind=" + msg_remind +
                ", send_msg=" + send_msg +
                ", nickname='" + nickname + '\'' +
                ", id=" + id +
                ", group_msg_remind=" + group_msg_remind +
                ", expires_in=" + expires_in +
                ", expiretime=" + expiretime +
                ", createtime=" + createtime +
                ", mobile='" + mobile + '\'' +
                ", avatar='" + avatar + '\'' +
                ", note_surname='" + note_surname + '\'' +
                ", cloudchat=" + cloudchat +
                ", bgimage_id=" + bgimage_id +
                ", token='" + token + '\'' +
                ", user_id=" + user_id +
                ", loginip='" + loginip + '\'' +
                ", username='" + username + '\'' +
                ", register=" + register +
                '}';
    }

    @Override
    public boolean equals(Object arg0) {
        LoginBean p = (LoginBean) arg0;
        return user_id.equals(p.user_id);
    }

    @Override
    public int hashCode() {
        String str = user_id + nickname;
        return str.hashCode();
    }

}
