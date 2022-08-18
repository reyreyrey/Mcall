package myapplication.utils;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by 发 on 2017/4/25.
 */

public class LogUtils {

    private static final String TAG = LogUtils.class.getSimpleName();

    private static String logFilePath;// 日志文件保存路径
    public static String crashLogFilePath;// 崩溃日志文件保存路径
    private static boolean showLog = true;// 是否显示日志
    private static boolean saveLog = true;// 是否保存日志
    private static boolean addTag = true;// 日志是否统一添加tag
    private static String logTag = "<Project>  ";// 日志过滤Tag

    // 日志打印类型
    // DEBUG
    private static final int TYPE_D = 1;
    // INFO
    private static final int TYPE_I = 2;
    // WARN
    private static final int TYPE_W = 3;
    // ERROR
    private static final int TYPE_E = 4;

    public static void v(String tag, String s) {
        if (showLog) {
            cutAndLogMsg(TYPE_D, tag, s);
        }
    }

    public static class Config {
        public Config logFilePath(String logFilePath) {
            LogUtils.logFilePath = logFilePath;
            return this;
        }

        public Config crashLogFilePath(String crashLogFilePath) {
            LogUtils.crashLogFilePath = crashLogFilePath;
            return this;
        }

        public Config showLog(boolean showLog) {
            LogUtils.showLog = showLog;
            return this;
        }

        public Config saveLog(boolean saveLog) {
            LogUtils.saveLog = saveLog;
            return this;
        }

        public Config addTag(boolean addTag) {
            LogUtils.addTag = addTag;
            return this;
        }

        public Config logTag(String logTag) {
            LogUtils.logTag = "<" + logTag + "> ";
            return this;
        }
    }

    // 获取添加标志之后的日志信息
    private static String getMsg(String msg) {
        if (addTag) {
            msg = logTag + msg;
        }
        return msg;
    }

    public static void d(String tag, String msg) {
        if (showLog) {
            cutAndLogMsg(TYPE_D, tag, msg);
        }
    }

    public static void d(String tag, boolean msg) {
        if (showLog) {
            cutAndLogMsg(TYPE_D, tag, String.valueOf(msg));
        }
    }

    public static void d(String tag, String msg, Throwable t) {
        if (showLog) {
            cutAndLogMsg(TYPE_D, tag, msg, t);
        }
    }

    public static void i(String tag, String msg) {
        if (showLog) {
            cutAndLogMsg(TYPE_I, tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable t) {
        if (showLog) {
            cutAndLogMsg(TYPE_I, tag, msg, t);
        }
    }

    public static void w(String tag, String msg) {
        if (showLog) {
            cutAndLogMsg(TYPE_W, tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable t) {
        if (showLog) {
            cutAndLogMsg(TYPE_W, tag, msg, t);
        }
    }

    public static void e(String tag, String msg) {
        if (showLog) {
            cutAndLogMsg(TYPE_E, tag, msg);
        }
    }

    public static void e(String tag, boolean msg) {
        if (showLog) {
            cutAndLogMsg(TYPE_E, tag, String.valueOf(msg));
        }
    }

    public static void e(String tag, String msg, Throwable t) {
        if (showLog) {
            cutAndLogMsg(TYPE_E, tag, msg, t);
        }
    }



    /**
     * 日志信息写入文件
     */
    public static void logToFile(final File logFile, final String tag, final String text) {
        new Thread(() -> {
            FileWriter filerWriter = null;
            BufferedWriter bufWriter = null;
            try {
                if (logFile != null) {
                    String needWriteMessage = "\n\n\n\n" + formatCurrentDate() + "    " + tag + "    " + text;
                    filerWriter = new FileWriter(logFile, true);// 后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
                    bufWriter = new BufferedWriter(filerWriter);
                    bufWriter.write(needWriteMessage);
                    bufWriter.newLine();
                }
            } catch (IOException e) {
                e(TAG, "日志写入文件失败" + "\n" + e.getCause() + "\n" + e.getMessage());
            } finally {
                try {
                    if (bufWriter != null) {
                        bufWriter.close();
                    }
                    if (filerWriter != null) {
                        filerWriter.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 获得日志File
     */
    public static File getLogFile(String filePath, String fileName) {
        // 创建目录
        File dir = new File(filePath);
        if (!dir.exists()) {
            boolean result = dir.mkdirs();
            i(TAG, "创建日志文件结果：" + result);
        }
        // 打开文件
        return new File(filePath, fileName);
    }

    /**
     * 时间戳转换成日期格式字符串
     */
    private static String formatCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取崩溃日志文件名称
     */
    public static String getCrashLogName() {
        return "crash_" + formatCurrentDate() + ".log";
    }


    /**
     * 日志长度过长进行切割
     */
    private static void cutAndLogMsg(int type, String tag, String msg) {
        cutAndLogMsg(type, tag, msg, null);
    }


    /**
     * 日志长度过长进行切割
     */
    private static void cutAndLogMsg(int type, String tag, String msg, Throwable exception) {
        // 限制长度(安卓限制单条日志长度 4*1024)

        int maxLen = 4000;
        for (int i = 0, len = msg.length(); i * maxLen < len; ++i) {
            String logContent = msg.substring(i * maxLen, Math.min((i + 1) * maxLen, len));
            logMsgByType(type, tag, logContent, exception);
        }
    }

    /**
     * 根据类型打印日志
     */
    private static void logMsgByType(int type, String tag, String msg, Throwable exception) {
        if (type == TYPE_D) {
            android.util.Log.d(tag, getMsg(msg), exception);
            return;
        }
        if (type == TYPE_I) {
            android.util.Log.i(tag, getMsg(msg), exception);
            return;
        }
        if (type == TYPE_W) {
            android.util.Log.w(tag, getMsg(msg), exception);
            return;
        }
        if (type == TYPE_E) {
            android.util.Log.e(tag, getMsg(msg), exception);
        }
    }
}
