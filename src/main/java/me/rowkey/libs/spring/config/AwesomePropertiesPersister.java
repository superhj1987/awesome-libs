package me.rowkey.libs.spring.config;

import com.google.common.collect.Maps;
import com.typesafe.config.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.PropertiesPersister;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * Author: Bryant Hang
 * Date: 16/5/31
 * Time: 16:20
 * <p/>
 * Support HOCON; JSON; Java Properties; Yaml
 * <p/>
 * <p/>
 * using in spring
 * <p/>
 * <bean id="propertyConfigurer"
 * class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
 * <property name="propertiesPersister" ref="persister"/>
 * </bean>
 * <p/>
 * <bean class="suishen.libs.spring.config.AwesomePropertiesPersister"/>
 */
public class AwesomePropertiesPersister implements PropertiesPersister {
    @Override
    public void load(Properties props, InputStream is) throws IOException {
        load(props, new InputStreamReader(is));
    }

    @Override
    public void load(Properties props, Reader reader) throws IOException {
        String configContent = IOUtils.toString(reader);

        Map<String, Object> propMap = null;

        if (configContent.contains("=") && !configContent.contains("{")) { // java properties
            propMap = getTypeSafeConfigMap(configContent, ConfigParseOptions.defaults().setSyntax(ConfigSyntax.PROPERTIES));
        } else if (configContent.contains("=") && configContent.contains("{")) {  // hocon .conf
            propMap = getTypeSafeConfigMap(configContent, ConfigParseOptions.defaults().setSyntax(ConfigSyntax.CONF));
        } else if (configContent.contains("{")) {  // json
            propMap = getTypeSafeConfigMap(configContent, ConfigParseOptions.defaults().setSyntax(ConfigSyntax.JSON));
        } else { // yaml
            propMap = (Map<String, Object>) new Yaml().load(configContent);
        }

        assignProperties(props, propMap, null);
    }

    private Map<String, Object> getTypeSafeConfigMap(String configContent, ConfigParseOptions configParseOptions) {
        Config config = ConfigFactory.parseString(configContent, configParseOptions);

        Map<String, Object> propMap = Maps.newHashMap();
        for (Map.Entry<String, ConfigValue> entry : config.entrySet()) {
            String key = entry.getKey();
            ConfigValue val = entry.getValue();
            propMap.put(key, val.unwrapped());
        }

        return propMap;
    }

    /**
     * @param props
     * @param map
     */
    private void assignProperties(Properties props, Map<String, Object> map, String path) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            if (StringUtils.isNotEmpty(path))
                key = path + "." + key;
            Object val = entry.getValue();
            if (!(val instanceof Map)) {
                // see if we need to create a compound key
                props.put(key, val);
            } else {
                assignProperties(props, (Map<String, Object>) val, key);
            }
        }
    }

    @Override
    public void store(Properties props, OutputStream os, String header) throws IOException {
        throw new NotImplementedException("Current implementation is a read-only");
    }

    @Override
    public void store(Properties props, Writer writer, String header) throws IOException {
        throw new NotImplementedException("Current implementation is a read-only");
    }

    @Override
    public void loadFromXml(Properties props, InputStream is) throws IOException {
        throw new NotImplementedException("Use DefaultPropertiesPersister if you want to read/write XML");
    }

    @Override
    public void storeToXml(Properties props, OutputStream os, String header) throws IOException {
        throw new NotImplementedException("Use DefaultPropertiesPersister if you want to load/store to XML");
    }

    @Override
    public void storeToXml(Properties props, OutputStream os, String header, String encoding) throws IOException {
        throw new NotImplementedException("Use DefaultPropertiesPersister if you want to read/write XML");
    }
}
