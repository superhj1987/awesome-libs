package me.rowkey.libs.util;

/**
 * Author: Bryant
 * Date: 14/11/19
 * Time: 下午3:36
 */

import me.rowkey.libs.meta.http.ApplicationType;
import me.rowkey.libs.meta.http.ContentEncoding;
import me.rowkey.libs.meta.http.HttpResult;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

/**
 * 带连接池的HTTP
 */

public class HttpClientUtil {

    private static final String TAG_CHARSET = "charset=";

    private final static Logger logger = Logger.getLogger(HttpClientUtil.class);

    private static final int CONNECTION_TIMEOUT = 3000;// 连接超时时间
    private static final int SO_TIMEOUT = 5000;// 等待数据超时时间
    private PoolingClientConnectionManager pool = null;
    private int maxConnection = 32;
    private static final String DEFAULT_CHARSET = "UTF-8";
    private int conntimeout = CONNECTION_TIMEOUT;
    private int sotimeout = SO_TIMEOUT;
    private String reqCharset = DEFAULT_CHARSET;
    private String resCharset = DEFAULT_CHARSET;

    private static final String COMMON_USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36;Suishenyun/0.1";
    private static final String MOBILE_USER_AGENT = "Mozilla/5.0 (iPhone; CPU iPhone OS 7_0 like Mac OS X; en-us) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53;;Suishenyun/0.1";
    private String agentHeader = COMMON_USER_AGENT;

    public HttpClientUtil() {

    }

    public HttpClientUtil(int conntimeout, int sotimeout) {
        this.sotimeout = sotimeout;
        this.conntimeout = conntimeout;
    }

    public HttpClientUtil(int maxConnection, int conntimeout, int sotimeout) {
        this(conntimeout, sotimeout);
        this.maxConnection = maxConnection;
    }

    public HttpClientUtil(int maxConnection, String charset, int conntimeout, int sotimeout) {
        this(conntimeout, sotimeout);
        this.maxConnection = maxConnection;
        this.reqCharset = charset;
    }

    public HttpClientUtil(int maxConnection) {
        this.maxConnection = maxConnection;
    }

    public HttpClientUtil(int maxConnection, String charset) {
        this.maxConnection = maxConnection;
        this.reqCharset = charset;
    }

    public HttpClientUtil(int maxConnection, String charset, String resCharset) {
        this.maxConnection = maxConnection;
        this.reqCharset = charset;
        this.resCharset = resCharset;
    }

    private HttpParams getParams() {
        HttpParams params = new BasicHttpParams();
        params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        params.setParameter(CoreConnectionPNames.SO_TIMEOUT, sotimeout);
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, conntimeout);
        return params;
    }

    private HttpClient httpClient;

    public HttpClient getHttpClient() {
        if (httpClient == null) {
            synchronized (this) {
                if (httpClient == null) {
                    httpClient = new DefaultHttpClient(pool, getParams());
                    ((DefaultHttpClient) httpClient).setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {
                        public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                            HeaderElementIterator it = new BasicHeaderElementIterator(response
                                    .headerIterator(HTTP.CONN_KEEP_ALIVE));
                            while (it.hasNext()) {
                                HeaderElement he = it.nextElement();
                                String param = he.getName();
                                String value = he.getValue();
                                if (value != null && param.equalsIgnoreCase("timeout")) {
                                    try {
                                        return Long.parseLong(value) * 1000;
                                    } catch (NumberFormatException ignore) {
                                    }
                                }
                            }
                            // 否则保持活动5秒
                            return 5 * 1000;
                        }
                    });
                }
            }
        }
        return httpClient;
    }

    @PreDestroy
    public void destroy() throws Exception {
        logger.info("Http connection pool will destory...");
        if (pool != null) {
            pool.shutdown();
        }
        logger.info("Http connection pool destroyed!");
    }

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        schemeRegistry.register(new Scheme("https", 443, PlainSocketFactory.getSocketFactory()));
        pool = new PoolingClientConnectionManager(schemeRegistry);
        pool.setMaxTotal(maxConnection);
        pool.setDefaultMaxPerRoute(maxConnection);
    }

    /**
     * 直接返回字符
     *
     * @param url
     * @return
     * @throws java.io.IOException
     */
    public String getData(String url, List<NameValuePair> params) throws IOException {
        return getData(url, params, null);
    }

    public String getData(String url) throws IOException {
        return getData(url, Collections.EMPTY_LIST, null, false);
    }

    public String getData(String url, ApplicationType type) throws IOException {
        return getData(url, Collections.EMPTY_LIST, type, false);
    }

    public String getData(String url, List<NameValuePair> params, ApplicationType type) throws IOException {
        return getData(url, params, type, false);
    }

    /**
     * @param url
     * @param params
     * @param type
     * @param isGzip 是否使用gzip解压
     * @return
     * @throws java.io.IOException
     */
    public String getData(String url, List<NameValuePair> params, ApplicationType type, boolean isGzip) throws IOException {
        return fetchData(createGet(url, params, type));
    }

    public String putData(String url, List<NameValuePair> params) throws IOException {
        return putData(url, params, null, false);
    }

    public String putData(String url, List<NameValuePair> params, ApplicationType type) throws IOException {
        return putData(url, params, type, false);
    }

    /**
     * @param url
     * @param params
     * @param type
     * @param isGzip 是否使用gzip解压
     * @return
     * @throws java.io.IOException
     */
    public String putData(String url, List<NameValuePair> params, ApplicationType type, boolean isGzip) throws IOException {
        return fetchData(createPut(url, params, type));
    }

    public String deleteData(String url, List<NameValuePair> params) throws IOException {
        return deleteData(url, params, null, false);
    }

    public String deleteData(String url, List<NameValuePair> params, ApplicationType type) throws IOException {
        return deleteData(url, params, type, false);
    }

    /**
     * @param url
     * @param params
     * @param type
     * @param isGzip 是否使用gzip解压
     * @return
     * @throws java.io.IOException
     */
    public String deleteData(String url, List<NameValuePair> params, ApplicationType type, boolean isGzip) throws IOException {
        return fetchData(createDelete(url, params, type));
    }

    public String postData(String url, final HttpEntity entity, ApplicationType type) throws IOException {
        return postData(url, entity, type, false);
    }

    public String postData(String url, final HttpEntity entity) throws IOException {
        return postData(url, entity, null, false);
    }

    /**
     * @param url
     * @param entity
     * @param type
     * @param isGzip 是否使用gzip解压
     * @return
     * @throws java.io.IOException
     */
    public String postData(String url, final HttpEntity entity, ApplicationType type, boolean isGzip) throws IOException {
        return fetchData(this.createPost(url, entity, type));
    }

    public String postData(String url, final List<NameValuePair> params) throws IOException {
        return postData(url, params, null, false);
    }

    public String postData(String url, final List<NameValuePair> params, ApplicationType type) throws IOException {
        return postData(url, params, type, false);
    }

    /**
     * @param url
     * @param params
     * @param type
     * @param isGzip 是否使用gzip解压
     * @return
     * @throws java.io.IOException
     */
    public String postData(String url, final List<NameValuePair> params, ApplicationType type, boolean isGzip) throws IOException {
        return fetchData(this.createPost(url, params, type));
    }

    public HttpResult postDetailData(String url, final List<NameValuePair> params) throws IOException {
        return fetchDetailData(this.createPost(url, params, null));
    }

    public HttpResult getDetailData(String url, List<NameValuePair> params) throws IOException {
        return fetchDetailData(createGet(url, params, null));
    }

    public HttpClientUtil mobileClient() {
        this.agentHeader = MOBILE_USER_AGENT;
        return this;
    }

    public String fetchData(HttpRequestBase request) {
        HttpResult result = fetchDetailData(request);
        return result.getResult();
    }

    public HttpResult fetchDetailData(HttpRequestBase request) {
        HttpResult httpResult = null;
        if (request == null) {
            return new HttpResult(HttpStatus.SC_BAD_REQUEST, "null request");
        }
        HttpClient client = null;
        String result = null;
        long watch = System.nanoTime();
        try {
            client = getHttpClient();

            request.addHeader("User-Agent", agentHeader);
            this.agentHeader = COMMON_USER_AGENT;

            request.addHeader("Connection", "keep-alive");

            //            if (request.getURI().getScheme().contains("https")) {
//                try {
//                    SSLContext sslcontext = SSLContext.getInstance("TLS");
//                    sslcontext.init(new KeyManager[0], new TrustManager[]{new SimpleTrustManager()}, null);
//                    SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
//                    Scheme sch = new Scheme("https", 443, sf);
//                    client.getConnectionManager().getSchemeRegistry().register(sch);
//                } catch (Exception e) {
//                    logger.error(e, e);
//                }
//            }

            HttpResponse response = client.execute(request);
            HttpEntity rsentity = response.getEntity();
            if (rsentity.getContentEncoding() != null &&
                    rsentity.getContentEncoding().getValue().toLowerCase().contains(ContentEncoding.GZIP.getName())) {
                rsentity = new GzipDecompressingEntity(rsentity);
            }
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                Charset rescharset = ContentType.getOrDefault(rsentity).getCharset();
                if (rescharset != null && rescharset.name().equals(HTTP.DEFAULT_CONTENT_CHARSET)) {
                    result = EntityUtils.toString(rsentity);
                    if (resCharset == null) {
                        result = new String(result.getBytes(rescharset), DEFAULT_CHARSET);
                    } else {
                        result = new String(result.getBytes(rescharset), resCharset);
                    }
                } else {
                    if (resCharset == null) {
                        result = EntityUtils.toString(rsentity);
                    } else {
                        result = EntityUtils.toString(rsentity, resCharset);
                    }
                }
                if (logger.isDebugEnabled()) {
                    logger.debug(" fetch request " + result);
                }
                httpResult = new HttpResult(result);
            } else {
                logger.error("fetch request return error status:" + request.getURI()
                        + response.getStatusLine().getStatusCode());
                result = EntityUtils.toString(rsentity);
                httpResult = new HttpResult(response.getStatusLine().getStatusCode(), result);
            }
        } catch (ClientProtocolException e) {
            logger.error("fetch request error " + request.getURI() + " msg" + e.getMessage());
            httpResult = new HttpResult(HttpStatus.SC_BAD_REQUEST, e.getMessage());
        } catch (ParseException e) {
            logger.error("fetch request error " + request.getURI() + " msg" + e.getMessage());
            httpResult = new HttpResult(HttpStatus.SC_BAD_REQUEST, e.getMessage());
        } catch (IOException e) {
            logger.error("fetch request error " + request.getURI() + " msg" + e.getMessage());
            httpResult = new HttpResult(HttpStatus.SC_BAD_REQUEST, e.getMessage());
        } finally {

            request.releaseConnection();
            if (logger.isDebugEnabled())
                logger.debug("fetch url " + request.getURI() + ",consume: " + (System.nanoTime() - watch) / 1000);
        }
        return httpResult;
    }

    /**
     * 创建post请求
     * <p/>
     * <p/>
     * 路径
     *
     * @return 请求
     * @throws java.io.UnsupportedEncodingException
     */
    public HttpPost createPost(String url, final List<NameValuePair> params, ApplicationType type)
            throws UnsupportedEncodingException {
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, reqCharset);
        return createPost(url, entity, type);
    }

    public HttpPost createPost(String url, final List<NameValuePair> params) throws UnsupportedEncodingException {
        return createPost(url, params, null);
    }

    public HttpPost createPost(String url, HttpEntity entity) {
        return createPost(url, entity);
    }

    public HttpPost createPost(String url, HttpEntity entity, ApplicationType accept) {
        HttpPost method = new HttpPost(url);
        if (null != accept) {
            method.addHeader("accept", accept.val());
        }
        method.setEntity(entity);
        return method;
    }

    public HttpGet createGet(String url, final List<NameValuePair> params) throws IOException {
        return createGet(url, params, null);
    }

    public HttpGet createGet(String url, final List<NameValuePair> params, ApplicationType accept) throws IOException {
        HttpGet method = new HttpGet(urlEncode(url, params));
        if (null != accept) {
            method.addHeader("accept", accept.val());
        }
        return method;
    }

    private String urlEncode(String url, final List<NameValuePair> params) {
        if (params == null)
            return url;
        String param = URLEncodedUtils.format(params, reqCharset);
        if (url.indexOf("?") == -1) {
            url += "?" + param;
        } else {
            url += param;
        }
        return url;
    }

    public HttpPut createPut(String url, final List<NameValuePair> params, ApplicationType accept) throws IOException {
        HttpPut method = new HttpPut(url);
        if (params != null) {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, reqCharset);
            method.setEntity(entity);
        }
        if (null != accept) {
            method.addHeader("accept", accept.val());
        }
        return method;
    }

    public HttpPut createPut(String url, final List<NameValuePair> params) throws IOException {
        return createPut(url, params, null);
    }

    public HttpDelete createDelete(String url, final List<NameValuePair> params) throws IOException {
        return createDelete(url, params, null);
    }

    public HttpDelete createDelete(String url, final List<NameValuePair> params, ApplicationType accept)
            throws IOException {
        HttpDelete method = new HttpDelete(urlEncode(url, params));
        if (null != accept) {
            method.addHeader("accept", accept.val());
        }
        return method;
    }

    public String getAgentHeader() {
        return agentHeader;
    }

    public void setAgentHeader(String agentHeader) {
        this.agentHeader = agentHeader;
    }

}
