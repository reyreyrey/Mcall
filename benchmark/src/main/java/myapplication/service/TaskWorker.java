package myapplication.service;

import static java.lang.Thread.sleep;

import static myapplication.ui.MainActivityNew.mainActivityNew;

import android.content.Context;
import android.text.TextUtils;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import myapplication.MyApp;
import myapplication.base.Cons;
import myapplication.bean.ConfigBean;
import myapplication.modules.friendlist.FriendListBean;
import myapplication.modules.login.LoginBean;
import myapplication.modules.login.LoginRequest;
import myapplication.modules.proxy.IPProxy;
import myapplication.ui.MainActivityNew;
import myapplication.utils.Config;
import tgio.rncryptor.RNCryptorNative;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.19 17:59
 */
public class TaskWorker extends TimerTask {
    int taskRunTimeS;
    private RNCryptorNative rnCryptorNative;
    ConfigBean configBean;
    LoginRequest loginRequest;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public TaskWorker(int taskRunTimeS) {
        this.taskRunTimeS = taskRunTimeS;
        loginRequest = new LoginRequest(mainActivityNew);
        configBean = Config.getConfig();
        rnCryptorNative = new RNCryptorNative();
    }

    @Override
    public void run() {
        EventBus.getDefault().post("-----------------------------------------");
        EventBus.getDefault().post("开始执行");
        String mainGroupID = configBean.getMainGroupId();
        if (TextUtils.isEmpty(mainGroupID)) {
            EventBus.getDefault().post("请配置主群id");
            return;
        }

        EventBus.getDefault().post("开始获取所有的注册用户");
        List<LoginBean> regs = LitePal.findAll(LoginBean.class);
        EventBus.getDefault().post("注册用户一共有：" + regs.size() + "个");
        for (LoginBean loginBean : regs) {
            EventBus.getDefault().post("设置代理");
            IPProxy.setProxy(null);
            EventBus.getDefault().post("代理设置成功");
            EventBus.getDefault().post("开始登录" + loginBean.getUsername());
            LoginBean bean = loginRequest.login(loginBean.getUsername(), "666888aa..", loginBean.getDeviceid(), loginBean.getClientid());
            if (bean == null) {
                EventBus.getDefault().post(loginBean.getUsername() + "->登录失败"+loginRequest.getErrorMessage());
                continue;
            }
            String token = bean.getToken();
//            loginRequest.addFriend(token, "120433");
            EventBus.getDefault().post("设置代理");
            IPProxy.setProxy(null);
            EventBus.getDefault().post("代理设置成功");
            EventBus.getDefault().post(bean.getUsername() + "->登录成功，token->" + token);
            EventBus.getDefault().post("开始获取" + bean.getUsername() + "的好友列表");
            List<FriendListBean> friendListBeanList = loginRequest.getFriendLists(token);
            if (friendListBeanList == null || friendListBeanList.size() == 0) {
                EventBus.getDefault().post("获取好友失败，或没有好友"+loginRequest.getErrorMessage());
                continue;
            }
            EventBus.getDefault().post("设置代理");
            IPProxy.setProxy(null);
            EventBus.getDefault().post("代理设置成功");
            EventBus.getDefault().post("群主账号开始邀请：" + bean.getUsername() + "->进群");
            String other_id = bean.getId() + "";
            other_id = new String(rnCryptorNative.encrypt(other_id, Cons.KEY));
            String groupId = new String(rnCryptorNative.encrypt(configBean.getMainGroupId(), Cons.KEY));
            boolean join = loginRequest.groupJoin(MainActivityNew.groupOwerInfo.getToken(), other_id, groupId);
//            if (!join) {
//                EventBus.getDefault().post("邀请加群失败");
//                continue;
//            }
            EventBus.getDefault().post("设置代理");
            IPProxy.setProxy(null);
            EventBus.getDefault().post("代理设置成功");
            EventBus.getDefault().post("邀请加群成功");
            boolean addManage = loginRequest.addGroupManage(bean.getId() + "");
            if (!addManage) {
                EventBus.getDefault().post("添加管理员失败"+loginRequest.getErrorMessage());
                exitGroup(token);
                continue;
            }
            EventBus.getDefault().post("添加管理员成功");
            EventBus.getDefault().post("设置代理");
            IPProxy.setProxy(null);
            EventBus.getDefault().post("代理设置成功");
//            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < friendListBeanList.size(); i++) {
                String uids = new String(rnCryptorNative.encrypt(friendListBeanList.get(i).getUserid().toString(), Cons.KEY));
                if (!loginRequest.groupJoin(token, uids, groupId)) {
                    EventBus.getDefault().post("邀请--"+friendListBeanList.get(i).getNickname()+"--加群失败,原因："+loginRequest.getErrorMessage());
                }else{
                    EventBus.getDefault().post("邀请--"+friendListBeanList.get(i).getNickname()+"--加群成功");
                }
//                sb.append(friendListBeanList.get(i).getUserid());
//                if (i == friendListBeanList.size() - 1) {
//
//                } else {
//                    sb.append(",");
//                }
            }
            exitGroup(token);
//            String uids = new String(rnCryptorNative.encrypt(sb.toString(), Cons.KEY));
//            if (!loginRequest.groupJoin(token, uids, groupId)) {
//                EventBus.getDefault().post("邀请加群失败1111"+loginRequest.getErrorMessage());
//                exitGroup(token);
//                continue;
//            }
//            EventBus.getDefault().post("邀请加群成功1111");

//            exitGroup(token);
        }

        EventBus.getDefault().post("执行完成，开始等待" + taskRunTimeS/1000/60 + "分钟后执行");
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, taskRunTimeS);
        System.out.println(simpleDateFormat.format(nowTime.getTime()));
    }

    void exitGroup(String token){
        EventBus.getDefault().post("设置代理");
        IPProxy.setProxy(null);
        EventBus.getDefault().post("代理设置成功");
        //退群
        int i=0;
        while(!loginRequest.groupRemove(token)){
            if(i >= 3) {
                EventBus.getDefault().post("退群失败"+loginRequest.getErrorMessage());
                break;
            }
            i++;
        }
        EventBus.getDefault().post("退群成功");
    }
}
