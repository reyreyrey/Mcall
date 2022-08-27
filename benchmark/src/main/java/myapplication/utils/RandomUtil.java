package myapplication.utils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.20 15:50
 */
public class RandomUtil {
    private static final String SYMBOLS = "abcdefghijklmnopqrstuvwxyz"; // 数字和26个字母组成
    private static final String SYMBOLS1 = "0123456789";
    private static final Random RANDOM = new SecureRandom();
    public static void main(String[] args) {
        System.out.println(getRandomchar()+getRandomnum());
    }

    public static String getRandomString(){
        return getRandomchar()+getRandomnum();
    }

    /**
     * 获取长度为 6 的随机字母+数字
     * @return 随机数字
     */
    public static String getRandomchar() {
        char[] nonceChars = new char[3];  //指定长度为6位/自己可以要求设置

        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }
    public static String getRandomchar2() {
        char[] nonceChars = new char[2];  //指定长度为6位/自己可以要求设置

        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }

    public static String getRandomchar1() {
        char[] nonceChars = new char[1];  //指定长度为6位/自己可以要求设置

        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }

    public static String getRandomchar5() {
        char[] nonceChars = new char[5];  //指定长度为6位/自己可以要求设置

        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }

    public static String getRandomnum() {
        char[] nonceChars = new char[3];  //指定长度为6位/自己可以要求设置

        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS1.charAt(RANDOM.nextInt(SYMBOLS1.length()));
        }
        return new String(nonceChars);
    }

    public static String getRandomnum2() {
        char[] nonceChars = new char[2];  //指定长度为6位/自己可以要求设置

        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS1.charAt(RANDOM.nextInt(SYMBOLS1.length()));
        }
        return new String(nonceChars);
    }

    public static String getRandomnum36() {
        char[] nonceChars = new char[getRandomLenth()];  //指定长度为6位/自己可以要求设置

        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS1.charAt(RANDOM.nextInt(SYMBOLS1.length()));
        }
        return new String(nonceChars);
    }

    public static int getRandomLenth() {
        char[] nonceChars = new char[1];  //指定长度为6位/自己可以要求设置

        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = "3456".charAt(RANDOM.nextInt("3456".length()));
        }
        String s = new String(nonceChars);
        return Integer.parseInt(s);
    }


}
