package myapplication.utils;

import android.util.Log;

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
        int last_userid = 1001;
        if (last != null) {
            last_userid = last.getUserid();
        }

        for (int id = last_userid; id < last_userid + 2000; id++) {
            Log.e("---->", "->开始获取列表+"+id);
            SearchUserBean searchUserBean = request.getUserByID(id + "", currentToken);
            if (searchUserBean == null) continue;
//            Date date = new Date(searchUserBean.getLast_login_time()*1000);
//            if(date.before(getThreeDaysAgoDate())){
//                Log.e("---->", searchUserBean.getNickname()+"->太久不登陆，最后登陆时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
//                continue;
//            }

            boolean f = searchUserBean.save();
            Log.e("---->", f ? searchUserBean.getNickname() + "->已经添加进数据库" : searchUserBean.getNickname() + "添加失败");
        }
//        LitePal.saveAll(list);
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
