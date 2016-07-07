package me.rowkey.libs.spring.remoting.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.support.RemoteExporter;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Author: Bryant Hang
 * Date: 15/5/21
 * Time: 15:03
 */
public class ThriftServiceExporter extends RemoteExporter
        implements HttpRequestHandler {
    Logger LOGGER = LoggerFactory.getLogger(ThriftServiceExporter.class);
    private TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!"POST".equals(request.getMethod())) {
            throw new HttpRequestMethodNotSupportedException(request.getMethod(),
                    new String[]{"POST"}, "ThriftServiceExporter only supports POST requests");
        }

        InputStream in = request.getInputStream();
        OutputStream out = response.getOutputStream();
        try {
            ThriftContextHolder.init();
            response.setContentType("application/x-thrift");
            TTransport transport = new TIOStreamTransport(in, out);

            TProtocol protocol = getProtocolFactory().getProtocol(transport);
            TProcessor processor = ThriftUtil.buildProcessor(getServiceInterface(), getProxyForService());
            processor.process(protocol, protocol);
        } catch (Throwable e) {
            response.setContentType("text/plain; charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace(new PrintWriter(out, true));
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("Thrift server direct error", e);
            }
        } finally {
            ThriftContextHolder.reset();
        }
    }

    public TProtocolFactory getProtocolFactory() {
        return protocolFactory;
    }

    public void setProtocolFactory(TProtocolFactory protocolFactory) {
        this.protocolFactory = protocolFactory;
    }
}
