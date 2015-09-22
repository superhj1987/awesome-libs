package me.rowkey.libs.meta.http;

/**
 * Author: Bryant Hang
 * Date: 14/12/30
 * Time: 下午8:21
 */
public interface RespConstant {
    public static final String XML_ANNOUNCEMENT = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" ;

    public static final String XML_RESP_OPEN = "<resp>" ;
    public static final String XML_RESP_CLOSE = "</resp>" ;

    public static final String XML_HEAD_OPEN = "<head>" ;
    public static final String XML_HEAD_CLOSE = "</head>" ;

    public static final String XML_STATUS_OPEN = "<status>" ;
    public static final String XML_STATUS_CLOSE = "</status>" ;

    public static final String XML_DESC_OPEN = "<desc><![CDATA[" ;
    public static final String XML_DESC_CLOSE = "]]></desc>" ;

    public static final String XML_PAGE_OPEN = "<page>" ;
    public static final String XML_PAGE_CLOSE = "</page>" ;

    public static final String XML_TOTAL_OPEN = "<total>" ;
    public static final String XML_TOTAL_CLOSE = "</total>" ;

    public static final String XML_DATA_OPEN = "<data>" ;
    public static final String XML_DATA_CLOSE = "</data>" ;

    public static final String XML_PIDX_OPEN = "<pidx>" ;
    public static final String XML_PIDX_CLOSE = "</pidx>" ;

    public static final StringBuffer JSON_ERROR = new StringBuffer("{\"desc\":\"build json failed\",\"status\":1007}") ;
    public static final StringBuilder ERROR_JSON = new StringBuilder("{\"desc\":\"build json failed\",\"status\":1007}") ;

    public static final String JSON_STATUS = "status" ;
    public static final String JSON_DESC = "desc" ;
    public static final String JSON_PAGE = "page" ;
    public static final String JSON_TOTAL = "total" ;
    public static final String JSON_DATA = "data" ;
    public static final String JSON_EXTEND_DATA = "extend_data" ;
    public static final String JSON_PIDX = "pidx" ;
    public static final String JSON_VERSION = "version" ;
    public static final String LAST_TIME_STAMP = "lastTimeStamp" ;//时间分页的返回
}
