package mx.gob.sev.serv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "mx.gob.sev.serv")
public class PirtAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(PirtAuthApplication.class, args);
    }
}