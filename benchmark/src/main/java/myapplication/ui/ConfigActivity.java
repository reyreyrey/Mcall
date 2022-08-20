package myapplication.ui;

import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import org.litepal.LitePal;

import myapplication.base.BaseActivity;
import myapplication.bean.ConfigBean;
import myapplication.utils.Config;
import myapplication.utils.LogUtils;
import tgio.benchmark.R;
import tgio.benchmark.databinding.ActivityConfigBinding;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.16 14:36
 */
public class ConfigActivity extends BaseActivity<ActivityConfigBinding> {
    private ConfigBean configBean;
    String  keywords =
            "财务,cw,CW,ZB,zb,主持,管理,机器人,亚泰,招财猫,财财,客服";

    @Override
    protected String getTitleStr() {
        return "配置";
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_config;
    }

    @Override
    protected void init() {
        configBean = Config.getConfig();
        LogUtils.e("---->", configBean.toString());
//
        binding.edtRegNum.setText(configBean.getRegCount() + "");
        binding.edtGroupAccount.setText(configBean.getGroupAccount());
        binding.edtGroupPwd.setText(configBean.getGroupPwd());
        binding.edtGroupJoniTime.setText(configBean.getGroupJoniTime()+"");
        binding.edtIpProxyUrl.setText(configBean.getIpProxyUrl());
        binding.edtNicknameKeyword.setText(configBean.getNickNameKeyWords());
        binding.edtGroupId.setText(configBean.getMainGroupId());
        binding.edtSearchUserStartId.setText(configBean.getSearchLastUserId()+"");
        binding.edtMainGroupAccount.setText(configBean.getMainGroupAccount());
        binding.edtMainGroupPwd.setText(configBean.getMainGroupPwd());
        binding.edtSearchUserCount.setText(configBean.getSearchLastUserCount()+"");
    }

    public void save(View v) {
        String groupAccount = binding.edtGroupAccount.getText().toString().trim();
        String groupPwd = binding.edtGroupPwd.getText().toString().trim();
        String time = binding.edtGroupJoniTime.getText().toString().trim();
        if(time.equals("")){
            new AlertDialog.Builder(context).setMessage("请配置轮询时间！！！").setNegativeButton("确定",null).create().show();
            return;
        }
        int groupJoniTime = Integer.parseInt(time);
        String ipProxyUrl = binding.edtIpProxyUrl.getText().toString().trim();
        if(ipProxyUrl.equals("")){
            new AlertDialog.Builder(context).setMessage("请配置IP代理，否则有可能被封号！！！").setNegativeButton("确定",null).create().show();
        }
        if(groupAccount.equals("") || groupPwd.equals("")){
            new AlertDialog.Builder(context).setMessage("请配置有群的账号密码！！！").setNegativeButton("确定",null).create().show();
            return;
        }
        String regNum = binding.edtRegNum.getText().toString().trim();
        String keyWords = binding.edtNicknameKeyword.getText().toString().trim();
        String mainGroupID = binding.edtGroupId.getText().toString().trim();
        String mainGroupAccount = binding.edtMainGroupAccount.getText().toString().trim();
        String mainGroupPwd = binding.edtMainGroupPwd.getText().toString().trim();

        configBean.setRegCount(Integer.parseInt(regNum));
        configBean.setConfigid(1);
        configBean.setMainGroupAccount(mainGroupAccount);
        configBean.setMainGroupPwd(mainGroupPwd);
        configBean.setMainGroupId(mainGroupID);
        configBean.setNickNameKeyWords(keyWords);
        configBean.setGroupAccount(groupAccount);
        configBean.setGroupJoniTime(groupJoniTime);
        configBean.setGroupPwd(groupPwd);
        configBean.setIpProxyUrl(ipProxyUrl);
        configBean.setSearchLastUserId(Integer.parseInt(binding.edtSearchUserStartId.getText().toString().trim()));
        configBean.setSearchLastUserCount(Integer.parseInt(binding.edtSearchUserCount.getText().toString().trim()));
        boolean flag = configBean.save();
        Toast.makeText(this, flag?"保存成功":"保存失败", Toast.LENGTH_LONG).show();
        if(flag)finish();
    }
}
