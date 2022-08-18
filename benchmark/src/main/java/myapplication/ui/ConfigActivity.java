package myapplication.ui;

import android.view.View;
import android.widget.Toast;

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
        binding.edtRegNum.setText(configBean.getRegCount()+"");
    }

    public void save(View v) {

        String regNum = binding.edtRegNum.getText().toString().trim();
        configBean.setRegCount(Integer.parseInt(regNum));
        configBean.save();
        Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show();
        finish();
    }
}
