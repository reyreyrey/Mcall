package myapplication.bean;

import android.text.TextUtils;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.16 14:34
 */
public class ConfigBean extends LitePalSupport {

    private int configid;

    private int regCount;

    private String mainGroupId;

    private String mainGroupAccount;

    private String mainGroupPwd;

    private String groupAccount;

    private String groupPwd;

    private int groupJoniTime;

    private String ipProxyUrl;

    private String nickNameKeyWords;


    public String getMainGroupAccount() {
        return mainGroupAccount;
    }

    public void setMainGroupAccount(String mainGroupAccount) {
        this.mainGroupAccount = mainGroupAccount;
    }

    public String getMainGroupPwd() {
        return mainGroupPwd;
    }

    public void setMainGroupPwd(String mainGroupPwd) {
        this.mainGroupPwd = mainGroupPwd;
    }

    @Override
    public String toString() {
        return "ConfigBean{" +
                "configid=" + configid +
                ", regCount=" + regCount +
                ", groupAccount='" + groupAccount + '\'' +
                ", groupPwd='" + groupPwd + '\'' +
                ", groupJoniTime=" + groupJoniTime +
                ", ipProxyUrl='" + ipProxyUrl + '\'' +
                ", nickNameKeyWords='" + nickNameKeyWords + '\'' +
                '}';
    }

    public String getMainGroupId() {
        return mainGroupId;
    }

    public void setMainGroupId(String mainGroupId) {
        this.mainGroupId = mainGroupId;
    }

    public int getConfigid() {
        return configid;
    }

    public void setConfigid(int configid) {
        this.configid = configid;
    }

    public String getNickNameKeyWords() {
        return nickNameKeyWords;
    }

    public void setNickNameKeyWords(String nickNameKeyWords) {
        this.nickNameKeyWords = nickNameKeyWords;
    }

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
