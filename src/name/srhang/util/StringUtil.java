package name.srhang.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * 字符串相关功能函数
 *
 * @author Bryant Hang
 */
public class StringUtil {

    /**
     * 转义单引号
     *
     * @param text
     * @return
     */
    public static String disposeQuote(String text) {
        int i = text.indexOf("'");
        if (i < 0)
            return text;

        // 对单引号进行转义
        StringBuffer sb = new StringBuffer(text);
        do {
            sb.insert(i, '\'');
            i = sb.indexOf("'", i + 2);
        } while (i >= 0);

        return sb.toString();

    }

    /**
     * 截取字符串
     *
     * @param str      : source string
     * @param width    : string's byte width
     * @param ellipsis : a string added to abbreviate string bottom
     * @return String Object
     */
    public static String abbreviate(String str, int width, String ellipsis) {
        if (str == null || "".equals(str)) {
            return "";
        }

        int d = 0; // byte length
        int n = 0; // char length
        for (; n < str.length(); n++) {
            d = (int) str.charAt(n) > 256 ? d + 2 : d + 1;
            if (d > width) {
                break;
            }
        }

        if (d > width) {
            n = n - ellipsis.length() / 2;
            return str.substring(0, n > 0 ? n : 0) + ellipsis;
        }

        return str.substring(0, n);
    }

    /**
     * 过滤html代码
     *
     * @param inputString
     * @return
     */
    public static String Html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        Pattern p_script;
        java.util.regex.Matcher m_script;
        Pattern p_style;
        java.util.regex.Matcher m_style;
        Pattern p_html;
        java.util.regex.Matcher m_html;

        Pattern p_html1;
        java.util.regex.Matcher m_html1;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            // }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            // }
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            String regEx_html1 = "<[^>]+";
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
            m_html1 = p_html1.matcher(htmlStr);
            htmlStr = m_html1.replaceAll(""); // 过滤html标签

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }

        return textStr;
    }

    /**
     * 字符串数组连接
     *
     * @param strArray
     * @param separator
     * @return
     */
    public static String stringArrayJoin(String[] strArray, String separator) {
        if (strArray == null || strArray.length == 0) {
            return null;
        }

        if (strArray.length == 1) {
            return strArray[0];
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strArray.length; i++) {
            String s = strArray[i] == null ? "" : strArray[i].trim();
            if (s.length() == 0) {
                continue;
            }
            sb.append(separator + s);
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(0);
            return sb.toString();
        } else {
            return null;
        }
    }

    /**
     * 字符串List连接
     *
     * @param strList
     * @param separator
     * @return
     */
    public static String stringListJoin(List<String> strList, String separator) {
        if (strList == null || strList.isEmpty()) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        for (Iterator<String> it = strList.iterator(); it.hasNext(); ) {
            String s = it.next();
            if (s == null || s.trim().equals(""))
                continue;
            sb.append(" " + separator + " " + s);
        }

        return sb.length() > 0 ? sb.substring(separator.length() + 2) : null;
    }

    /**
     * MD5 加密
     *
     * @param str
     * @return
     */
    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();
    }

    public static final String ALPHA_BASE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBER_BASE = "0123456789";

    /**
     * 生成一定长度的随机字符串,多用于生成验证码
     *
     * @param length
     * @param type
     * @return
     */
    public static String generateRandomCode(int length, RandomCodeType type) {
        String base = ALPHA_BASE;
        if (type.equals(RandomCodeType.NUMBER)) {
            base = NUMBER_BASE;
        } else if (type.equals(RandomCodeType.ALPHA_NUMBER)) {
            base = ALPHA_BASE + NUMBER_BASE;
        }

        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(base.charAt(random.nextInt(base.length())));
        }

        return sb.toString();
    }

}
