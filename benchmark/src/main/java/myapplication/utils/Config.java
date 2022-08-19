package myapplication.utils;

import org.litepal.LitePal;

import java.util.List;

import myapplication.bean.ConfigBean;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.16 15:07
 */
public class Config {

    public static ConfigBean getConfig() {
        List<ConfigBean> list = LitePal.where("configid=?", "1").find(ConfigBean.class);
        LogUtils.e("---->", list.toString());
        if (list != null && list.size() > 0) return list.get(0);
        return null;
    }
}
