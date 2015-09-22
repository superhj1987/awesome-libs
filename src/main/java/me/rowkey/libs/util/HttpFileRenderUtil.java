package me.rowkey.libs.util;


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
import java.io.*;

/**
 * Author: Bryant Hang
 * Date: 15/1/21
 * Time: 下午2:30
 *
 * 生成文件的http下载链接
 */
public class HttpFileRenderUtil {
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
            sslcontext.init(null, new TrustManager[]{new SimpleTrustManager()}, null);
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
        try {
            HttpEntity entity = getEntityFromUrl(url);
            is = entity.getContent();

            renderDownloadFromInputStream(response, is, fileName, (int) entity.getContentLength());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 读取文件生成下载文件
     *
     * @param response
     * @param file
     */
    public static void renderDownloadFromFile(HttpServletResponse response, File file) {
        if (!file.exists()) {
            return;
        }
        try {
            renderDownloadFromInputStream(response, new FileInputStream(file), file.getName(), (int) file.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取inputstream生成下载文件
     *
     * @param response
     * @param is
     * @param fileName
     * @param contentLength
     */
    public static void renderDownloadFromInputStream(HttpServletResponse response, InputStream is, String fileName, int contentLength) {
        ServletOutputStream sos = null;
        try {
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentLength(contentLength);
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
                if (sos != null) {
                    sos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
