package me.rowkey.libs.util;

/**
 * Created by Bryant.Hang on 16/8/12.
 */
public class JavaUtil {
    /***
     * 获取当前代码的行数
     *
     * @return
     */
    public static int getLineNumber() {
        StackTraceElement[] ste = new Throwable().getStackTrace();
        return ste[1].getLineNumber();
    }
}
