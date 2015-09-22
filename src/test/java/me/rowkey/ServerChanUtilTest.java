package me.rowkey;

import junit.framework.Assert;
import me.rowkey.libs.util.ServerChanUtil;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;

public class ServerChanUtilTest {
    @Test
    public void testPrimitive() throws ParseException, IOException {
        Assert.assertTrue(new ServerChanUtil("XXXX").sendMsg("test","test"));
    }
}
