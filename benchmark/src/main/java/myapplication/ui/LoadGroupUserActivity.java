package myapplication.ui;

import android.content.DialogInterface;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

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
import myapplication.utils.Config;
import tgio.benchmark.R;
import tgio.benchmark.databinding.ActivityLoadGroupUserBinding;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.19 15:04
 */
public class LoadGroupUserActivity extends BaseMessageActivity<ActivityLoadGroupUserBinding> {
    private LoginRequest request;
    private NickNameKeyWordsArrayList allMembers;
    private int count;
    private boolean isRun = false;
    @Override
    protected String getTitleStr() {
        return "拉取群好友";
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_load_group_user;
    }

    @Override
    protected void init() {
        allMembers = new NickNameKeyWordsArrayList();;
        request = new LoginRequest(this);
        List<MemberAddBean> memberAddBeanList = LitePal.findAll(MemberAddBean.class);
        count = memberAddBeanList.size();
        binding.btn.setText("拉取群成员列表,目前共有"+count+"个群成员已经获取到");
        binding.btn.setOnClickListener(v->{
            if(MainActivityNew.needLoadGroupInfo == null){
                new AlertDialog.Builder(context).setMessage("请先登录有群的账号").setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                }).create().show();
                return;
            }
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    if(isRun)return;
                    isRun = true;
                    getAllMemberList();
                    isRun = false;
                }
            }.start();
        });
    }

    void getAllMemberList() {
        sendTextMessage("获取配置");
        ConfigBean configBean = Config.getConfig();
        sendTextMessage("配置的账号->"+configBean.getGroupAccount());
        sendTextMessage("配置的密码->"+configBean.getGroupPwd());
        sendTextMessage("开始配置代理");
        IPProxy.setProxy(this);
        sendTextMessage("代理配置成功");

        String mainToken = MainActivityNew.needLoadGroupInfo.getToken();
        sendTextMessage("开始获取群列表");
        //获取群列表
        List<GroupListBean> listHttpData = request.getGroupList(mainToken);
        if (listHttpData == null) {
            sendDialogMessage("群列表获取失败，请重新尝试,原因："+request.getErrorMessage());
            return;
        }
//
        sendTextMessage("群列表获取成功，一共有 " + listHttpData.size() + " 个群");
//
        for (GroupListBean listBean : listHttpData) {
            String groupID = listBean.getId() + "";
            sendTextMessage("开始获取 " + listBean.getGroup_name() + "的群成员列表");
            //获取群成员
            List<GroupMemberListBean> l = request.getGroupMemberList(mainToken, groupID);
            if (l == null) {
                sendTextMessage("获取 " + listBean.getGroup_name() + "失败");
                continue;
            }
            sendTextMessage("获取 " + listBean.getGroup_name() + "的群成员列表成功，一共有 " + l.size() + " 个用户");
            allMembers.addAll(l);
        }
        sendTextMessage("，一共有 " + allMembers.size() + " 个用户");
        sendTextMessage("开始去重");
        List<MemberAddBean> list = LitePal.findAll(MemberAddBean.class);
        Log.e("---->", list.size() + "---");
        List<MemberAddBean> needAddlist = new ArrayList<>();
        for (GroupMemberListBean b : allMembers) {
//            for (MemberAddBean addBean : list) {
//                Log.e("---->", addBean.getNeedAddUserID() + "---"+b.getId());
//                if (addBean.getNeedAddUserID().equals(b.getId())) {
//                    //
//                    sendTextMessage(b.getNick() + "已经存在于数据库中");
//                    Log.e("---->", b.getNick() + "已经存在于数据库中");
//
//                    break;
//                }
//            }
            MemberAddBean memberAddBean = new MemberAddBean();
            memberAddBean.setAdd(false);
            memberAddBean.setAgree(false);
            memberAddBean.setNeedAddUserNick(b.getNick());
            memberAddBean.setNeedAddUserID(b.getId());
            memberAddBean.saveOrUpdate("needAddUserID=?",b.getId()+"");
            Log.e("---->", b.getNick() + "存储完成");
            sendTextMessage(b.getNick() + "存储完成");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    binding.btn.setText("拉取群成员列表,目前共有"+LitePal.findAll(MemberAddBean.class).size()+"个群成员已经获取到");
                }
            });
        }

        sendTextMessage("全部成员已经拉取完毕");
    }
}
