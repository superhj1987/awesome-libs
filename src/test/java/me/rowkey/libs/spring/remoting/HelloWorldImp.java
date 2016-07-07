package me.rowkey.libs.spring.remoting;

import org.apache.thrift.TException;

/**
 * Author: Bryant Hang
 * Date: 15/5/22
 * Time: 10:05
 */
public class HelloWorldImp implements HelloWorldService.Iface {

    public HelloWorldImp() {
    }

    @Override
    public String sayHello(String username) throws TException {
        return "Hi," + username;
    }

}
