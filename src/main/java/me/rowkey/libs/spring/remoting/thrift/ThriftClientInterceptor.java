package me.rowkey.libs.spring.remoting.thrift;

import me.rowkey.libs.util.HttpClientUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.remoting.RemoteLookupFailureException;
import org.springframework.remoting.RemoteProxyFailureException;
import org.springframework.remoting.support.UrlBasedRemoteAccessor;

import java.lang.reflect.InvocationTargetException;

/**
 * Author: Bryant Hang
 * Date: 15/5/21
 * Time: 16:02
 */
public class ThriftClientInterceptor extends UrlBasedRemoteAccessor implements MethodInterceptor {
    private TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();

    private Object thriftProxy;

    HttpClientUtil httpClientUtil;

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        prepare();
    }

    /**
     * Initialize the Hessian proxy for this interceptor.
     *
     * @throws org.springframework.remoting.RemoteLookupFailureException if the service URL is invalid
     */
    public void prepare() throws RemoteLookupFailureException {
        if (httpClientUtil == null) {
            this.httpClientUtil = new HttpClientUtil();
        }

        try {
            thriftProxy = ThriftUtil.buildClient(getServiceInterface(), protocolFactory.getProtocol(getTransport()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if (this.thriftProxy == null) {
            throw new IllegalStateException("ThriftClientInterceptor is not properly initialized - " +
                    "invoke 'prepare' before attempting any operations");
        }

        ClassLoader originalClassLoader = overrideThreadContextClassLoader();
        try {
            return methodInvocation.getMethod().invoke(thriftProxy, methodInvocation.getArguments());
        } catch (InvocationTargetException e) {
            Throwable targetEx = e.getTargetException();
            if (targetEx instanceof InvocationTargetException) {
                targetEx = ((InvocationTargetException) targetEx).getTargetException();
            }
            if (targetEx instanceof TApplicationException && ((TApplicationException) targetEx).getType() == TApplicationException.MISSING_RESULT) {
                return null;
            } else {
                throw targetEx;
            }
        } catch (Throwable ex) {
            throw new RemoteProxyFailureException(
                    "Failed to invoke Thrift proxy for remote service [" + getServiceUrl() + "]", ex);
        } finally {
            resetThreadContextClassLoader(originalClassLoader);
        }
    }

    protected TTransport getTransport() throws TTransportException {
        return new THttpClient(getServiceUrl(), httpClientUtil.getHttpClient());
    }

    public void setHttpClientUtil(HttpClientUtil httpClientUtil) {
        this.httpClientUtil = httpClientUtil;
    }
}
