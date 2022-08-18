package myapplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import tgio.rncryptor.RNCryptorNative;

public class Test {
    private static final char[] f28172b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public static final String e(byte[] bytes) {
        char[] cArr = new char[bytes.length << 1];
        int i2 = 0;
        for (int i3 = 0; i3 < bytes.length; i3++) {
            int i4 = i2 + 1;
            cArr[i2] = f28172b[(bytes[i3] >>> 4) & 15];
            i2 = i4 + 1;
            cArr[i4] = f28172b[bytes[i3] & 15];
        }
        return new String(cArr);
    }

    public static String getTimestamp() {
        return System.currentTimeMillis() + "";
    }

    public static String getNonce() {
        return ((new Random().nextInt(999999) % 900000) + 100000) + "";
    }

    private static final byte[] q(byte[] bArr, String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(str);
            messageDigest.update(bArr);
            byte[] digest = messageDigest.digest();
            return digest;
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return new byte[0];
        }
    }

    public static final byte[] x(byte[] bArr) {
        return q(bArr, "MD5");
    }

    public static final String E( byte[] bArr) {
        return e(x(bArr));
    }
    static RNCryptorNative cryptor = new RNCryptorNative();
    public static String getSignature(String str, String str2) {
        try {
            return E((str + str2 + "" + cryptor.decrypt("AwFV6M5nuSXnUqJr2af\\/yXl+UVkf\\/FV0l9NvpIS0fbTV6HIGbELFAmXyv1EBE+xaoA18uvjU5V8OpALXcxLawjIgcH70G4wRKPUQLDQQeMnsabvC2gkO918egwAxbXvT2gA=", "aLPZYr9JStu")).getBytes()).toLowerCase();
        } catch (Exception unused) {
            return "";
        }
    }

}
