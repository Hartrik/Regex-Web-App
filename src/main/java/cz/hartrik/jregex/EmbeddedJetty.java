package cz.hartrik.jregex;

import java.io.IOException;

import cz.hartrik.jregex.config.BaseConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 *
 * @version 2017-02-07
 * @author Patrik Harag
 */
public class EmbeddedJetty {

    private static final int DEFAULT_PORT = 5000;

    private static final String CONTEXT_PATH = "/";
    private static final String MAPPING_URL = "/";
    private static final String WEBAPP_DIRECTORY = "webapp";

    public static void init() throws Exception {
        String envPort = System.getenv("PORT");
        int port = (envPort != null)
                ? Integer.valueOf(envPort)
                : DEFAULT_PORT;

        new EmbeddedJetty().startJetty(port);
    }

    private void startJetty(int port) throws Exception {
        Server server = new Server(port);

        server.setHandler(getServletContextHandler());

        addRuntimeShutdownHook(server);

        server.start();
        server.join();
    }

    private static ServletContextHandler getServletContextHandler() throws IOException {
        WebApplicationContext webAppContext = getWebApplicationContext();
        DispatcherServlet dispatcherServlet = new DispatcherServlet(webAppContext);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);  // handling 404
        ServletHolder springServletHolder = new ServletHolder("mvc-dispatcher", dispatcherServlet);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setErrorHandler(null);
        contextHandler.setResourceBase(new ClassPathResource(WEBAPP_DIRECTORY).getURI().toString());
        contextHandler.setContextPath(CONTEXT_PATH);
        contextHandler.addServlet(springServletHolder, MAPPING_URL);
        contextHandler.addEventListener(new ContextLoaderListener(webAppContext));

        return contextHandler;
    }

    private static void addRuntimeShutdownHook(Server server) {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (server.isStarted()) {
                server.setStopAtShutdown(true);
                try {
                    server.stop();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }));
	}

    private static WebApplicationContext getWebApplicationContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(BaseConfig.class);
        return context;
    }

}