package mx.gob.sev.serv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "mx.gob.sev.serv")
public class PirtAuthApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PirtAuthApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(PirtAuthApplication.class, args);
    }
}