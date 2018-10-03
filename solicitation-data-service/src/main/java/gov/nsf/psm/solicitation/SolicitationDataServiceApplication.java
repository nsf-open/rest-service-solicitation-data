package gov.nsf.psm.solicitation;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class SolicitationDataServiceApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SolicitationDataServiceApplication.class);
    }

    public static void main(String[] args) {
        setEmbeddedContainerEnvironmentProperties();
        SpringApplication.run(SolicitationDataServiceApplication.class, args);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        setExternalContainerEnvironmentProperties();
        super.onStartup(servletContext);
    }

    private static void setEmbeddedContainerEnvironmentProperties() {
        setEnvironmentProperties();
        System.setProperty("server.context-path", "/solicitation-data-service");
    }

    private static void setExternalContainerEnvironmentProperties() {
        setEnvironmentProperties();
    }

    private static void setEnvironmentProperties() {
        System.setProperty("spring.config.name", "solicitation-data-service");
    }
}
