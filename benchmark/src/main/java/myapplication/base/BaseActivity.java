package myapplication.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;

import tgio.benchmark.R;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.16 14:12
 */
public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {
    protected ProgressDialog dialog;
    protected Toolbar toolbar;
    protected T binding;
    protected FragmentActivity context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        binding = DataBindingUtil.setContentView(this, getLayoutID());
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitleStr());
        toolbar.setNavigationIcon(showBack() ? getResources().getDrawable(R.mipmap.back) : null);
        toolbar.setNavigationOnClickListener(v -> finish());
        dialog = new ProgressDialog(this);
        dialog.setMessage("loading....");
        init();
    }



    protected boolean showBack() {
        return true;
    }

    protected abstract String getTitleStr();

    protected abstract int getLayoutID();

    protected abstract void init();
}
