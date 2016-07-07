package me.rowkey.libs.spring.remoting;

import me.rowkey.libs.spring.remoting.thrift.ThriftUtil;
import org.junit.Test;

/**
 * Author: Bryant Hang
 * Date: 15/5/22
 * Time: 10:04
 */
public class ThriftUtilTest {
    @Test
    public void testBuildProcessor() throws Exception {
        ThriftUtil.buildProcessor(HelloWorldService.Iface.class, new HelloWorldImp());
    }

    @Test()
    public void testBuildClient() throws Exception {
//        ThriftUtil.buildClient(HelloWorldService.Iface.class, new TBinaryProtocol.Factory().getProtocol(
//                new THttpClient("", new HttpClientUtil().getHttpClient())));
    }
}
