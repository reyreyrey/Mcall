package myapplication.service;

import static java.lang.Thread.sleep;

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
        loginRequest = new LoginRequest(MyApp.fragmentActivity);
        configBean = Config.getConfig();
        rnCryptorNative = new RNCryptorNative();
    }

    @Override
    public void run() {
        EventBus.getDefault().post("-----------------------------------------");
        EventBus.getDefault().post("开始执行");
        String mainGroupID = configBean.getMainGroupId();
        if(TextUtils.isEmpty(mainGroupID)){
            EventBus.getDefault().post("请配置主群id");
            return;
        }
//        EventBus.getDefault().post("设置代理");
//        IPProxy.setProxy(null);
//        EventBus.getDefault().post("代理设置成功");
//        EventBus.getDefault().post("开始登录主群群主的账号");
//        LoginBean mainGroupLoginBean = loginRequest.login(configBean.getMainGroupAccount(), configBean.getMainGroupPwd(), null,null);
//        if(mainGroupLoginBean == null){
//            EventBus.getDefault().post("登录主群群主的账号失败");
//            return;
//        }
//        EventBus.getDefault().post("登录主群群主的账号成功");
        EventBus.getDefault().post("开始获取所有的注册用户");
        List<LoginBean> regs = LitePal.findAll(LoginBean.class);
        EventBus.getDefault().post("注册用户一共有："+regs.size()+"个");
        for(LoginBean loginBean : regs){
            EventBus.getDefault().post("设置代理");
            IPProxy.setProxy(null);
            EventBus.getDefault().post("代理设置成功");
            EventBus.getDefault().post("开始登录"+loginBean.getNickname());
            LoginBean bean = loginRequest.login(loginBean.getUsername(), "666888aa..", loginBean.getDeviceid(), loginBean.getClientid());
            if(bean == null){
                EventBus.getDefault().post(bean.getNickname()+"->登录失败");
                continue;
            }
            String token = bean.getToken();
            EventBus.getDefault().post(bean.getNickname()+"->登录成功，token->"+token);
            EventBus.getDefault().post("开始获取"+bean.getNickname()+"的好友列表");
            List<FriendListBean> friendListBeanList = loginRequest.getFriendLists(token);
            if(friendListBeanList == null || friendListBeanList.size() == 0){
                EventBus.getDefault().post("获取好友失败，或没有好友");
                continue;
            }
            EventBus.getDefault().post("群主账号开始邀请："+bean.getNickname()+"->进群");
            String other_id = bean.getId() + "";
            other_id = new String(rnCryptorNative.encrypt(other_id, Cons.KEY));
            String groupId = new String(rnCryptorNative.encrypt(configBean.getMainGroupId(), Cons.KEY));

        }

        EventBus.getDefault().post("执行完成，开始等待"+taskRunTimeS+"分钟后执行");
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, taskRunTimeS);
        System.out.println(simpleDateFormat.format(nowTime.getTime()));
    }
}
