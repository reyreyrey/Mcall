package myapplication.ui;

import static myapplication.base.Cons.project_id;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hjq.http.listener.OnHttpListener;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

import myapplication.NickNameKeyWordsArrayList;
import myapplication.base.BaseActivity;
import myapplication.base.BaseMessageActivity;
import myapplication.base.Cons;
import myapplication.bean.ConfigBean;
import myapplication.bean.MemberAddBean;
import myapplication.modules.audit.AuditListBean;
import myapplication.modules.friendlist.FriendListBean;
import myapplication.modules.groupList.GroupListBean;
import myapplication.modules.groupMemberList.GroupMemberListBean;
import myapplication.modules.isNewDevices.IsNewDeviceBean;
import myapplication.modules.login.LoginBean;
import myapplication.modules.login.LoginRequest;
import myapplication.modules.proxy.IPProxy;
import myapplication.modules.proxy.IPProxyBean;
import myapplication.modules.searchUser.SearchUserBean;
import myapplication.modules.sms.smsLogin.SmsLoginBean;
import myapplication.service.TaskService;
import myapplication.utils.AddFriend;
import myapplication.utils.Config;
import myapplication.utils.GetAllUserList;
import myapplication.utils.Log2File;
import myapplication.utils.LogUtils;
import myapplication.utils.MD5Utils;
import pub.devrel.easypermissions.EasyPermissions;
import tgio.benchmark.R;
import tgio.benchmark.databinding.ActivityMainNewBinding;
import tgio.rncryptor.RNCryptorNative;

public class MainActivityNew extends BaseMessageActivity<ActivityMainNewBinding> {

    public static MainActivityNew mainActivityNew;
    public static LoginBean groupOwerInfo, needLoadGroupInfo;
    private LoginRequest request;
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
        mainActivityNew = this;
        EventBus.getDefault().register(this);
        requestPermission();
        binding.tvHintMessage.setText("加载中...");
        request = new LoginRequest(this);

        List<String> memberAddBeanList = Arrays.asList("1","2","3","4","5","6");
        ListIterator<String> in = memberAddBeanList.listIterator();
        while(in.hasNext()){
            LogUtils.e("---->","data=="+in.next());
        }
//        for(int i=0;i<memberAddBeanList.size();i++){
//            LogUtils.e("---->","i=="+i);
//            LogUtils.e("---->","data=="+in.next());
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    public void showFriendNum(View v){
        startActivity(new Intent(this, ShowFriendCountActivity.class));
    }

    public void showGroupList(View v){
        if(groupOwerInfo == null){
            sendDialogMessage("先登陆群主账号");
            return;
        }
        new Thread(){
            @Override
            public void run() {
                super.run();
                showProgressDialog();
                List<GroupListBean> listHttpData = request.getGroupList(groupOwerInfo.getToken());
                if(listHttpData == null){
                    dismissProgressDialog();
                    sendDialogMessage("获取群列表失败，原因："+request.getErrorMessage());
                    return;
                }
                StringBuffer sb = new StringBuffer();
                for(GroupListBean groupListBean : listHttpData){
                    sb.append("群名字："+groupListBean.getGroup_name());
                    sb.append("群号："+groupListBean.getId());
                    sb.append("\n");
                }
                dismissProgressDialog();
                sendDialogMessage(sb.toString());
            }
        }.start();
    }
    public void includeAccount(View v){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try{

                showProgressDialog();
                String json = Log2File.readRegUser();
                JSONArray jsonArray = new JSONArray(json);
                LitePal.deleteAll(LoginBean.class);
//                List<LoginBean> loginBeans = new ArrayList<>();
//                    List<LoginBean> loginBeans1 = new ArrayList<>();
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    object.remove("baseObjId");
                    LoginBean bean = new GsonBuilder().create().fromJson(object.toString(), LoginBean.class);
//                    loginBeans.add(bean);
                    LogUtils.e("---->","保存---》"+ bean.save());
//                    loginBeans1.add(bean);
                }

//                    LogUtils.e("---->","111111---->"+ loginBeans.size());
//                    List<LoginBean> firstMenu=loginBeans.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()->new TreeSet<LoginBean>(Comparator.comparing(LoginBean::getUser_id))), ArrayList::new));
//                    loginBeans.clear();
//                    loginBeans.addAll(firstMenu);
//                    LogUtils.e("---->","22222---->"+ loginBeans.size());
////                    for(LoginBean bean : loginBeans){
////                        LogUtils.e("---->","5555---->"+ bean.getNickname());
////                    }
//                    LitePal.saveAll(loginBeans);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                    }
                });
//
                sendDialogMessage("导入成功");
                    LogUtils.e("---->","结束了");
                }catch (Exception e){
                    LogUtils.e("---->","Exception->"+e.toString());
                }
                dismissProgressDialog();
            }
        }.start();


    }

    public void checkAccountStatus(View v){
        startActivity(new Intent(context, CheckAccountStatusActivity.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(String str){
        sendTextMessage(str);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                List<LoginBean> regs = LitePal.findAll(LoginBean.class);
                runOnUiThread(() -> {
                    binding.tvHintMessage.setText("已经注册的账号一共" + regs.size() + "个");
                    binding.tvHintMessage.append("\n");
                });
                List<MemberAddBean> memberAddBeanList = LitePal.findAll(MemberAddBean.class);
                runOnUiThread(() -> {
                    binding.btnGroupUser.setText("拉取群成员列表,目前共有"+memberAddBeanList.size()+"个群成员已经获取到");
                });
                List<SearchUserBean> searchUserBeansAll = LitePal.findAll(SearchUserBean.class);
                LogUtils.e("---->", searchUserBeansAll.toString());
                List<SearchUserBean> searchUserBeans = LitePal.where("isAdded = ?", "0").find(SearchUserBean.class);

                runOnUiThread(()->{
                    binding.tvHintMessage.append("搜索到的用户数量：" + searchUserBeansAll.size() + "个");
                    binding.tvHintMessage.append("\n");
                    binding.tvHintMessage.append("搜索到的用户，但未添加的数量：" + searchUserBeans.size() + "个");

                    if(groupOwerInfo != null){
                        binding.btnGroupOwer.setText("群主账号已经登陆（每次启动都要登录）");
                        binding.btnGroupOwer.setEnabled(false);
                    }else{
                        binding.btnGroupOwer.setText("登录群主账号");
                        binding.btnGroupOwer.setEnabled(true);
                    }

                    if(needLoadGroupInfo != null){
                        binding.btnGroupLoader.setText("有群的账号已经登陆");
                        binding.btnGroupLoader.setEnabled(false);
                    }else{
                        binding.btnGroupLoader.setText("登录有群的账号（每次启动都要登录）");
                        binding.btnGroupLoader.setEnabled(true);
                    }
                });
            }
        }.start();
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
        Log2File.writeRegUser(json);
        Toast.makeText(context, "导出成功", Toast.LENGTH_LONG).show();
    }

    public void addFriend(View v){
        startActivity(new Intent(this, AddFriendActivity.class));
    }

    public void loadGroupUser(View v){
        startActivity(new Intent(this, LoadGroupUserActivity.class));
    }

    public void joinGroupTask(View v){
        startService(new Intent(this, TaskService.class));
    }



    public void searchUserById(View v){
        new Thread(){
            @Override
            public void run() {
                super.run();
                List<LoginBean> regs = LitePal.findAll(LoginBean.class);
                LoginBean loginBean = regs.get(0);
                loginBean = request.login(loginBean.getUsername(), "666888aa..", loginBean.getDeviceid(), loginBean.getClientid());
                GetAllUserList.getAllUser(new LoginRequest(context), loginBean.getToken());
            }
        }.start();
    }

    public void loginGroupOwerAccount(View v){
        new Thread(){
            @Override
            public void run() {
                super.run();
                showProgressDialog();
                IPProxy.setProxy(context);
                ConfigBean configBean = Config.getConfig();
                String username = configBean.getMainGroupAccount();
                String password = MD5Utils.encode(configBean.getMainGroupPwd());
                IsNewDeviceBean isNewDeviceBean = request.isNewDevice(username, password, Cons.main_group_deviceId);
                if(isNewDeviceBean == null){
                    dismissProgressDialog();
                    sendDialogMessage("主账号登录失败"+request.getErrorMessage());
                    return;
                }
                if(isNewDeviceBean.getIsNewDevice() == 1){
                    inputCode(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LogUtils.e("---->", code);
                            new Thread(){
                                @Override
                                public void run() {
                                    super.run();
                                    loginMain();
                                }
                            }.start();
                        }
                    });
                }else{
                    loginMain();
                }
            }
        }.start();
    }



    void loginMain(){
        ConfigBean configBean = Config.getConfig();
        LoginBean bean = request.login(
                configBean.getMainGroupAccount(),
                configBean.getMainGroupPwd(),
                Cons.main_group_deviceId,
                Cons.main_group_deviceId,
                code);
        if(bean == null){
            dismissProgressDialog();
            sendDialogMessage("主账号登录失败"+request.getErrorMessage());
            return;
        }
        if(bean.getId() == null){
            dismissProgressDialog();
            sendDialogMessage("主账号登录失败"+request.getErrorMessage());
            return;
        }
        groupOwerInfo = bean;
        dismissProgressDialog();
        sendDialogMessage("主账号登录成功");
    }

    public void loginNeedLoadGroupUserAccount(View v){
        new Thread(){
            @Override
            public void run() {
                super.run();
                showProgressDialog();
                IPProxy.setProxy(context);
                ConfigBean configBean = Config.getConfig();
                String username = configBean.getGroupAccount();
                String password = MD5Utils.encode(configBean.getGroupPwd());
                IsNewDeviceBean isNewDeviceBean = request.isNewDevice(username, password, Cons.main_group_deviceId);
                if(isNewDeviceBean == null){
                    dismissProgressDialog();
                    sendDialogMessage("群账号登录失败"+request.getErrorMessage());
                    return;
                }
                if(isNewDeviceBean.getIsNewDevice() == 1){
                    inputCode(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LogUtils.e("---->", code);
                            new Thread(){
                                @Override
                                public void run() {
                                    super.run();
                                    loginGroup();
                                }
                            }.start();
                        }
                    });
                }else{
                    loginGroup();
                }
            }
        }.start();
    }

    void loginGroup(){
        ConfigBean configBean = Config.getConfig();
        LoginBean bean = request.login(
                configBean.getGroupAccount(),
                configBean.getGroupPwd(),
                Cons.main_group_deviceId,
                Cons.main_group_clientId,
                code);
        if(bean == null){
            dismissProgressDialog();
            sendDialogMessage("群账号登录失败"+request.getErrorMessage());
            return;
        }
        needLoadGroupInfo = bean;
        dismissProgressDialog();
        sendDialogMessage("群账号登录成功");
    }

    public void editNickname(View v){
        startActivity(new Intent(context, EditNicknameActivity.class));
    }

    public void addOwerFriend(View v){
        startActivity(new Intent(context, AddOwerFriendsActivity.class));
    }

    void toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public void clearCache(View v){
        LitePal.deleteAll(FriendListBean.class);
        sendDialogMessage("清空成功");
    }

    public void saveUidFriend(View v){
        new Thread(){
            @Override
            public void run() {
                super.run();
                List<SearchUserBean> searchUserBeansAll = LitePal.findAll(SearchUserBean.class);
                String json = new GsonBuilder().create().toJson(searchUserBeansAll);
                Log2File.newFindUserFile();
                Log2File.writeFindUser(json);
                sendDialogMessage("导出成功");
            }
        }.start();

    }
    public void importUidFriend(View v){
        String uids = Log2File.readFindUser();
        if(TextUtils.isEmpty(uids)){
            Toast.makeText(context, "没有缓存", Toast.LENGTH_LONG).show();
            return;
        }
        new Thread(){
            @Override
            public void run() {
                super.run();
                try{

                    showProgressDialog();
                    String json = Log2File.readFindUser();
                    JSONArray jsonArray = new JSONArray(json);
                    LitePal.deleteAll(SearchUserBean.class);
                    List<SearchUserBean> loginBeans = new ArrayList();
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        object.remove("baseObjId");
                        SearchUserBean bean = new GsonBuilder().create().fromJson(object.toString(), SearchUserBean.class);
                        loginBeans.add(bean);
//                        LogUtils.e("---->","保存---》"+ bean.save());
                    }
                    List<SearchUserBean> firstMenu=loginBeans.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()->new TreeSet<SearchUserBean>(Comparator.comparing(SearchUserBean::getUserid))), ArrayList::new));
                    LitePal.saveAll(firstMenu);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            refresh();
                        }
                    });
//
                    sendDialogMessage("导入成功");
                    LogUtils.e("---->","结束了");
                }catch (Exception e){
                    LogUtils.e("---->","Exception->"+e.toString());
                }
                dismissProgressDialog();
            }
        }.start();
    }
    public void clearUidFriend(View v){
        Log2File.newFindUserFile();
        sendDialogMessage("删除成功");
    }

    public void clearUidFriendStatus(View v){
        new Thread(){
            @Override
            public void run() {
                super.run();
                showProgressDialog();
                List<SearchUserBean> searchUserBeansAll = LitePal.findAll(SearchUserBean.class);
                for(SearchUserBean bean : searchUserBeansAll){
                    bean.setAdded(false);
                    bean.save();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                    }
                });

                List<SearchUserBean> searchUserBeansAll1 = LitePal.findAll(SearchUserBean.class);
                String json = new GsonBuilder().create().toJson(searchUserBeansAll1);
                Log2File.newFindUserFile();
                Log2File.writeFindUser(json);
                dismissProgressDialog();
                sendDialogMessage("清除完毕");
            }
        }.start();
    }

    public void deleteFriend(View v){
        startActivity(new Intent(this, DeleteFriendActivity.class));
    }
}