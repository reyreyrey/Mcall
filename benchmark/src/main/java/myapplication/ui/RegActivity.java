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
    private String smsToken;


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
                smsToken = request.smsLogin();
                if (smsToken == null) {
                    sendTextMessage("接码平台登陆失败"+request.getErrorMessage());
                    return;
                }

//                double money = Double.parseDouble(request.smsyue);
//                sendTextMessage("接码平台登陆成功，余额：" + money);

//                if(money < 5){
//                    sendDialogMessage("余额少于5元，请登录接码平台充值，接码平台账号"+ Cons.sms_username+",密码："+Cons.sms_password);
//                    return;
//                }


                for (int i = 0; i < Config.getConfig().getRegCount(); i++) {
                    if(!isStart)return;
                    sendTextMessage("开始设置代理");
                    IPProxy.setProxy(context);



                    sendTextMessage("开始注册第" + (i + 1) + "个账号");
                    sendTextMessage("开始获取手机号");
                    String[] phoneNumAndToken = request.getPhoneNum();

                    if (phoneNumAndToken == null) {
                        //号码不足
                        sendDialogMessage("接码平台手机号不足，请稍等再试下，退出注册流程");
                        return;
                    }
//                    phoneNum = "86-" + phoneNum;
                    sendTextMessage("获取手机号成功：" + phoneNumAndToken[0]);

                    sendTextMessage("开始检测手机号是否可用");
                    boolean b = request.checkPhone(phoneNumAndToken[0]);
                    if (b) {
                        //号码已经存在了
                        sendTextMessage("手机号" + phoneNumAndToken[0] + "已经注册过了");
                        continue;
                    }
                    sendTextMessage("手机号" + phoneNumAndToken[0] + "可以使用，开始发送验证码");
                    //发送验证码
                    boolean success = request.sendSms(phoneNumAndToken[0]);
                    if (!success) {
                        //发送失败
                        sendTextMessage("手机号" + phoneNumAndToken[0] + "验证码发送失败"+request.getErrorMessage());
                        continue;
                    }
                    sendTextMessage("手机号" + phoneNumAndToken[0] + "验证码已经发送，开始等待获取接码平台的验证码");
                    //开始获取验证码，先等待5S

                    String code;
                    int count = 0;
                    do {
                        try {
                            sleep(5000);
                        } catch (InterruptedException e) {
                        }
                        code = request.getSms(phoneNumAndToken);
                        count++;
                    } while (TextUtils.isEmpty(code) && count < 48);
                    if (TextUtils.isEmpty(code)) {
                        sendTextMessage("验证码获取超时");
                        continue;
                    }
                    sendTextMessage("验证码获取成功，验证码是：" + code);
                    if (!request.checkSmsCode(phoneNumAndToken[0], code)) {
                        sendTextMessage("验证码检测失败"+request.getErrorMessage());
                        continue;
                    }
                    sendTextMessage("验证码检测成功，开始使用手机号登陆");
                    //登陆
                    String clientid = UUID.randomUUID().toString().toUpperCase(Locale.ROOT);
                    String deviceid = UUID.randomUUID().toString().toUpperCase(Locale.ROOT);
                    LoginBean loginBean = request.loginByPhoneNum(phoneNumAndToken[0], code, clientid, deviceid);
                    if(loginBean == null){
                        sendTextMessage("登录失败，原因："+request.getErrorMessage());
                        continue;
                    }
                    Calendar calendar = Calendar.getInstance();
                    String nickname = getRandomString();
                    String susername = getRandomString();
                    String username = RandomUtil.getRandomchar5()+RandomUtil.getRandomnum36();
//                    String username = RandomUtil.getRandomchar1()+RandomUtil.getRandomnum2()+RandomUtil.getRandomchar2()+calendar.get(Calendar.SECOND)+RandomUtil.getRandomchar1();
                    sendTextMessage("手机号登陆成功，开始设置用户名和密码");
                    sendTextMessage("用户名是：" + username);
                    sendTextMessage("密码是：666888aa..");

                    int setPersonInfoCount = 0;
                    boolean setPersonInfoSuccess = true;
                    //设置用户名
                    while(!request.setPersonInfo(loginBean.getToken(), phoneNumAndToken[0], nickname, susername)){
                        if(setPersonInfoCount > 3){
                            sendTextMessage("设置昵称失败"+request.getErrorMessage());
                            setPersonInfoSuccess = false;
                            break;
                        }
                        setPersonInfoCount++;
                        sendTextMessage("开始尝试第"+setPersonInfoCount+"设置昵称");
                    }

                    if(!setPersonInfoSuccess){
                        continue;
                    }


//                    boolean b1 = request.setPersonInfo(loginBean.getToken(), phoneNum, nickname, susername);
//                    if (!b1) {
//                        sendTextMessage("设置昵称失败"+request.getErrorMessage());
//                        continue;
//                    }
                    sendTextMessage("设置昵称成功");

                    int setUserNameCount = 0;
                    boolean setUserNameSuccess = true;
                    while(!request.setUserName(loginBean.getToken(), username)){
                        if(setUserNameCount > 3){
                            setUserNameSuccess = false;
                            sendTextMessage("设置用户名失败"+request.getErrorMessage());
                            break;
                        }
                        setUserNameCount++;
                        sendTextMessage("开始尝试第"+setUserNameCount+"设置用户名");
                    }
                    if(!setUserNameSuccess){
                        continue;
                    }

                    sendTextMessage("设置用户名成功");

                    int setPwdCount = 0;
                    boolean setPwdSuccess = true;
                    while(!request.setPwd(loginBean.getToken())){
                        if(setPwdCount > 3){
                            sendTextMessage("设置密码失败"+request.getErrorMessage());
                            setPwdSuccess = false;
                            break;
                        }
                        setPwdCount++;
                        sendTextMessage("开始尝试第"+setPwdCount+"设置密码");
                    }
                    if(!setPwdSuccess){
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
