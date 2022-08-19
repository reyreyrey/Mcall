package myapplication.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hjq.http.listener.OnHttpListener;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import myapplication.NickNameKeyWordsArrayList;
import myapplication.base.BaseActivity;
import myapplication.base.BaseMessageActivity;
import myapplication.base.Cons;
import myapplication.bean.ConfigBean;
import myapplication.bean.MemberAddBean;
import myapplication.modules.groupList.GroupListBean;
import myapplication.modules.groupMemberList.GroupMemberListBean;
import myapplication.modules.login.LoginBean;
import myapplication.modules.login.LoginRequest;
import myapplication.modules.proxy.IPProxy;
import myapplication.modules.proxy.IPProxyBean;
import myapplication.modules.searchUser.SearchUserBean;
import myapplication.modules.sms.smsLogin.SmsLoginBean;
import myapplication.service.TaskService;
import myapplication.utils.AddFriend;
import myapplication.utils.Config;
import myapplication.utils.Log2File;
import myapplication.utils.LogUtils;
import pub.devrel.easypermissions.EasyPermissions;
import tgio.benchmark.R;
import tgio.benchmark.databinding.ActivityMainNewBinding;
import tgio.rncryptor.RNCryptorNative;

public class MainActivityNew extends BaseMessageActivity<ActivityMainNewBinding> {

    @Override
    protected String getTitleStr() {
        return "首页";
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main_new;
    }

    @Override
    protected boolean showBack() {
        return false;
    }

    @Override
    protected void init() {
        EventBus.getDefault().register(this);
        requestPermission();
        binding.tvHintMessage.setText("加载中...");
        List<LoginBean> regs = LitePal.findAll(LoginBean.class);
        List<SearchUserBean> searchUserBeansAll = LitePal.findAll(SearchUserBean.class);
        List<SearchUserBean> searchUserBeans = LitePal.where("isAdded = ?", "0").find(SearchUserBean.class);
        binding.tvHintMessage.setText("已经注册的账号一共" + regs.size() + "个");
        binding.tvHintMessage.append("\n");
        binding.tvHintMessage.append("搜索到的用户数量：" + searchUserBeansAll.size() + "个");
        binding.tvHintMessage.append("\n");
        binding.tvHintMessage.append("搜索到的用户，但未添加的数量：" + searchUserBeans.size() + "个");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(String str){
        sendTextMessage(str);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<MemberAddBean> memberAddBeanList = LitePal.findAll(MemberAddBean.class);
        binding.btnGroupUser.setText("拉取群成员列表,目前共有"+memberAddBeanList.size()+"个群成员已经获取到");
    }


    private void requestPermission() {
        XXPermissions.with(this)
                // 不适配 Android 11 可以这样写
                //.permission(Permission.Group.STORAGE)
                // 适配 Android 11 需要这样写，这里无需再写 Permission.Group.STORAGE
                .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            toast("获取存储权限成功");
                            Log2File.init(getApplicationContext(), "log.txt");
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            toast("被永久拒绝授权，请手动授予存储权限");
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(context, permissions);
                        } else {
                            toast("获取存储权限失败");
                        }
                    }
                });
    }


    public void config(View v) {
        startActivity(new Intent(this, ConfigActivity.class));
    }

    public void regAccount(View v) {
        startActivity(new Intent(this, RegActivity.class));
    }

    public void export(View v) {
        List<LoginBean> regs = LitePal.findAll(LoginBean.class);
        String json = new GsonBuilder().create().toJson(regs);
        Log2File.w(json);
        Toast.makeText(context, "导出成功", Toast.LENGTH_LONG).show();
        Log2File.close();
    }

    public void addFriend(View v){
        startActivity(new Intent(this, AddFriendActivity.class));
    }

    public void loadGroupUser(){
        startActivity(new Intent(this, LoadGroupUserActivity.class));
    }

    public void joinGroupTask(View v){
        startService(new Intent(this, TaskService.class));
    }

    public void joni_group_manager(View v){
        List<LoginBean> regs = LitePal.findAll(LoginBean.class);
        LoginBean loginBean = regs.get(0);

    }

    void toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}