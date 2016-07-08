package me.rowkey.libs.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Calendar;

/**
 * Author: Bryant Hang Date: 15/2/5 Time: 下午8:33
 */
public class DateUtil {

    public static final String DATE_FORMAT_RFC_1123_2_YEAR = "EEE, dd MMM yy HH:mm:ss z"; /* RFC 1123 with 2-digit Year */
    public static final String DATE_FORMAT_RFC_1123_4_YEAR = "EEE, dd MMM yyyy HH:mm:ss z"; /* RFC 1123 with 4-digit Year */
    public static final String DATE_FORMAT_RFC_1123_NO_TIMEZONE = "EEE, dd MMM yy HH:mm:ss"; /* RFC 1123 with no Timezone */
    public static final String DATE_FORMAT_RFC_1123_VARIANT_1 = "EEE, MMM dd yy HH:mm:ss"; /* Variant of RFC 1123 */
    public static final String DATE_FORMAT_RFC_1123_NO_SECOND = "EEE, dd MMM yy HH:mm z";  /* RFC 1123 with no Seconds */
    public static final String DATE_FORMAT_RFC_1123_VARIANT_2 = "EEE dd MMM yyyy HH:mm:ss"; /* Variant of RFC 1123 */
    public static final String DATE_FORMAT_RFC_1123_NO_WEEKDAY = "dd MMM yy HH:mm:ss z"; /* RFC 1123 with no Day */
    public static final String DATE_FORMAT_RFC_1123_NO_WEEKDAY_SECOND = "dd MMM yy HH:mm z"; /* RFC 1123 with no Day or Seconds */
    public static final String DATE_FORMAT_RFC_1123_NO_WEEKDAY_4_YEAR = "dd MMM yyyy HH:mm:ss z"; /* RFC 1123 without Day Name */
    public static final String DATE_FORMAT_RFC_1123_NO_WEEKDAY_SECOND_4_YEAR = "dd MMM yyyy HH:mm z"; /* RFC 1123 without Day Name and Seconds */
    public static final String DATE_FORMAT_ISO_8601_MODE_1 = "yyyy-MM-dd'T'HH:mm:ssZ"; /* ISO 8601 slightly modified */
    public static final String DATE_FORMAT_ISO_8601_MODE_2 = "yyyy-MM-dd'T'HH:mm:ss'Z'"; /* ISO 8601 slightly modified */
    public static final String DATE_FORMAT_ISO_8601_MODE_3 = "yyyy-MM-dd'T'HH:mm:sszzzz"; /* ISO 8601 slightly modified */
    public static final String DATE_FORMAT_ISO_8601_MODE_4 = "yyyy-MM-dd'T'HH:mm:ss z"; /* ISO 8601 slightly modified */
    public static final String DATE_FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ssz"; /* ISO 8601 */
    public static final String DATE_FORMAT_ISO_8601_MODE_5 = "yyyy-MM-dd'T'HH:mm:ss.SSSz"; /* ISO 8601 slightly modified */
    public static final String DATE_FORMAT_ISO_8601_MODE_6 = "yyyy-MM-dd'T'HHmmss.SSSz"; /* ISO 8601 slightly modified */
    public static final String DATE_FORMAT_ISO_8601_MODE_7 = "yyyy-MM-dd'T'HH:mm:ss"; /* ISO 8601 slightly modified */
    public static final String DATE_FORMAT_ISO_8601_MODE_8 = "yyyy-MM-dd'T'HH:mmZ"; /* ISO 8601 w/o seconds */
    public static final String DATE_FORMAT_ISO_8601_MODE9 = "yyyy-MM-dd'T'HH:mm'Z'"; /* ISO 8601 w/o seconds */
    public static final String DATE_FORMAT_SIMPLE = "yyyy-MM-dd"; /* Simple Date Format */
    public static final String DATE_FORMAT_SIMPLE_2 = "MMM dd, yyyy"; /* Simple Date Format */
    public static final String DATE_FORMAT_RFC_822_4_YEAR = "EEE, dd MMM yyyy HH:mm:ss Z";
    public static final String DATE_FORMAT_RFC_822_2_YEAR = "EEE, dd MMM yy HH:mm:ss Z";

    /**
     * 年-格式化
     */
    public static final String YEAR_MONTH = "yyyy-MM";

    /**
     * 年 格式化
     */
    public static final String YEAR_MONTH_WITHOUT_SEPERATOR = "yyyyMM";

    /**
     * 日期 格式化:yyyy-MM-dd
     */
    public static final String DATE = "yyyy-MM-dd";

    /**
     * 日期 格式化:yyyyMMdd
     */
    public static final String DATE_WITHOUT_SEPERATOR = "yyyyMMdd";

    /**
     * 日期-小时 格式化
     */
    public static final String DATE_HOUR = "yyyyMMddHH";

    /**
     * 日期小时 格式化
     */
    public static final String DATE_SPACE_HOUR = "yyyy-MM-dd HH";

    /**
     * 日期小时分钟 格式化
     */
    public static final String DATE_SPACE_MINUTE = "yyyy-MM-dd HH:mm";

    /**
     * 日期时间 格式化
     */
    public static final String TIMESTAMP = "yyyy-MM-dd HH:mm:ss";

    /**
     * 当前小时
     *
     * @return
     */
    public static String currentHour() {
        return DateFormatUtils.format(System.currentTimeMillis(), DATE_HOUR);
    }

    /**
     * 今天
     *
     * @return
     */
    public static String today() {
        return DateFormatUtils.format(System.currentTimeMillis(), DATE_WITHOUT_SEPERATOR);
    }

    /**
     * 当前月
     *
     * @return
     */
    public static String currentMonth() {
        return DateFormatUtils.format(System.currentTimeMillis(), YEAR_MONTH_WITHOUT_SEPERATOR);
    }

    /**
     * 今天最早的时间
     *
     * @return
     */
    public static long getTodayStart() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime().getTime();
    }

    /**
     * 今天的最晚点
     *
     * @return
     */
    public static long getTodayEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime().getTime();
    }

    /**
     * 本月第一天
     *
     * @return
     */
    public static long getCurrentMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime().getTime();
    }

    /**
     * 获取本周一
     *
     * @return
     */
    public static long getCurrentMonday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return calendar.getTime().getTime();
    }
}
