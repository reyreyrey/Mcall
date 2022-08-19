package myapplication.bean;

import android.text.TextUtils;

import org.litepal.crud.LitePalSupport;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.16 14:34
 */
public class ConfigBean extends LitePalSupport {

    private int regCount;

    private String groupAccount;

    private String groupPwd;

    private int groupJoniTime;

    private String ipProxyUrl;

    public boolean needConfig(){
        return TextUtils.isEmpty(groupAccount) ||
                TextUtils.isEmpty(groupPwd) ||
                groupJoniTime == 0 ||
                TextUtils.isEmpty(ipProxyUrl) ;
    }

    public String getIpProxyUrl() {
        return ipProxyUrl;
    }

    public void setIpProxyUrl(String ipProxyUrl) {
        this.ipProxyUrl = ipProxyUrl;
    }

    public int getRegCount() {
        return regCount;
    }

    public void setRegCount(int regCount) {
        this.regCount = regCount;
    }

    public String getGroupAccount() {
        return groupAccount;
    }

    public void setGroupAccount(String groupAccount) {
        this.groupAccount = groupAccount;
    }

    public String getGroupPwd() {
        return groupPwd;
    }

    public void setGroupPwd(String groupPwd) {
        this.groupPwd = groupPwd;
    }

    public int getGroupJoniTime() {
        return groupJoniTime;
    }

    public void setGroupJoniTime(int groupJoniTime) {
        this.groupJoniTime = groupJoniTime;
    }
}
