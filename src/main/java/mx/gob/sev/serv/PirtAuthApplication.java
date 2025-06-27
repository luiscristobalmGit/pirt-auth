package mx.gob.sev.serv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan({
    "mx.gob.sev.serv.model",
    "mx.gob.sev.serv.dto"
})
@EnableJpaRepositories(basePackages = "mx.gob.sev.serv.repository")
@ComponentScan(basePackages = {
    "mx.gob.sev.serv.config",
    "mx.gob.sev.serv.controller",
    "mx.gob.sev.serv.service",
    "mx.gob.sev.serv.util",
    "mx.gob.sev.serv.filter"
})
public class PirtAuthApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PirtAuthApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(PirtAuthApplication.class, args);
    }
}