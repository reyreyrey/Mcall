package myapplication.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.OnHttpListener;
import com.hjq.http.model.ResponseClass;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import myapplication.NickNameKeyWordsArrayList;
import myapplication.base.Cons;
import myapplication.bean.MemberAddBean;
import myapplication.modules.groupList.GroupListBean;
import myapplication.modules.groupMemberList.GroupMemberListBean;
import myapplication.modules.login.LoginBean;
import myapplication.modules.login.LoginRequest;
import myapplication.modules.searchUser.SearchUserBean;
import myapplication.modules.sms.smsLogin.SmsLoginBean;
import myapplication.utils.AddFriend;
import myapplication.modules.proxy.IPProxy;
import myapplication.modules.proxy.IPProxyBean;
import pub.devrel.easypermissions.EasyPermissions;
import tgio.benchmark.R;
import tgio.rncryptor.RNCryptorNative;

public class MainActivity extends AppCompatActivity implements OnHttpListener<Object> {

    private NickNameKeyWordsArrayList allMembers;
    private TextView tvMsg, tv_hint;
    private LoginRequest request;
    private SmsLoginBean smsLoginBean;
    private RNCryptorNative rnCryptorNative;
    private ScrollView scrollView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String text = (String) msg.obj;
                    tvMsg.append(text);
                    scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            scrollView.post(new Runnable() {
                                public void run() {
                                    scrollView.fullScroll(View.FOCUS_DOWN);
                                }
                            });
                        }
                    });
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allMembers = new NickNameKeyWordsArrayList();
        tvMsg = findViewById(R.id.tv_msg);
        tv_hint = findViewById(R.id.tv_hint);
        rnCryptorNative = new RNCryptorNative();
        request = new LoginRequest(this);
        scrollView = findViewById(R.id.scrollview);
        List<LoginBean> regs = LitePal.findAll(LoginBean.class);
        List<MemberAddBean> list = LitePal.findAll(MemberAddBean.class);
        tv_hint.setText("已经注册-->" + (regs == null ? 0 : regs.size()));
        List<SearchUserBean> searchUserBeans = LitePal.where("isAdded = ?", "0").find(SearchUserBean.class);
//        List<SearchUserBean> searchUserBeans = LitePal.findAll(SearchUserBean.class);

        Log.e("---->", "->搜索到的用户列表+"+searchUserBeans.size());
        EasyPermissions.requestPermissions(this, "ss", 1, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        EasyPermissions.requestPermissions(this, "ss", 1, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    public void loginAll() {
        new Thread() {
            @Override
            public void run() {
                super.run();


                List<LoginBean> regs2 = LitePal.findAll(LoginBean.class);
                for (LoginBean bean : regs2){
                    LoginBean loginBean = request.login(bean.getUsername(), "666888aa..", bean.getDeviceid(), bean.getClientid());
//                    loginBean.saveOrUpdate("id=?", loginBean.getId()+"");
                    sendTextMessage(loginBean.getUsername()+"登陆成功");
                }

            }
        }.start();
    }

    public void getAllMemberList(View v) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                getAllMemberList();
            }
        }.start();

    }


    public void regAccount(View v) {
        new Thread() {
            @Override
            public void run() {
                super.run();

                sendTextMessage("开始登陆接码平台");
                smsLoginBean = request.smsLogin();
                if (smsLoginBean == null) {
                    sendTextMessage("接码平台登陆失败");
                    return;
                }
                sendTextMessage("接码平台登陆成功，余额：" + smsLoginBean.getData().get(0).getMoney());


                for (int i = 0; i < 10; i++) {
                        sendTextMessage("开始设置代理");
                        IPProxyBean.Obj obj = IPProxy.setProxy(MainActivity.this);

                        sendTextMessage("代理设置成功IP：" + obj.getIp() + ",端口：" + obj.getPort());

                    sendTextMessage("开始注册第" + (i + 1) + "个账号");
                    sendTextMessage("开始获取手机号");
                    String phoneNum = request.getPhoneNum(smsLoginBean);

                    if (TextUtils.isEmpty(phoneNum)) {
                        //号码不足
                        sendTextMessage("接码平台手机号不足，请稍等再试下，退出注册流程");
                        return;
                    }
                    phoneNum = "86-" + phoneNum;
                    sendTextMessage("获取手机号成功：" + phoneNum);

//                    sendTextMessage("开始检测手机号是否可用");
//                    boolean b = request.checkPhone("86-" + phoneNum);
//                    if (b) {
//                        //号码已经存在了
//                        sendTextMessage("手机号" + phoneNum + "已经注册过了");
//                        continue;
//                    }
                    sendTextMessage("手机号" + phoneNum + "可以使用，开始发送验证码");
                    //发送验证码
                    boolean success = request.sendSms(phoneNum);
                    if (!success) {
                        //发送失败
                        sendTextMessage("手机号" + phoneNum + "验证码发送失败");
                        continue;
                    }
                    sendTextMessage("手机号" + phoneNum + "验证码已经发送，开始等待获取接码平台的验证码");
                    //开始获取验证码，先等待5S
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                    }
                    String code;
                    int count = 0;
                    do {
                        code = request.getSms(phoneNum);
                        count++;
                    } while (TextUtils.isEmpty(code) && count < 100);
                    if (TextUtils.isEmpty(code)) {
                        sendTextMessage("验证码获取超时");
                        continue;
                    }
                    sendTextMessage("验证码获取成功，验证码是：" + code);
                    if (!request.checkSmsCode(phoneNum, code)) {
                        sendTextMessage("验证码检测失败");
                        continue;
                    }
                    sendTextMessage("验证码检测成功，开始使用手机号登陆");
                    //登陆
                    String clientid = UUID.randomUUID().toString().toUpperCase(Locale.ROOT);
                    String deviceid = UUID.randomUUID().toString().toUpperCase(Locale.ROOT);
                    LoginBean loginBean = request.loginByPhoneNum(phoneNum, code, clientid, deviceid);

                    String nickname = "nn" + new SimpleDateFormat("ddHHmmss").format(new Date());
                    String susername = "sn" + new SimpleDateFormat("MMddHHmmss").format(new Date());
                    String username = "u" + new SimpleDateFormat("ddHHmmss").format(new Date());
                    sendTextMessage("手机号登陆成功，开始设置用户名和密码");
                    sendTextMessage("用户名是：" + username);
                    sendTextMessage("密码是：666888aa..");

                    //设置用户名
                    boolean b1 = request.setPersonInfo(loginBean.getToken(), phoneNum, nickname, susername);
                    if (!b1) {
                        sendTextMessage("设置昵称失败");
                        continue;
                    }
                    sendTextMessage("设置昵称成功");
                    boolean b2 = request.setUserName(loginBean.getToken(), username);
                    if (!b2) {
                        sendTextMessage("设置用户名失败");
                        continue;
                    }
                    sendTextMessage("设置用户名成功");
                    boolean b3 = request.setPwd(loginBean.getToken());
                    if (!b2) {
                        sendTextMessage("设置密码失败");
                        continue;
                    }
                    sendTextMessage("设置密码成功");
                    loginBean.setUsername(username);
                    loginBean.setDeviceid(deviceid);
                    loginBean.setClientid(clientid);
                    boolean falg = loginBean.save();
                    sendTextMessage("保存login--》" + falg);

                    List<LoginBean> regs = LitePal.findAll(LoginBean.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_hint.setText("已经注册-->" + (regs == null ? 0 : regs.size()));
                        }
                    });
                }

            }
        }.start();

    }


    void sendTextMessage(String msg) {
        Message message = Message.obtain();
        message.what = 1;
        message.obj = msg + "\n";
        handler.sendMessage(message);
    }


    void getAllMemberList() {
        allMembers.clear();
        sendTextMessage("正在登陆主账号");
        LoginBean bean = request.login(Cons.main_account_name, Cons.main_account_pwd, null, null);
        if (bean == null) {
            sendTextMessage("主账号登陆失败，请重新尝试");
            return;
        }
        String mainToken = bean.getToken();
//
//
        sendTextMessage("主账号登陆成功，Token：" + mainToken);
        sendTextMessage("开始获取主账号的群列表");
        //获取群列表
        List<GroupListBean> listHttpData = request.getGroupList(mainToken);
        if (listHttpData == null) {
            sendTextMessage("群列表获取失败，请重新尝试");
            return;
        }
//
        sendTextMessage("主账号的群列表获取成功，一共有 " + listHttpData.size() + " 个群");
//
        for (GroupListBean listBean : listHttpData) {
            String groupID = listBean.getId() + "";
            sendTextMessage("开始获取 " + listBean.getGroup_name() + "的群成员列表");
            //获取群成员
            List<GroupMemberListBean> l = request.getGroupMemberList(mainToken, groupID);
            if (l == null) {
                sendTextMessage("获取 " + listBean.getGroup_name() + "失败");
                continue;
            }
            sendTextMessage("获取 " + listBean.getGroup_name() + "的群成员列表成功，一共有 " + l.size() + " 个用户");
            allMembers.addAll(l);
        }
        sendTextMessage("，一共有 " + allMembers.size() + " 个用户");
        List<MemberAddBean> list = LitePal.findAll(MemberAddBean.class);
        for (GroupMemberListBean b : allMembers) {
            for (MemberAddBean addBean : list) {
                if (addBean.getNeedAddUserID() == b.getId()) {
                    //
                    Log.e("---->", b.getNick() + "已经存在于数据库中");
                    continue;
                }
            }
            MemberAddBean memberAddBean = new MemberAddBean();
            memberAddBean.setAdd(false);
            memberAddBean.setAgree(false);
            memberAddBean.setNeedAddUserNick(b.getNick());
            memberAddBean.setNeedAddUserID(b.getId());
            memberAddBean.save();
            Log.e("---->", b.getNick() + "存储完成");
        }


    }

    public void addFriends(View v) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                AddFriend.addFriend(request);
//                List<LoginBean> regs2 = LitePal.findAll(LoginBean.class);
//                for (LoginBean bean : regs2){
//                    LoginBean loginBean = request.login(bean.getUsername(), "666888aa..", bean.getDeviceid(), bean.getClientid());
//                    loginBean.saveOrUpdate("id=?", loginBean.getId()+"");
//                    sendTextMessage(loginBean.getUsername()+"登陆成功");
//                }
//                    //群里所有的群成员列表
//                    List<MemberAddBean> list = LitePal.findAll(MemberAddBean.class);
//                    //注册的用户列表
//                    List<LoginBean> regs = LitePal.findAll(LoginBean.class);
//                sendTextMessage("一共有--》"+regs.size()+"个账号");
//                    int posistion = 0;
//                    for(int i= 0;i<list.size();i++){
//                        if(i > 0 && i % 5 == 0){
//                            //切换账号
//                            Log.e("=======", ""+i);
//                            posistion++;
//                        }
//                        LoginBean bean = regs.get(posistion);
//                        MemberAddBean addBean = list.get(i);
//                        sendTextMessage("开始为第"+(posistion+1)+"个账号，username:"+bean.getUsername()+",添加好友，好友nick："+addBean.getNeedAddUserNick());
//                        boolean flag = request.addFriend(bean.getToken(), new String(rnCryptorNative.encrypt(addBean.getNeedAddUserID()+"", Cons.KEY)));
//                        if(flag){
//                            sendTextMessage("添加好友请求发送成功");
//                            addBean.setAdd(true);
//                            addBean.setMineUserID(bean.getId());
//                            addBean.save();
//                        }
//                    }

//                    for (GroupListBean listBean : listHttpData) {
//                        String groupID = listBean.getId() + "";
//                        sendTextMessage("开始获取 " + listBean.getGroup_name() + "的群成员列表");
//                        //获取群成员
//                        List<GroupMemberListBean> l = request.getGroupMemberList(mainToken, groupID);
//                        sendTextMessage("获取 " + listBean.getGroup_name() + "的群成员列表成功，一共有 " + l.size() + " 个用户");
//                        allMembers.addAll(l);
//                        //重新登陆需要添加好友的账号
////                        sendTextMessage("开始登陆需要添加好友的用户");
////                        String token = login("asd123hhh", "666888aa..").getToken();
////                        sendTextMessage("需要添加好友的用户登陆成功");
////                        for (GroupMemberListBean groupMemberListBean : l) {
////                            //根据用户id添加好友
////                            String other_id = groupMemberListBean.getId() + "";
////                            other_id = new String(rnCryptorNative.encrypt(other_id, Cons.KEY));
////
////                            sendTextMessage("开始为用户添加好友：昵称：" + groupMemberListBean.getNick());
////                            addFriend(token, other_id);
////                            sendTextMessage("昵称：" + groupMemberListBean.getNick() + " 添加成功");
////                        }
//                    }
//                    sendTextMessage("，一共有 " + allMembers.size() + " 个用户");
            }
        }.start();

    }


//
//    public void test(View v) {
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//
//                try {
//
//                    //登陆
//                    HttpData<LoginBean> bean = EasyHttp.post(MainActivity.this).api(new LoginApi().setParam(new GsonBuilder().create().toJson(new LoginApi.LoginRequestParams()))).execute(new ResponseClass<HttpData<LoginBean>>() {
//                    });
//                    //获取config
////                    ConfigRequester.requestConfig(bean.getData().getToken());
//                    HttpData<ConfigBean> infoBeanHttpData = EasyHttp.post(MainActivity.this).api(new ConfigApi().setParam(new GsonBuilder().create().toJson(new ConfigApi.ConfigRequestParams().setToken(bean.getData().getToken())))).execute(new ResponseClass<HttpData<ConfigBean>>() {
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
////
//    }


    @Override
    public void onSucceed(Object result) {

    }

    @Override
    public void onFail(Exception e) {

    }
}