package myapplication.ui;

import android.content.DialogInterface;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import org.litepal.LitePal;

import java.util.List;

import myapplication.base.BaseMessageActivity;
import myapplication.modules.audit.AuditListBean;
import myapplication.modules.login.LoginBean;
import myapplication.modules.login.LoginRequest;
import myapplication.modules.proxy.IPProxy;
import tgio.benchmark.R;
import tgio.benchmark.databinding.ActivityAddOwerFriendBinding;
import tgio.benchmark.databinding.ActivityCheckAccountBinding;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.21 10:38
 */
public class AddOwerFriendsActivity extends BaseMessageActivity<ActivityAddOwerFriendBinding> {
    private LoginRequest request;
    @Override
    protected String getTitleStr() {
        return "添加群主好友";
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_add_ower_friend;
    }

    @Override
    protected void init() {
        request = new LoginRequest(this);
    }

    public void aggren(View v){
        if(MainActivityNew.groupOwerInfo == null){
            new AlertDialog.Builder(context).setMessage("先登录群主账号").setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).create().show();
            return;
        }

        new Thread(){
            @Override
            public void run() {
                super.run();
                sendTextMessage("开始用群主账号同意添加请求");
                sendTextMessage("群主账号开始获取请求列表");
                //获取请求列表
                List<AuditListBean> auditListBeans = request.auditList();
                if(auditListBeans == null){
                    dismissProgressDialog();
                    sendDialogMessage("获取申请列表失败，原因"+request.getErrorMessage());
                    return;
                }
                sendTextMessage("获取申请列表成功，一共有"+auditListBeans.size()+"个请求，开始逐一同意添加");
                //同意添加
                for(AuditListBean auditListBean : auditListBeans){
                    sendTextMessage("开始同意："+auditListBean.getNickname()+"的添加请求");
                    if(request.auditAgree(auditListBean)){
                        sendTextMessage(auditListBean.getNickname()+"的好友请求已经通过");
                    }else{
                        sendTextMessage(auditListBean.getNickname()+"的好友请求失败，原因："+request.getErrorMessage());
                    }
                }

                sendDialogMessage("添加好友完成");
            }
        }.start();
    }

    public void check(View v){
        if(MainActivityNew.groupOwerInfo == null){
            new AlertDialog.Builder(context).setMessage("先登录群主账号").setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).create().show();
            return;
        }
        addOwerFriend();
    }



    public void addOwerFriend(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                sendTextMessage("开始执行");
                List<LoginBean> regs = LitePal.findAll(LoginBean.class);
                sendTextMessage("一共有"+regs.size()+"个注册用户");
                for(LoginBean bean : regs){
                    sendTextMessage("开始设置代理");
                    IPProxy.setProxy(context);
                    sendTextMessage("设置代理成功");
                    sendTextMessage("开始登录"+bean.getNickname());
                    LoginBean loginBean = request.login(bean.getUsername(), "666888aa..", bean.getDeviceid(), bean.getClientid());
                    if(loginBean == null || loginBean.isfeng()){
                        sendTextMessage(bean.getNickname()+"登录失败，原因："+request.getErrorMessage());
                        continue;
                    }
                    sendTextMessage(bean.getNickname()+"登录成功，开始添加好友");
                    if(request.addFriend(loginBean.getToken(), MainActivityNew.groupOwerInfo.getUser_id()+"")){
                        sendTextMessage(bean.getNickname()+"添加好友成功");
                    }else{
                        sendTextMessage(bean.getNickname()+"添加好友失败，原因"+request.getErrorMessage());
                    }
                }
                sendTextMessage("全部好友添加完成，");
                sendDialogMessage("全部好友添加完成，");

            }
        }.start();
    }
}
