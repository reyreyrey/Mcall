package myapplication.ui;

import android.view.View;

import myapplication.base.BaseActivity;
import myapplication.utils.AddFriend;
import tgio.benchmark.R;
import tgio.benchmark.databinding.ActivityAddFriendBinding;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.16 16:26
 */
public class AddFriendActivity extends BaseActivity<ActivityAddFriendBinding> {
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

    }

    public void search(View V){}

    public void add(View v){
    }
}
