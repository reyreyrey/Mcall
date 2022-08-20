package myapplication.modules.login;

import static myapplication.base.Cons.project_id;

import android.app.Service;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hjq.http.EasyHttp;
import com.hjq.http.model.ResponseClass;
import com.lzy.okgo.OkGo;

import org.json.JSONObject;

import java.util.List;

import myapplication.base.Cons;
import myapplication.base.HttpData;
import myapplication.base.ResultException;
import myapplication.bean.ConfigBean;
import myapplication.modules.addFriend.AddFriendApi;
import myapplication.modules.audit.AuditAgreeApi;
import myapplication.modules.audit.AuditListApi;
import myapplication.modules.audit.AuditListBean;
import myapplication.modules.checkphone.CheckPhoneApi;
import myapplication.modules.checkphone.CheckPhoneBean;
import myapplication.modules.friendlist.FriendListApi;
import myapplication.modules.friendlist.FriendListBean;
import myapplication.modules.groupAddManage.GroupAddManageApi;
import myapplication.modules.groupJoin.GroupJoniAPi;
import myapplication.modules.groupList.GroupListApi;
import myapplication.modules.groupList.GroupListBean;
import myapplication.modules.groupMemberList.GroupMemberListApi;
import myapplication.modules.groupMemberList.GroupMemberListBean;
import myapplication.modules.groupRemove.GroupRemoveAPi;
import myapplication.modules.inviteJoinGroup.InviteJoinGroupApi;
import myapplication.modules.isNewDevices.IsNewDeviceApi;
import myapplication.modules.isNewDevices.IsNewDeviceBean;
import myapplication.modules.proxy.IPProxy;
import myapplication.modules.reg.CheckSmsCodeApi;
import myapplication.modules.reg.SendSmsApi;
import myapplication.modules.reg.SendSmsBean;
import myapplication.modules.searchUser.SearchUserApi;
import myapplication.modules.searchUser.SearchUserBean;
import myapplication.modules.sms.getPhoneNum.GetPhoneNumApi;
import myapplication.modules.sms.getPhoneNum.GetPhoneNumBean;
import myapplication.modules.sms.getSms.GetSmsApi;
import myapplication.modules.sms.smsLogin.SmsLoginApi;
import myapplication.modules.sms.smsLogin.SmsLoginBean;
import myapplication.ui.MainActivityNew;
import myapplication.utils.Config;
import myapplication.utils.MD5Utils;
import okhttp3.Response;

public class LoginRequest {
    private Gson gson;
    private SmsLoginBean smsLoginBean;
    private FragmentActivity context;
    public LoginRequest(FragmentActivity context) {
        this.context = context;
        gson = new GsonBuilder().create();
    }

    public boolean setPersonInfo(String token, String mobile, String nickname, String susername){

        try {
            HttpData<String> data = EasyHttp
                    .post(context)
                    .api(new SetPersonInfoApi()
                            .setParam(gson.toJson(new SetPersonInfoApi.SetPersonInfoParam()
                                    .setToken(token)
                                    .setMobile(mobile)
                                    .setNickname(nickname)
                                    .setSurname(susername)
                            )))
                    .execute(new ResponseClass<HttpData<String>>() {
                    });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean checkSmsCode(String phoneNum, String code){
        try {
            HttpData<String> data = EasyHttp
                    .post(context)
                    .api(new CheckSmsCodeApi()
                            .setParam(gson.toJson(new CheckSmsCodeApi.CheckSmsCodeParam().setMobile(phoneNum).setCaptcha(code))))
                    .execute(new ResponseClass<HttpData<String>>() {
                    });
            return data.getCode() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public LoginBean loginByPhoneNum(String mobile, String code, String clientid, String deviceid){
        try {
            return EasyHttp
                    .post(context)
                    .api(new LoginApi()
                            .setParam(gson.toJson(new LoginApiByPhoneNum.LoginRequestParams()
                                    .setMobile(mobile)
                                    .setCaptcha(code)
                                    .setClientId(clientid)
                                    .setDeviceId(deviceid)
                            )))
                    .execute(new ResponseClass<HttpData<LoginBean>>() {
                    }).getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getSms(String phoneNum) {
        phoneNum = phoneNum.replaceAll("86-", "");
        String code = "";
        try {
            Response response = OkGo.get(Cons.SMS_URl+"api/get_message")
                    .params("token", smsLoginBean.getToken())
                    .params("project_id", project_id)
                    .params("phone_num", phoneNum)
                    .params("r", Math.random()*10000)
                    .execute();
            Log.e("----->", response == null ? "结果为空---":"00000000000");
            if(response == null)return "";
            String json = response.body().string();
            JSONObject jsonObject = new JSONObject(json);
            code = jsonObject.getString("code");
        } catch (Exception e) {
            Log.e("----->", e.toString());

        }
        return code;
    }

    public SmsLoginBean smsLogin() {
        if (smsLoginBean != null) {
            return smsLoginBean;
        }
        try {
            Response response = OkGo.get(Cons.SMS_URl+"api/logins")
                    .params("username", Cons.sms_username)
                    .params("password", Cons.sms_password)
                    .execute();
            if(response == null)return null;
             smsLoginBean = new GsonBuilder().create().fromJson(response.body().string(), SmsLoginBean.class);

             return smsLoginBean;
        } catch (Exception e) {
            return null;
        }
    }

    public String getPhoneNum(SmsLoginBean loginBean) {
        String json = null;
        try {
            Response response = OkGo.get(Cons.SMS_URl+"api/get_mobile")
                    .params("token", loginBean.getToken())
                    .params("project_id", project_id)
                    .params("operator", "4")
                    .params("scope_black", "1700,1701,1702,162,1703,1705,1706,165,1704,1707,1708,1709,171,167,1349,174,140,141,144,146,148,195")
                    .execute();
            if(response == null)return null;
             json = response.body().string();
        } catch (Exception e) {
            return null;
        }
        String sub = json.substring(json.indexOf("1分钟内剩余取卡数:\":\"") + 13, json.indexOf("\",\"上线时间"));
        int less = Integer.parseInt(sub);
        if (less < 11) {
            return "";
        }
        GetPhoneNumBean bean = new GsonBuilder().create().fromJson(json, GetPhoneNumBean.class);
        String mobile = bean.getMessage().equalsIgnoreCase("ok") ? bean.getMobile() : "";
        Log.e("----->", mobile);
        return mobile;
    }

    public boolean setPwd(String token){
        try {
            HttpData<String> data = EasyHttp
                    .post(context)
                    .api(new SetPwdApi()
                            .setParam(gson.toJson(new SetPwdApi.SetPwdParam()
                                    .setToken(token)
                            )))
                    .execute(new ResponseClass<HttpData<String>>() {
                    });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean setUserName(String token, String username){

        try {
            HttpData<String> data = EasyHttp
                    .post(context)
                    .api(new SetUsernameApi()
                            .setParam(gson.toJson(new SetUsernameApi.SetUsernameParam()
                                    .setToken(token)
                                    .setUsername(username)
                            )))
                    .execute(new ResponseClass<HttpData<String>>() {
                    });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sendSms(String phoneNum) {
        try {
            HttpData<SendSmsBean> data = EasyHttp
                    .post(context)
                    .api(new SendSmsApi()
                            .setParam(gson.toJson(new SendSmsApi.RegParams().setMobile(phoneNum))))
                    .execute(new ResponseClass<HttpData<SendSmsBean>>() {
                    });
            return data.getData().getCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param mainToken 需要获取群列表的用户token
     * @return
     * @throws Exception
     */
    public  List<GroupListBean> getGroupList(String mainToken)  {
        HttpData<List<GroupListBean>> listHttpData = null;
        try {
            listHttpData = EasyHttp
                    .post(context)
                    .api(new GroupListApi()
                            .setParam(gson.toJson(new GroupListApi.GroupRequestPram().setToken(mainToken))))
                    .execute(new ResponseClass<HttpData<List<GroupListBean>>>() {
                    });
            return listHttpData.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * @param mainToken 需要获取群成员的用户token
     * @param groupID   群id
     * @return
     * @throws Exception
     */
    public  List<GroupMemberListBean> getGroupMemberList(String mainToken, String groupID)  {
        HttpData<List<GroupMemberListBean>> l = null;
        try {
            l = EasyHttp
                    .post(context)
                    .api(new GroupMemberListApi()
                            .setParam(gson.toJson(new GroupMemberListApi.GroupMemberListRequestParam().setGroup_id(groupID).setToken(mainToken))))
                    .execute(new ResponseClass<HttpData<List<GroupMemberListBean>>>() {
                    });
            return l.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param token    需要添加好友的用户token
     * @param other_id 被添加的用户id
     * @throws Exception
     */
    public  boolean addFriend(String token, String other_id) {
        try {
            EasyHttp
                    .post(context)
                    .api(new AddFriendApi()
                            .setParam(gson.toJson(new AddFriendApi.AddFriendParam().setOtherid(other_id).setToken(token))))
                    .execute(new ResponseClass<HttpData<Object>>() {
                    });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 通过userid获取用户信息
     * @param id
     * @param token
     * @return
     */
    public SearchUserBean getUserByID(String id, String token){
        try {
            return EasyHttp
                    .post(context)
                    .api(new SearchUserApi()
                            .setParam(gson.toJson(new SearchUserApi.SearchUserParam()
                                    .setField(id)
                                    .setToken(token))))
                    .execute(new ResponseClass<HttpData<SearchUserBean>>() {
                    }).getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检测手机号是否存在
     * @param phoneNum
     * @return
     * @throws Exception
     */
    public boolean checkPhone(String phoneNum)  {
        HttpData<CheckPhoneBean> httpData = null;
        try {
            httpData = EasyHttp
                    .post(context)
                    .api(new CheckPhoneApi()
                            .setParam(gson.toJson(new CheckPhoneApi.CheckPhoneParams().setField(phoneNum))))
                    .execute(new ResponseClass<HttpData<CheckPhoneBean>>() {
                    });
            if(httpData.getCode() == 0){
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof ResultException){
                ResultException exception  = (ResultException) e;
                if(exception.getMessage().equalsIgnoreCase("账号不存在"))return false;
            }
        }

        return true;
    }

    public boolean groupJoin(String token,String uids, String groupId){
        try {
            EasyHttp
                    .post(context)
                    .api(new GroupJoniAPi()
                            .setParam(gson.toJson(
                                    new GroupJoniAPi.GroupJoniParam()
                                            .setToken(token)
                                            .setGroup_id(groupId)
                                            .setUids(uids)
                                            )))
                    .execute(new ResponseClass<HttpData<String>>() {
                    });
            return true;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }
    public LoginBean login(String account, String pwd, String deviceid, String clientid, String captcha){
        deviceid = deviceid == null ? Cons.deviceId : deviceid;
        clientid = clientid == null ? Cons.clientId : clientid;
        try {

            return EasyHttp
                    .post(context)
                    .api(new LoginApi()
                            .setParam(gson.toJson(new LoginApi.LoginRequestParams()
                                    .setAccount(account)
                                    .setPassword(pwd)
                                    .setClientId(clientid)
                                    .setDeviceId(deviceid)
                                    .setCaptcha(captcha)
                            )))
                    .execute(new ResponseClass<HttpData<LoginBean>>() {
                    }).getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * @param account 用户名
     * @param pwd     密码
     * @return
     * @throws Exception
     */
    public LoginBean login(String account, String pwd, String deviceid, String clientid)  {
        return login(account,pwd,deviceid,clientid,"");
    }

    /**
     * 获取好友列表
     * @param token
     * @return
     */
    public List<FriendListBean> getFriendLists(String token){
        try {

            return EasyHttp
                    .post(context)
                    .api(new FriendListApi()
                            .setParam(gson.toJson(new FriendListApi.FriendListParams()
                                    .setToken(token)
                            )))
                    .execute(new ResponseClass<HttpData<List<FriendListBean>>>() {
                    }).getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 邀请进群
     * @param token
     * @return
     */
    public boolean getFriendLists(String token, String ids, String groupId){
        try {

             EasyHttp
                    .post(context)
                    .api(new InviteJoinGroupApi()
                            .setParam(gson.toJson(new InviteJoinGroupApi.inviteJoinGroupParams()
                                    .setToken(token)
                                    .setGroup_id(groupId)
                                    .setUids(ids)
                            )))
                    .execute(new ResponseClass<HttpData<Object>>() {
                    }).getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public IsNewDeviceBean isNewDevice(String username, String password){

        try {

            return EasyHttp
                    .post(context)
                    .api(new IsNewDeviceApi()
                            .setParam(gson.toJson(new IsNewDeviceApi.IsNewDeviceParam()
                                    .setUsername(username)
                                    .setPassword(password)
                            )))
                    .execute(new ResponseClass<HttpData<IsNewDeviceBean>>() {
                    }).getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<AuditListBean> auditList(){
        try {

            return EasyHttp
                    .post(context)
                    .api(new AuditListApi()
                            .setParam(gson.toJson(new AuditListApi.AuditListParam()
                                    .setToken(MainActivityNew.groupOwerInfo.getToken())
                            )))
                    .execute(new ResponseClass<HttpData<List<AuditListBean>>>() {
                    }).getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean auditAgree(AuditListBean bean){
        try {

             EasyHttp
                    .post(context)
                    .api(new AuditAgreeApi()
                            .setParam(gson.toJson(new AuditAgreeApi.AuditAgreeParam()
                                    .setToken(MainActivityNew.groupOwerInfo.getToken())
                                    .setOtherid(bean.getUserid()+"")
                            )))
                    .execute(new ResponseClass<HttpData<Object>>() {
                    }).getData();
             return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addGroupManage(String id){
        try {

            EasyHttp
                    .post(context)
                    .api(new GroupAddManageApi()
                            .setParam(gson.toJson(new GroupAddManageApi.GroupAddManageParam()
                                    .setToken(MainActivityNew.groupOwerInfo.getToken())
                                    .setManager_uid(id+"")
                                    .setGroup_id(Config.getConfig().getMainGroupId())
                            )))
                    .execute(new ResponseClass<HttpData<Object>>() {
                    }).getData();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean groupRemove(String token){
        try {

            EasyHttp
                    .post(context)
                    .api(new GroupRemoveAPi()
                            .setParam(gson.toJson(new GroupRemoveAPi.GroupRemoveParam()
                                    .setToken(token)
                                    .setGroup_id(Config.getConfig().getMainGroupId())
                            )))
                    .execute(new ResponseClass<HttpData<Object>>() {
                    }).getData();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
