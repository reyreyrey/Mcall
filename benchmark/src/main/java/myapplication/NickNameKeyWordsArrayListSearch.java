package myapplication;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

import myapplication.bean.ConfigBean;
import myapplication.modules.groupMemberList.GroupMemberListBean;
import myapplication.modules.searchUser.SearchUserBean;
import myapplication.utils.Config;

public class NickNameKeyWordsArrayListSearch extends ArrayList<SearchUserBean> {
     String [] keywords = {
            "财务","cw","CW","ZB","zb","主持","管理","机器人","亚泰","招财猫","财财","客服"
    };

    public NickNameKeyWordsArrayListSearch() {
        ConfigBean configBean = Config.getConfig();
        String keyWords = configBean.getNickNameKeyWords();
        if(!TextUtils.isEmpty(keyWords) && keyWords.contains(",")){
            this.keywords = keyWords.split(",");
        }

    }

    private boolean isConstant(String nickname){
        for(String key : keywords){
            if(nickname.contains(key))return true;
        }
        return false;
    }

    public boolean addList(SearchUserBean groupMemberListBean) {
        if(!isConstant(groupMemberListBean.getNickname())){
            Log.e("---->","添加调："+groupMemberListBean.getNickname());
            add(groupMemberListBean);
            return true;

        }else{
            Log.e("---->","过滤调："+groupMemberListBean.getNickname());
            return false;
        }
    }


}
