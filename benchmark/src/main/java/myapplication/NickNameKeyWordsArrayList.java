package myapplication;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

import myapplication.modules.groupMemberList.GroupMemberListBean;

public class NickNameKeyWordsArrayList extends ArrayList<GroupMemberListBean> {
     String [] keywords = {
            "财务","cw","CW","ZB","zb","主持","管理","机器人","亚泰","招财猫","财财","客服"
    };

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
