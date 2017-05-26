package me.rowkey.libs;

import junit.framework.Assert;
import me.rowkey.libs.support.Maps;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Bryant.Hang on 2017/5/26.
 */
public class MapsTest {
    @Test
    public void testFromMap() throws Exception {
        Map<String,String> map = Maps.newHashMap();
        map.put("name","superhj1987");

        User u  = Maps.fromMap(map,User.class);

        Assert.assertTrue(u.getName().equals("superhj1987"));
    }

    @Test
    public void testToMap() throws Exception {
        User u  = new User();
        u.setName("superhj1987");

        Map<?, ?> map = Maps.toMap(u);
        Assert.assertTrue(map.get("name").equals("superhj1987"));
    }
}

class User{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
