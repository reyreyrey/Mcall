package myapplication.utils;

import org.litepal.LitePal;

import myapplication.bean.ConfigBean;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.16 15:07
 */
public class Config {

    public static ConfigBean getConfig(){
        return LitePal.findFirst(ConfigBean.class);
    }
}
