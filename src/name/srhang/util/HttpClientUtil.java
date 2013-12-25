package name.srhang.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * HTTP请求工具类
 */
public class HttpClientUtil {

    /**
     * 从网络url中获取inputstream，支持 https
     *
     * @param url
     * @return
     */
    private static HttpEntity getEntityFromUrl(String url) {
        HttpEntity entity = null;

        try {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[]{new YxTrustManage()}, null);
            SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
            Scheme sch = new Scheme("https", 443, sf);

            HttpGet request = new HttpGet(url);
            HttpClient httpClient = new DefaultHttpClient();
            httpClient.getConnectionManager().getSchemeRegistry().register(sch);
            entity = httpClient.execute(request).getEntity();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return entity;
    }


    /**
     * 读取url生成下载文件， url支持https链接
     *
     * @param response
     * @param url
     * @param fileName
     */
    public static void renderDownloadFromUrl(HttpServletResponse response, String url, String fileName) {
        InputStream is = null;
        ServletOutputStream sos = null;
        try {
            HttpEntity entity = getEntityFromUrl(url);
            is = entity.getContent();

            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentLength((int) entity.getContentLength());
            sos = response.getOutputStream();
            byte[] buffer = new byte[8192];
            int count = 0;
            while ((count = is.read(buffer)) > 0) {
                sos.write(buffer, 0, count);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (sos != null) {
                    sos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
