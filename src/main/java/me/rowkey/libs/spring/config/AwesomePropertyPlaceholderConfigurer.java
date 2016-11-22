package me.rowkey.libs.spring.config;

import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Author: Bryant
 * Date: 14/11/20
 * Time: 上午10:09
 */
public class AwesomePropertyPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer {
    public AwesomePropertyPlaceholderConfigurer() {
        super();
        setPropertiesPersister(new AwesomePropertiesPersister());
    }
}
