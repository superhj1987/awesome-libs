package me.rowkey.libs.spring.core.databinder;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.web.bind.ServletRequestDataBinder;

/**
 * Author: Bryant Hang
 * Date: 15/1/17
 * Time: 下午6:32
 */
public interface SuishenExtendDataBinder {
    public void doExtendBind(MutablePropertyValues mpvs, ServletRequestDataBinder dataBinder);
}
