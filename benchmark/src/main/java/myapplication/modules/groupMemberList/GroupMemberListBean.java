package myapplication.modules.groupMemberList;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class GroupMemberListBean extends LitePalSupport implements Serializable {


    private Integer msg;

    private Object f_surname;

    private Integer last_login_time;

    private Integer level;

    private String avatar;

    private String nick;

    private Integer online_state;

    private Integer top;

    private String phone;

    private Integer gag_status;

    private String surname;

    private Object gag_time;

    private String nickname;

    private Object f_nickname;

    private String online;

    private Integer id;

    private String nick_surname;

    private Integer status;

    public Integer getMsg() {
        return this.msg;
    }

    public void setMsg(Integer msg) {
        this.msg = msg;
    }

    public Object getF_surname() {
        return this.f_surname;
    }

    public void setF_surname(Object f_surname) {
        this.f_surname = f_surname;
    }

    public Integer getLast_login_time() {
        return this.last_login_time;
    }

    public void setLast_login_time(Integer last_login_time) {
        this.last_login_time = last_login_time;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getOnline_state() {
        return this.online_state;
    }

    public void setOnline_state(Integer online_state) {
        this.online_state = online_state;
    }

    public Integer getTop() {
        return this.top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getGag_status() {
        return this.gag_status;
    }

    public void setGag_status(Integer gag_status) {
        this.gag_status = gag_status;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Object getGag_time() {
        return this.gag_time;
    }

    public void setGag_time(Object gag_time) {
        this.gag_time = gag_time;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Object getF_nickname() {
        return this.f_nickname;
    }

    public void setF_nickname(Object f_nickname) {
        this.f_nickname = f_nickname;
    }

    public String getOnline() {
        return this.online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNick_surname() {
        return this.nick_surname;
    }

    public void setNick_surname(String nick_surname) {
        this.nick_surname = nick_surname;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "GroupMemberListBean{" +
                "msg=" + msg +
                ", f_surname=" + f_surname +
                ", last_login_time=" + last_login_time +
                ", level=" + level +
                ", avatar='" + avatar + '\'' +
                ", nick='" + nick + '\'' +
                ", online_state=" + online_state +
                ", top=" + top +
                ", phone='" + phone + '\'' +
                ", gag_status=" + gag_status +
                ", surname='" + surname + '\'' +
                ", gag_time=" + gag_time +
                ", nickname='" + nickname + '\'' +
                ", f_nickname=" + f_nickname +
                ", online='" + online + '\'' +
                ", id=" + id +
                ", nick_surname='" + nick_surname + '\'' +
                ", status=" + status +
                '}';
    }
}

