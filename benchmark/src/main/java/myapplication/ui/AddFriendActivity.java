package myapplication.ui;

import android.util.Log;
import android.view.View;

import org.litepal.LitePal;

import java.util.List;
import java.util.ListIterator;

import myapplication.base.BaseActivity;
import myapplication.base.BaseMessageActivity;
import myapplication.bean.MemberAddBean;
import myapplication.modules.login.LoginBean;
import myapplication.modules.login.LoginRequest;
import myapplication.modules.proxy.IPProxy;
import myapplication.modules.searchUser.SearchUserBean;
import myapplication.utils.AddFriend;
import myapplication.utils.GetAllUserList;
import tgio.benchmark.R;
import tgio.benchmark.databinding.ActivityAddFriendBinding;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.16 16:26
 */
public class AddFriendActivity extends BaseMessageActivity<ActivityAddFriendBinding> {
    private LoginRequest request;
    private boolean isRun = false;

    @Override
    protected String getTitleStr() {
        return "添加好友";
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_add_friend;
    }

    @Override
    protected void init() {
        request = new LoginRequest(this);
    }


    public void addGroup(View v) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                if (isRun) return;
                isRun = true;
                addFriend();
                isRun = false;
                sendTextMessage("添加好友完成");
                sendDialogMessage("添加好友完成");
            }
        }.start();
    }

    public void addSearch(View v){
        new Thread() {
            @Override
            public void run() {
                super.run();
                if (isRun) return;
                isRun = true;
                addFriendBySearch();
                isRun = false;
                sendTextMessage("添加好友完成");
                sendDialogMessage("添加好友完成");
            }
        }.start();
    }

    void addFriendBySearch() {
        //注册的用户列表
        List<LoginBean> regs = LitePal.findAll(LoginBean.class);
        sendTextMessage("注册的用户列表+" + regs.size());
        //搜索到的用户列表
        List<SearchUserBean> searchUserBeans = LitePal.where("isAdded = ?", "0").find(SearchUserBean.class);
        sendTextMessage("未添加的用户列表+" + searchUserBeans.size());
        if (searchUserBeans == null || searchUserBeans.size() < 0) {
            sendDialogMessage("未添加的用户少于10条，请先返回，获取用户");
            return;
        }
        ListIterator<SearchUserBean> listIterator = searchUserBeans.listIterator();
        for (LoginBean bean : regs) {
            sendTextMessage("开始设置代理");
            IPProxy.setProxy(this);
            sendTextMessage("代理设置成功");
            sendTextMessage("开始登录账号");
            LoginBean loginBean = request.login(bean.getUsername(), "666888aa..", bean.getDeviceid(), bean.getClientid());
            if (loginBean == null) {
                sendTextMessage("账号登录失败");
                continue;
            }
            sendTextMessage("账号登录成功");
            for (int i = 0; i < 5; i++) {
                addByDearch(listIterator, loginBean.getToken());
            }
        }
    }

    void addFriend() {
        //注册的用户列表
        List<LoginBean> regs = LitePal.findAll(LoginBean.class);
        sendTextMessage("注册的用户列表+" + regs.size());
        //搜索到的用户列表
        List<MemberAddBean> searchUserBeans = LitePal.where("isAdd = ?", "0").find(MemberAddBean.class);
        sendTextMessage("未添加的群成员列表+" + searchUserBeans.size());
        if (searchUserBeans == null || searchUserBeans.size() < 0) {
            sendDialogMessage("未添加的群成员少于10条，请先返回，获取群成员");
            return;
        }
        ListIterator<MemberAddBean> listIterator = searchUserBeans.listIterator();
        for (LoginBean bean : regs) {
            sendTextMessage("开始设置代理");
            IPProxy.setProxy(this);
            sendTextMessage("代理设置成功");
            sendTextMessage("开始登录账号");
            LoginBean loginBean = request.login(bean.getUsername(), "666888aa..", bean.getDeviceid(), bean.getClientid());
            if (loginBean == null) {
                sendTextMessage("账号登录失败");
                continue;
            }
            sendTextMessage("账号登录成功");
            for (int i = 0; i < 5; i++) {
                add(listIterator, loginBean.getToken());
            }
        }
    }

    void add(ListIterator<MemberAddBean> listIterator, String token) {

        MemberAddBean searchUserBean = listIterator.next();
        sendTextMessage("->开始添加+" + searchUserBean.getNeedAddUserNick());
        boolean flag = request.addFriend(token, searchUserBean.getMineUserID() + "");
        sendTextMessage("->" + (flag ? "添加成功" : "添加失败"));
        if (flag) {
            searchUserBean.setAdd(true);
            searchUserBean.saveOrUpdate();
            sendTextMessage("->保存成功");
        }

    }

    void addByDearch(ListIterator<SearchUserBean> listIterator, String token) {

        SearchUserBean searchUserBean = listIterator.next();
        sendTextMessage("->开始添加+" + searchUserBean.getNickname());
        boolean flag = request.addFriend(token, searchUserBean.getUserid() + "");
        sendTextMessage("->" + (flag ? "添加成功" : "添加失败"));
        if (flag) {
            searchUserBean.setAdded(true);
            searchUserBean.saveOrUpdate();
            sendTextMessage("->保存成功");
        }

    }
}
