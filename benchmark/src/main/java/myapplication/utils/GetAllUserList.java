package myapplication.utils;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import myapplication.modules.login.LoginRequest;
import myapplication.modules.searchUser.SearchUserBean;

public class GetAllUserList {

    public static void getAllUser(LoginRequest request, String currentToken) {
        SearchUserBean last = LitePal.findLast(SearchUserBean.class);
        int last_userid = Config.getConfig().getSearchLastUserId();
        if (last != null) {
            Log.e("---->", "->null");
            last_userid = last.getUserid();
        }
        EventBus.getDefault().post("clear");
        int count = Config.getConfig().getSearchLastUserCount();
        for (int id = last_userid; id < last_userid + count; id++) {
            Log.e("---->", "->开始获取列表+"+id);
            EventBus.getDefault().post("开始获取列表"+id);
            SearchUserBean searchUserBean = request.getUserByID(id + "", currentToken);
            if (searchUserBean == null) continue;
//            Date date = new Date(searchUserBean.getLast_login_time()*1000);
//            if(date.before(getThreeDaysAgoDate())){
//                Log.e("---->", searchUserBean.getNickname()+"->太久不登陆，最后登陆时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
//                continue;
//            }

            boolean f = searchUserBean.save();
            EventBus.getDefault().post(f ? searchUserBean.getNickname() + "->已经添加进数据库" : searchUserBean.getNickname() + "添加失败");
            Log.e("---->", f ? searchUserBean.getNickname() + "->已经添加进数据库" : searchUserBean.getNickname() + "添加失败");
        }
//        LitePal.saveAll(list);
        EventBus.getDefault().post("结束");
    }

    public static Date getThreeDaysAgoDate() {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 3);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return endDate;
    }
}
