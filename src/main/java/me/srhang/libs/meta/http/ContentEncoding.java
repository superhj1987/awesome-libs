package me.srhang.libs.meta.http;

/**
 * Author: Bryant Hang
 * Date: 3/19/15
 * Time: 7:36 PM
 */
public enum ContentEncoding {
    GZIP("gzip","gzip"),
    DEFLATE("deflate","deflate"),
    COMPRESS("compress","compress");

    private String name;
    private String value;

    private ContentEncoding(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
