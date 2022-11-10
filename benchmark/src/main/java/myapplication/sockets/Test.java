package myapplication.sockets;

import static com.tencent.bugly.crashreport.crash.c.d;

import android.util.Base64;

import com.alibaba.fastjson.JSON;

import java.nio.charset.Charset;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import myapplication.modules.login.LoginBean;
import myapplication.utils.LogUtils;
import tgio.rncryptor.RNCryptorNative;

/**
 * @作者 ：xrz
 * @日期：2022.11.10 19:17
 * @简介：
 */
public class Test {

    public static String bindUser(String userId, String token, String deskey) {
        LogUtils.i("登录", "userId:" + userId + "   token:" + token);
        HashMap hashMap = new HashMap();
        hashMap.put("cmd", 5);
        hashMap.put("flags", 16);
        hashMap.put("sessionId", 2);
        HashMap hashMap2 = new HashMap();
        hashMap2.put("userId", "19051484MSSX3P4K_" + userId);
        HashMap hashMap3 = new HashMap();
        hashMap3.put("appKey", "19051484MSSX3P4K");
        hashMap3.put("token", token);
        hashMap3.put("userId", userId);
        hashMap2.put("data", JSON.toJSONString(hashMap3));
        hashMap.put("body", hashMap2);
        return encodeStr(JSON.toJSONString(hashMap), deskey);
    }


    public static String encodeStr(String str, String deskey) {
        String str2 = new RNCryptorNative().decrypt(deskey, "i6vn%aLPZYr9JStu");

        if (str2 == null) {
            str2 = "123456781234567812345678";
        }
        LogUtils.i("11","发送数据前加密：" + str);
        try {
            SecretKey generateSecret = SecretKeyFactory.getInstance("desede").generateSecret(new DESedeKeySpec(str2.getBytes()));
            Cipher cipher = Cipher.getInstance("desede/ECB/PKCS5Padding");
            cipher.init(1, generateSecret);
            return encode(cipher.doFinal(str.getBytes("UTF-8")));
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    //需要把configBean中的longurl解密，然后就可以建立websocket链接了
    public static String decodeStr(String str, String deskey) {
        try {
            String str2 = new RNCryptorNative().decrypt(deskey, "i6vn%aLPZYr9JStu");
            if (str2 == null) {
                str2 = "123456781234567812345678";
            }
            byte[] decode = decode(str);
            SecretKey generateSecret = SecretKeyFactory.getInstance("desede").generateSecret(new DESedeKeySpec(str2.getBytes()));
            Cipher cipher = Cipher.getInstance("desede/ECB/PKCS5Padding");
            cipher.init(2, generateSecret);
            return new String(cipher.doFinal(decode), "UTF-8");
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }


    public static String encode(byte[] bArr) {
        return new String(Base64.encode(bArr, 2), Charset.forName("UTF-8"));
    }
    public static byte[] decode(String str) {
        if (str != null) {
            return Base64.decode(str.getBytes(Charset.forName("UTF-8")), 2);
        }
        return null;
    }




}
