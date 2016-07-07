package me.rowkey.libs.spring.remoting.thrift;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * Author: Bryant Hang
 * Date: 15/5/21
 * Time: 15:43
 */
public class ThriftProxyFactoryBean extends ThriftClientInterceptor implements FactoryBean<Object> {
    private Object serviceProxy;


    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        this.serviceProxy = new ProxyFactory(getServiceInterface(), this).getProxy(getBeanClassLoader());
    }

    @Override
    public Object getObject() throws Exception {
        return this.serviceProxy;
    }

    @Override
    public Class<?> getObjectType() {
        return getServiceInterface();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
