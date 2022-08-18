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

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import myapplication.NickNameKeyWordsArrayList;
import myapplication.base.BaseActivity;
import myapplication.base.Cons;
import myapplication.bean.MemberAddBean;
import myapplication.modules.groupList.GroupListBean;
import myapplication.modules.groupMemberList.GroupMemberListBean;
import myapplication.modules.login.LoginBean;
import myapplication.modules.login.LoginRequest;
import myapplication.modules.proxy.IPProxy;
import myapplication.modules.proxy.IPProxyBean;
import myapplication.modules.searchUser.SearchUserBean;
import myapplication.modules.sms.smsLogin.SmsLoginBean;
import myapplication.utils.AddFriend;
import myapplication.utils.Log2File;
import pub.devrel.easypermissions.EasyPermissions;
import tgio.benchmark.R;
import tgio.benchmark.databinding.ActivityMainNewBinding;
import tgio.rncryptor.RNCryptorNative;

public class MainActivityNew extends BaseActivity<ActivityMainNewBinding> {

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
        requestPermission();
        binding.tvHint.setText("加载中...");
        List<LoginBean> regs = LitePal.findAll(LoginBean.class);
        List<SearchUserBean> searchUserBeansAll = LitePal.findAll(SearchUserBean.class);
        List<SearchUserBean> searchUserBeans = LitePal.where("isAdded = ?", "0").find(SearchUserBean.class);
        binding.tvHint.setText("已经注册的账号一共" + regs.size() + "个");
        binding.tvHint.append("\n");
        binding.tvHint.append("搜索到的用户数量：" + searchUserBeansAll.size() + "个");
        binding.tvHint.append("\n");
        binding.tvHint.append("搜索到的用户，但未添加的数量：" + searchUserBeans.size() + "个");
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

    }

    void toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}