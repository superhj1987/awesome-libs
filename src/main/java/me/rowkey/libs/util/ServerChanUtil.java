package me.rowkey.libs.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Author: Bryant Hang
 * Date: 15/9/22
 * Time: 14:50
 */
public class ServerChanUtil {
    private static HttpClientUtil httpClientUtil = null;

    private static final String SEND_URL = "http://sc.ftqq.com/%s.send";

    private String sckey;

    public ServerChanUtil(String sckey) {
        Preconditions.checkArgument(StringUtils.isNotBlank(sckey));
        this.sckey = sckey;
    }

    public boolean sendMsg(String text, String desp) throws IOException {
        if (httpClientUtil == null) {
            synchronized (ServerChanUtil.class) {
                if (httpClientUtil == null) {
                    httpClientUtil = new HttpClientUtil();
                }
            }
        }

        String url = String.format(SEND_URL, sckey);

        List<NameValuePair> params = Lists.newArrayList();
        params.add(new BasicNameValuePair("text", text));
        params.add(new BasicNameValuePair("desp", desp));

        String result = httpClientUtil.postData(url, params);

        if (StringUtils.isBlank(result)) {
            return false;
        }

        JSONObject jo = null;
        int errno = -1;

        try {
            jo = new JSONObject(result);
            errno = jo.getInt("errno");
        } catch (JSONException e) {
            return false;
        }

        if (jo == null) {
            return false;
        }

        return errno == 0;
    }
}
