package me.rowkey.libs.spring.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * Author: Bryant
 * Date: 14/11/20
 * Time: 上午10:09
 */
public class AwesomePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    public AwesomePropertyPlaceholderConfigurer() {
        super();
        setPropertiesPersister(new AwesomePropertiesPersister());
    }
}
