package myapplication;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

import myapplication.bean.ConfigBean;
import myapplication.modules.groupMemberList.GroupMemberListBean;
import myapplication.utils.Config;

public class NickNameKeyWordsArrayList extends ArrayList<GroupMemberListBean> {
     String [] keywords = {
            "财务","cw","CW","ZB","zb","主持","管理","机器人","亚泰","招财猫","财财","客服"
    };

    public NickNameKeyWordsArrayList() {
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

    @Override
    public boolean addAll(@NonNull Collection<? extends GroupMemberListBean> c) {
        for(GroupMemberListBean bean : c){
            if(!isConstant(bean.getNick())){
                add(bean);
                Log.e("---->","添加调："+bean.getNick());

            }else{
                Log.e("---->","过滤调："+bean.getNick());
            }
        }
        return true;
    }
}
