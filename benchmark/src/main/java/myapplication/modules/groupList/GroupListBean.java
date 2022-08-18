package myapplication.modules.groupList;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;

public class GroupListBean implements Serializable {
  private Integer master_id;

  private Integer avatar_status;

  private String mem_limit;

  private Integer user_msg_disturb;

  private String group_name;

  private Integer num;

  private Integer user_level;

  private Integer last_shake_time;

  private Integer id;

  private String avatar;

  private Integer user_save_group;

  private String notice;

  public Integer getMaster_id() {
    return this.master_id;
  }

  public void setMaster_id(Integer master_id) {
    this.master_id = master_id;
  }

  public Integer getAvatar_status() {
    return this.avatar_status;
  }

  public void setAvatar_status(Integer avatar_status) {
    this.avatar_status = avatar_status;
  }

  public String getMem_limit() {
    return this.mem_limit;
  }

  public void setMem_limit(String mem_limit) {
    this.mem_limit = mem_limit;
  }

  public Integer getUser_msg_disturb() {
    return this.user_msg_disturb;
  }

  public void setUser_msg_disturb(Integer user_msg_disturb) {
    this.user_msg_disturb = user_msg_disturb;
  }

  public String getGroup_name() {
    return this.group_name;
  }

  public void setGroup_name(String group_name) {
    this.group_name = group_name;
  }

  public Integer getNum() {
    return this.num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

  public Integer getUser_level() {
    return this.user_level;
  }

  public void setUser_level(Integer user_level) {
    this.user_level = user_level;
  }

  public Integer getLast_shake_time() {
    return this.last_shake_time;
  }

  public void setLast_shake_time(Integer last_shake_time) {
    this.last_shake_time = last_shake_time;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getAvatar() {
    return this.avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public Integer getUser_save_group() {
    return this.user_save_group;
  }

  public void setUser_save_group(Integer user_save_group) {
    this.user_save_group = user_save_group;
  }

  public String getNotice() {
    return this.notice;
  }

  public void setNotice(String notice) {
    this.notice = notice;
  }


  @Override
  public String toString() {
    return "GroupListBean{" +
            "master_id=" + master_id +
            ", avatar_status=" + avatar_status +
            ", mem_limit='" + mem_limit + '\'' +
            ", user_msg_disturb=" + user_msg_disturb +
            ", group_name='" + group_name + '\'' +
            ", num=" + num +
            ", user_level=" + user_level +
            ", last_shake_time=" + last_shake_time +
            ", id=" + id +
            ", avatar='" + avatar + '\'' +
            ", user_save_group=" + user_save_group +
            ", notice='" + notice + '\'' +
            '}';
  }
}
