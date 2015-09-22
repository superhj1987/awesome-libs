package me.rowkey.libs.meta.http;

/**
 * Author: Bryant
 * Date: 14/11/19
 * Time: 上午10:53
 */
public enum ApplicationType {

    JSON("application/json"), XML("application/xml"), TEXT("text/xml"), FORM("application/x-www-form-urlencoded");

    private String type;

    private ApplicationType(String type) {
        this.type = type;
    }

    public String val() {
        return type;
    }

}
