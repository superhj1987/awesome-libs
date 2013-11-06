package name.srhang.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM");
    private static SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy");

    static {
        /**
         * 设置lenient为false.
         */
        dateFormat1.setLenient(false);
        dateFormat2.setLenient(false);
        dateFormat3.setLenient(false);
    }

    public static boolean isValidDate(String s) {
        try {
            int length = s.length();
            if (length < 4)
                return false;
            else if (length == 4) {
                dateFormat3.parse(s);
            } else if (length == 6 || length == 7) {
                dateFormat2.parse(s);
            } else if (length == 8 || length == 9 || length == 10) {
                dateFormat1.parse(s);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * 下面这个方法则可以将一个日期按照你指定的格式输出
     *
     * @param d
     * @return
     */
    public static String formatDate(Date d) {
        return dateFormat1.format(d);
    }

    public static Date StringToDate(String s) {
        Date date = null;

        int length = s.length();

        try {
            if (length == 4) {
                date = dateFormat3.parse(s);

            } else if (length == 6 || length == 7) {
                date = dateFormat2.parse(s);

            } else if (length == 8 || length == 9 || length == 10) {
                date = dateFormat1.parse(s);

            }
        } catch (Exception e) {
            return date;
        }

        return date;
    }
}