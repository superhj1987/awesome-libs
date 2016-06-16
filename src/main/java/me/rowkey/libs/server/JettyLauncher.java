package me.rowkey.libs.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.net.URL;

/**
 * Author: Bryant Hang
 * Date: 16/6/16
 * Time: 下午9:31
 */
public class JettyLauncher {

    private static final Logger logger = LoggerFactory.getLogger(JettyLauncher.class);

    private String host = null;
    private int port = 8080;
    private String contextPath = "/";

    public void launchWebapp(String[] args) throws Exception {
        Server server = initServer(args);

        WebAppContext context = new WebAppContext();
        URL location = JettyLauncher.class.getResource("/");

        context.setContextPath(this.contextPath);
        context.setDescriptor(location.toExternalForm() + "/WEB-INF/web.xml");
        context.setServer(server);
        context.setWar(location.toExternalForm());

        launch(server, context);
    }

    public void launchSpringMvcApp(String[] args, WebApplicationContext context) throws Exception {
        launchSpringMvcApp(args, context, "/*");
    }

    public void launchSpringMvcApp(String[] args, WebApplicationContext context, String mappingUrl) throws Exception {
        Server server = initServer(args);

        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setHandler(null);
        contextHandler.setContextPath(this.contextPath);
        contextHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), mappingUrl);
        contextHandler.addEventListener(new ContextLoaderListener(context));

        launch(server, contextHandler);
    }

    public static WebApplicationContext annotationContext(String configLocation) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(configLocation);
        return context;
    }

    private void launch(Server server, Handler handler) throws Exception {
        server.setHandler(handler);
        server.start();
        logger.info("Jetty Server started at port {}", this.port);
        server.join();
    }

    private Server initServer(String args[]) {
        for (String arg : args) {
            if (arg.startsWith("--") && arg.contains("=")) {
                String[] dim = arg.split("=");
                if (dim.length >= 2) {
                    if (dim[0].equals("--host")) {
                        host = dim[1];
                    } else if (dim[0].equals("--port")) {
                        port = Integer.parseInt(dim[1]);
                    } else if (dim[0].equals("--prefix")) {
                        contextPath = dim[1];
                    }
                }
            }
        }

        Server server = new Server();

        ServerConnector connector = new ServerConnector(server);
        if (host != null) {
            connector.setHost(host);
        }
        connector.setIdleTimeout(1000 * 60 * 60);
        connector.setSoLingerTime(-1);
        connector.setPort(port);

        server.addConnector(connector);

        return server;
    }
}
