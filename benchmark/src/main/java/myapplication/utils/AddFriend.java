package myapplication.utils;

import android.util.Log;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;
import java.util.ListIterator;

import myapplication.modules.login.LoginBean;
import myapplication.modules.login.LoginRequest;
import myapplication.modules.searchUser.SearchUserBean;

public class AddFriend {

    public static void addFriend(LoginRequest request) {
        //注册的用户列表
        List<LoginBean> regs = LitePal.findAll(LoginBean.class);
        Log.e("---->", "->注册的用户列表+"+regs.size());
        //搜索到的用户列表
        List<SearchUserBean> searchUserBeans = LitePal.where("isAdded = ?", "0").find(SearchUserBean.class);
        Log.e("---->", "->搜索到的用户列表+"+searchUserBeans.size());
        if(searchUserBeans == null || searchUserBeans.size()<1000){
            Log.e("---->", "->开始获取列表+");
            GetAllUserList.getAllUser(request, regs.get(0).getToken());
            searchUserBeans = LitePal.where("isAdded = ?", "0").find(SearchUserBean.class);
        }
        ListIterator<SearchUserBean> listIterator = searchUserBeans.listIterator();
        for(LoginBean bean : regs){
            if(searchUserBeans == null || searchUserBeans.size()<1000){
                GetAllUserList.getAllUser(request, bean.getToken());
                searchUserBeans = LitePal.where("isAdded = ?", "0").find(SearchUserBean.class);
                listIterator = searchUserBeans.listIterator();
            }
            for(int i=0;i<5;i++){
                add(listIterator, request, bean.getToken());
            }
        }
    }

    static void add(ListIterator<SearchUserBean> listIterator, LoginRequest request, String token){

        SearchUserBean searchUserBean = listIterator.next();
        Log.e("---->", "->开始添加+"+searchUserBean.getNickname());
        boolean flag = request.addFriend(token, searchUserBean.getUserid()+"");
        Log.e("---->", "->"+(flag ? "添加成功" : "添加失败"));
        if(!flag){
            add(listIterator, request, token);
        }
        searchUserBean.setAdded(true);
        searchUserBean.save();
        Log.e("---->", "->保存成功");
    }
}
