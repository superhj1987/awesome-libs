package me.rowkey;

import me.rowkey.libs.spring.config.AwesomePropertiesPersister;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.PropertiesPersister;

import java.io.FileReader;
import java.util.Properties;

/**
 * Author: Bryant Hang
 * Date: 16/5/31
 * Time: 下午10:59
 */
public class AwesomeConfigTest {
    @Test
    public void test() throws Exception {
        PropertiesPersister persister = new AwesomePropertiesPersister();
        persister.load(new Properties(), new FileReader(new ClassPathResource("test.conf").getFile()));
    }
}
