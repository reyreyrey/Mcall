package myapplication.bean;

import org.litepal.crud.LitePalSupport;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.16 14:34
 */
public class ConfigBean extends LitePalSupport {

    private int regCount;

    public int getRegCount() {
        return regCount;
    }

    public void setRegCount(int regCount) {
        this.regCount = regCount;
    }
}
