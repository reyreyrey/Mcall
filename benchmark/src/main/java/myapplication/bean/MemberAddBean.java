package myapplication.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

import myapplication.modules.groupMemberList.GroupMemberListBean;
import myapplication.modules.login.LoginBean;

public class MemberAddBean extends LitePalSupport implements Serializable {

    private Integer mineUserID;

    private Integer needAddUserID;
    private String needAddUserNick;

    public String getNeedAddUserNick() {
        return needAddUserNick;
    }

    public void setNeedAddUserNick(String needAddUserNick) {
        this.needAddUserNick = needAddUserNick;
    }

    //是否已经同意
    private boolean isAgree;
    //是否已经添加
    private boolean isAdd;

    public boolean isAgree() {
        return isAgree;
    }

    public void setAgree(boolean agree) {
        isAgree = agree;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public Integer getMineUserID() {
        return mineUserID;
    }

    public void setMineUserID(Integer mineUserID) {
        this.mineUserID = mineUserID;
    }

    public Integer getNeedAddUserID() {
        return needAddUserID;
    }

    public void setNeedAddUserID(Integer needAddUserID) {
        this.needAddUserID = needAddUserID;
    }

    @Override
    public String toString() {
        return "MemberAddBean{" +
                "mineUserID=" + mineUserID +
                ", needAddUserID=" + needAddUserID +
                ", needAddUserNick='" + needAddUserNick + '\'' +
                ", isAgree=" + isAgree +
                ", isAdd=" + isAdd +
                '}';
    }
}
