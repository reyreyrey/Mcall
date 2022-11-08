package myapplication.ui;

import android.view.View;

import org.litepal.LitePal;

import java.util.List;

import myapplication.base.BaseMessageActivity;
import myapplication.modules.login.LoginBean;
import myapplication.modules.login.LoginRequest;
import myapplication.modules.proxy.IPProxy;
import tgio.benchmark.R;
import tgio.benchmark.databinding.ActivityCheckAccountBinding;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.21 10:23
 */
public class CheckAccountStatusActivity extends BaseMessageActivity<ActivityCheckAccountBinding> {
    private LoginRequest request;
    @Override
    protected String getTitleStr() {
        return "检测账号状态";
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_check_account;
    }

    @Override
    protected void init() {
        request = new LoginRequest(this);
    }

    public void check(View view){
        loginAll();
    }

    public void loginAll() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                List<LoginBean> regs2 = LitePal.findAll(LoginBean.class);
                int count = 0;
                for (LoginBean bean : regs2){
                    sendTextMessage("开始检测"+bean.getNickname());
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
                        }
                    }
                }
                sendDialogMessage("全部检测完成,一共有"+count+"个账号被封");

            }
        }.start();
    }
}
