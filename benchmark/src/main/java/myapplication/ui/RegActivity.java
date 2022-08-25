package myapplication.ui;

import static myapplication.utils.RandomUtil.getRandomString;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import myapplication.base.BaseActivity;
import myapplication.base.BaseMessageActivity;
import myapplication.base.Cons;
import myapplication.modules.login.LoginBean;
import myapplication.modules.login.LoginRequest;
import myapplication.modules.proxy.IPProxy;
import myapplication.modules.proxy.IPProxyBean;
import myapplication.modules.sms.smsLogin.SmsLoginBean;
import myapplication.utils.Config;
import myapplication.utils.Log2File;
import myapplication.utils.RandomUtil;
import tgio.benchmark.R;
import tgio.benchmark.databinding.ActivityRegBinding;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.16 14:03
 */
public class RegActivity extends BaseMessageActivity<ActivityRegBinding> {
    private boolean isStart = false;
    private LoginRequest request;
    private SmsLoginBean smsLoginBean;


    @Override
    protected String getTitleStr() {
        return "注册";
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_reg;
    }

    @Override
    protected void init() {
        binding.startReg.setText("开始注册(0/" + Config.getConfig().getRegCount() + ")");
        binding.startReg.setOnClickListener(v -> {
            if (isStart) return;
            isStart = true;
            v.setEnabled(false);
            binding.stopReg.setEnabled(true);
            reg();
        });
        binding.stopReg.setOnClickListener(v -> {
            if(!isStart)return;
            isStart = false;
            v.setEnabled(false);
            binding.startReg.setEnabled(true);
        });

        request = new LoginRequest(this);
    }

    private void reg(){
        new Thread() {
            @Override
            public void run() {
                super.run();

                sendTextMessage("开始登陆接码平台");
                smsLoginBean = request.smsLogin();
                if (smsLoginBean == null) {
                    sendTextMessage("接码平台登陆失败"+request.getErrorMessage());
                    return;
                }

                double money = Double.parseDouble(smsLoginBean.getData().get(0).getMoney());
                sendTextMessage("接码平台登陆成功，余额：" + money);

                if(money < 10){
                    sendDialogMessage("余额少于10元，请登录接码平台充值，接码平台账号"+ Cons.sms_username+",密码："+Cons.sms_password);
                    return;
                }


                for (int i = 0; i < Config.getConfig().getRegCount(); i++) {
                    if(!isStart)return;
                    sendTextMessage("开始设置代理");
                    IPProxyBean.Obj obj = IPProxy.setProxy(context);

                    sendTextMessage("代理设置成功IP：" + obj.getIp() + ",端口：" + obj.getPort());

                    sendTextMessage("开始注册第" + (i + 1) + "个账号");
                    sendTextMessage("开始获取手机号");
                    String phoneNum = request.getPhoneNum(smsLoginBean);

                    if (TextUtils.isEmpty(phoneNum)) {
                        //号码不足
                        sendDialogMessage("接码平台手机号不足，请稍等再试下，退出注册流程");
                        return;
                    }
                    phoneNum = "86-" + phoneNum;
                    sendTextMessage("获取手机号成功：" + phoneNum);

                    sendTextMessage("开始检测手机号是否可用");
                    boolean b = request.checkPhone("86-" + phoneNum);
                    if (b) {
                        //号码已经存在了
                        sendTextMessage("手机号" + phoneNum + "已经注册过了");
                        continue;
                    }
                    sendTextMessage("手机号" + phoneNum + "可以使用，开始发送验证码");
                    //发送验证码
                    boolean success = request.sendSms(phoneNum);
                    if (!success) {
                        //发送失败
                        sendTextMessage("手机号" + phoneNum + "验证码发送失败"+request.getErrorMessage());
                        continue;
                    }
                    sendTextMessage("手机号" + phoneNum + "验证码已经发送，开始等待获取接码平台的验证码");
                    //开始获取验证码，先等待5S

                    String code;
                    int count = 0;
                    do {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                        }
                        code = request.getSms(phoneNum);
                        count++;
                    } while (TextUtils.isEmpty(code) && count < 150);
                    if (TextUtils.isEmpty(code)) {
                        sendTextMessage("验证码获取超时");
                        continue;
                    }
                    sendTextMessage("验证码获取成功，验证码是：" + code);
                    if (!request.checkSmsCode(phoneNum, code)) {
                        sendTextMessage("验证码检测失败"+request.getErrorMessage());
                        continue;
                    }
                    sendTextMessage("验证码检测成功，开始使用手机号登陆");
                    //登陆
                    String clientid = UUID.randomUUID().toString().toUpperCase(Locale.ROOT);
                    String deviceid = UUID.randomUUID().toString().toUpperCase(Locale.ROOT);
                    LoginBean loginBean = request.loginByPhoneNum(phoneNum, code, clientid, deviceid);
                    if(loginBean == null){
                        sendTextMessage("登录失败，原因："+request.getErrorMessage());
                        continue;
                    }
                    Calendar calendar = Calendar.getInstance();
                    String nickname = getRandomString();
                    String susername = getRandomString();
                    String username = RandomUtil.getRandomchar1()+RandomUtil.getRandomnum2()+RandomUtil.getRandomchar2()+calendar.get(Calendar.SECOND)+RandomUtil.getRandomchar1();
                    sendTextMessage("手机号登陆成功，开始设置用户名和密码");
                    sendTextMessage("用户名是：" + username);
                    sendTextMessage("密码是：666888aa..");

                    //设置用户名
                    boolean b1 = request.setPersonInfo(loginBean.getToken(), phoneNum, nickname, susername);
                    if (!b1) {
                        sendTextMessage("设置昵称失败"+request.getErrorMessage());
                        continue;
                    }
                    sendTextMessage("设置昵称成功");
                    boolean b2 = request.setUserName(loginBean.getToken(), username);
                    if (!b2) {
                        sendTextMessage("设置用户名失败"+request.getErrorMessage());
                        continue;
                    }
                    sendTextMessage("设置用户名成功");
                    boolean b3 = request.setPwd(loginBean.getToken());
                    if (!b3) {
                        sendTextMessage("设置密码失败"+request.getErrorMessage());
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
                            binding.startReg.setText("开始注册("+(regs == null ? 0 : regs.size())+"/" + Config.getConfig().getRegCount() + ")");
                        }
                    });
                }

            }
        }.start();

    }


}
