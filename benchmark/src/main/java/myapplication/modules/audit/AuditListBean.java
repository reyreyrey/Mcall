package myapplication.modules.audit;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.Object;
import java.lang.String;

public class AuditListBean implements Serializable {
  private Object note;

  private Integer otherid;

  private Integer gender;

  private Integer last_login_time;

  private String mobile;

  private String avatar;

  private Integer is_default;

  private Object note_surname;

  private Integer userid;

  private Object o_note_surname;

  private Integer audit;

  private String nickname;

  private String online;

  private String username;

  public Object getNote() {
    return this.note;
  }

  public void setNote(Object note) {
    this.note = note;
  }

  public Integer getOtherid() {
    return this.otherid;
  }

  public void setOtherid(Integer otherid) {
    this.otherid = otherid;
  }

  public Integer getGender() {
    return this.gender;
  }

  public void setGender(Integer gender) {
    this.gender = gender;
  }

  public Integer getLast_login_time() {
    return this.last_login_time;
  }

  public void setLast_login_time(Integer last_login_time) {
    this.last_login_time = last_login_time;
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

  public Integer getIs_default() {
    return this.is_default;
  }

  public void setIs_default(Integer is_default) {
    this.is_default = is_default;
  }

  public Object getNote_surname() {
    return this.note_surname;
  }

  public void setNote_surname(Object note_surname) {
    this.note_surname = note_surname;
  }

  public Integer getUserid() {
    return this.userid;
  }

  public void setUserid(Integer userid) {
    this.userid = userid;
  }

  public Object getO_note_surname() {
    return this.o_note_surname;
  }

  public void setO_note_surname(Object o_note_surname) {
    this.o_note_surname = o_note_surname;
  }

  public Integer getAudit() {
    return this.audit;
  }

  public void setAudit(Integer audit) {
    this.audit = audit;
  }

  public String getNickname() {
    return this.nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getOnline() {
    return this.online;
  }

  public void setOnline(String online) {
    this.online = online;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
