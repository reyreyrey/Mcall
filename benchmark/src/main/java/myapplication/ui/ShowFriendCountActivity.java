package myapplication.ui;

import android.text.TextUtils;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePal;

import java.util.List;

import myapplication.base.BaseMessageActivity;
import myapplication.base.Cons;
import myapplication.modules.friendlist.FriendListBean;
import myapplication.modules.login.LoginBean;
import myapplication.modules.login.LoginRequest;
import myapplication.modules.proxy.IPProxy;
import tgio.benchmark.R;
import tgio.benchmark.databinding.ActivityShowFriendNumBinding;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.25 09:25
 */
public class ShowFriendCountActivity extends BaseMessageActivity<ActivityShowFriendNumBinding> {
    private LoginRequest loginRequest;
    @Override
    protected String getTitleStr() {
        return "展示用户好友个数";
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_show_friend_num;
    }

    @Override
    protected void init() {
        loginRequest = new LoginRequest(this);
    }
    
    public void check(View view){
        new Thread(){
            @Override
            public void run() {
                super.run();
                sendTextMessage("开始获取所有的注册用户");
                List<LoginBean> regs = LitePal.findAll(LoginBean.class);
                sendTextMessage("注册用户一共有：" + regs.size() + "个");
                int count = 0;
                for (LoginBean loginBean : regs) {
                    if(TextUtils.isEmpty(loginBean.getUsername())){
                        continue;
                    }
                    sendTextMessage("设置代理");
                    IPProxy.setProxy(null);
                    sendTextMessage("代理设置成功");

                    sendTextMessage("开始登录" + loginBean.getUsername());
                    LoginBean bean = loginRequest.login(loginBean.getUsername(), "666888aa..", loginBean.getDeviceid(), loginBean.getClientid());
                    if (bean == null) {
                        sendTextMessage(loginBean.getUsername() + "->登录失败"+loginRequest.getErrorMessage());
                        continue;
                    }
                    String token = bean.getToken();
                    sendTextMessage(bean.getUsername() + "->登录成功，token->" + token);
                    sendTextMessage("开始获取" + bean.getUsername() + "的好友列表");
                    List<FriendListBean> friendListBeanList = loginRequest.getFriendLists(token);
                    if (friendListBeanList == null || friendListBeanList.size() == 0) {
                        sendTextMessage("获取好友失败，或没有好友"+loginRequest.getErrorMessage());
                        continue;
                    }
                    count+=friendListBeanList.size();
                    sendTextMessage(bean.getUsername()+"的好友一共有："+friendListBeanList.size()+"个");
                }


                sendTextMessage("一共有："+count+"个好友");
                sendDialogMessage("一共有："+count+"个好友");
                
                
            }
        }.start();
    }
}
