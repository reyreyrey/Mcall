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
public class EditNicknameActivity extends BaseMessageActivity<ActivityCheckAccountBinding> {
    private LoginRequest request;
    private static final String [] nicknames = {"管理", "财财", "财务", "主持管理"};
    @Override
    protected String getTitleStr() {
        return "批量修改昵称";
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_edit_nickname;
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
                    sendTextMessage("开始设置"+bean.getNickname());
                    sendTextMessage("开始设置代理");
                    IPProxy.setProxy(context);
                    sendTextMessage("代理设置成功");
                    sendTextMessage("开始登录"+bean.getNickname());
                    LoginBean loginBean = request.login(bean.getUsername(), "666888aa..", bean.getDeviceid(), bean.getClientid());
                    if(loginBean == null || loginBean.isfeng()){
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
                            String token = loginBean.getToken();
                            String nickname = nicknames[(int) (Math.random()*3)] + "（进群有福利）";
                            boolean flag = request.editNickname(token, nickname);
                            if(flag){
                                sendTextMessage(bean.getNickname()+"修改昵称完成，修改为:"+nickname);
                            }else{
                                sendTextMessage(bean.getNickname()+"修改昵称失败，原因为:"+request.getErrorMessage());
                            }
                        }

                    }
                }
                sendDialogMessage("----------全部完成----------");

            }
        }.start();
    }
}
