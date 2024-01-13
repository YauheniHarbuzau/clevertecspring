package ru.clevertec.clevertecspring.util;

import lombok.experimental.UtilityClass;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ru.clevertec.clevertecspring.config.WebConfig;

import java.io.IOException;
import java.nio.file.Files;

/**
 * Утилитарный класс для запуска сервера Apache Tomcat
 */
@UtilityClass
public class TomcatStarter {

    public void start() throws IOException, LifecycleException {
        var tomcat = new Tomcat();
        tomcat.setConnector(getConnector());

        var context = getContext(tomcat);
        var appContext = getAppContext();

        Tomcat.addServlet(context, "dispatcherServlet", getDispatcherServlet(appContext)).setLoadOnStartup(1);
        context.addServletMappingDecoded("/*", "dispatcherServlet");

        tomcat.start();
        tomcat.getServer().await();
    }

    private Connector getConnector() {
        var connector = new Connector();
        connector.setPort(8080);
        connector.setScheme("http");
        connector.setSecure(false);
        return connector;
    }

    private Context getContext(Tomcat tomcat) throws IOException {
        var baseDir = Files.createTempDirectory("embedded-tomcat").toFile();
        return tomcat.addWebapp("", baseDir.getAbsolutePath());
    }

    private AnnotationConfigWebApplicationContext getAppContext() {
        var appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(WebConfig.class);
        return appContext;
    }

    private DispatcherServlet getDispatcherServlet(AnnotationConfigWebApplicationContext appContext) {
        return new DispatcherServlet(appContext);
    }
}
