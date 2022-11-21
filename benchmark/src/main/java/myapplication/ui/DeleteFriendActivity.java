package myapplication.ui;

import android.text.TextUtils;
import android.view.View;

import org.litepal.LitePal;

import java.util.List;

import myapplication.base.BaseMessageActivity;
import myapplication.modules.friendlist.FriendListBean;
import myapplication.modules.login.LoginBean;
import myapplication.modules.login.LoginRequest;
import myapplication.modules.proxy.IPProxy;
import tgio.benchmark.R;
import tgio.benchmark.databinding.ActivityCheckAccountBinding;
import tgio.benchmark.databinding.ActivityDeleteFriendBinding;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.21 10:23
 */
public class DeleteFriendActivity extends BaseMessageActivity<ActivityDeleteFriendBinding> {
    private LoginRequest request;
    @Override
    protected String getTitleStr() {
        return "检测并删除好友";
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_delete_friend;
    }

    @Override
    protected void init() {
        request = new LoginRequest(this);
    }

    public void check(View view){
        loginAll();
    }

    public void loginAll() {
        String key_nickname = binding.edtNickname.getText().toString().trim();
        if(TextUtils.isEmpty(key_nickname))return;
        new Thread() {
            @Override
            public void run() {
                super.run();
                sendTextMessage("开始检测包含昵称为："+key_nickname+" 的好友");
                List<LoginBean> regs2 = LitePal.findAll(LoginBean.class);
                int count = 0;
                for (LoginBean bean : regs2){
                    sendTextMessage("开始设置代理");
                    IPProxy.setProxy(context);
                    sendTextMessage("代理设置成功");
                    sendTextMessage("开始登录"+bean.getNickname());
                    LoginBean loginBean = request.login(bean.getUsername(), "666888aa..", bean.getDeviceid(), bean.getClientid());
                    if(loginBean == null){
                        sendTextMessage("登录"+bean.getNickname()+"失败，原因："+request.getErrorMessage());
                    }else{
                        int userid = loginBean.getUser_id();
                        if(userid == -1){
                            sendTextMessage(bean.getNickname()+"已经被封，开始删除");
                            bean.delete();
                            count++;
                            sendTextMessage(bean.getNickname()+"删除完成");
                        }else{
                            sendTextMessage(loginBean.getUsername()+"登陆成功,状态正常");
                            bean.setToken(loginBean.getToken());
                            bean.save();
                            sendTextMessage("开始获取"+loginBean.getUsername()+"的好友列表");
                            List<FriendListBean> friendListBeanList = request.getFriendLists(loginBean.getToken());
                            if (friendListBeanList == null || friendListBeanList.size() == 0) {
                                sendTextMessage("获取好友失败，或没有好友"+request.getErrorMessage());
                                continue;
                            }
                            sendTextMessage(loginBean.getUsername()+"一共有："+friendListBeanList.size()+"个好友");
                            for(int i=0;i<friendListBeanList.size();i++){
                                FriendListBean listBean = friendListBeanList.get(i);
                                String nickname = listBean.getNickname();
                                if(nickname.equals(key_nickname)){
                                    sendTextMessage("第"+(i+1)+"个好友昵称是："+nickname+"包含设置的昵称，开始删除");
                                    if(request.deleteFriend(bean.getToken(), listBean.getOtherid().toString())){
                                        sendTextMessage("删除成功");
                                    }else{
                                        sendTextMessage("删除失败，原因："+request.getErrorMessage());
                                        continue;
                                    }
                                }else{
                                    sendTextMessage("第"+(i+1)+"个好友昵称是："+nickname+",不好含设置的昵称，开始检测下一个");
                                    continue;
                                }

                            }
                        }
                    }
                }
                sendDialogMessage("全部检测完成");

            }
        }.start();
    }
}
