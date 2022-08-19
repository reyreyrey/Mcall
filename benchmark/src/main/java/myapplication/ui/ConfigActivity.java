package myapplication.ui;

import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import org.litepal.LitePal;

import myapplication.base.BaseActivity;
import myapplication.bean.ConfigBean;
import tgio.benchmark.R;
import tgio.benchmark.databinding.ActivityConfigBinding;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.16 14:36
 */
public class ConfigActivity extends BaseActivity<ActivityConfigBinding> {
    private ConfigBean configBean;

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
        configBean = LitePal.findFirst(ConfigBean.class);
        if (configBean == null) configBean = new ConfigBean();
        binding.edtRegNum.setText(configBean.getRegCount() + "");
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
            return;
        }
        if(groupAccount.equals("") || groupPwd.equals("")){
            new AlertDialog.Builder(context).setMessage("请配置有群的账号密码！！！").setNegativeButton("确定",null).create().show();
            return;
        }
        LitePal.deleteAll(ConfigBean.class);
        String regNum = binding.edtRegNum.getText().toString().trim();
        configBean.setRegCount(Integer.parseInt(regNum));
        configBean.setGroupAccount(groupAccount);
        configBean.setGroupJoniTime(groupJoniTime);
        configBean.setGroupPwd(groupPwd);
        configBean.setIpProxyUrl(ipProxyUrl);
        configBean.save();
        Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show();
        finish();
    }
}
