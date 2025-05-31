package mx.gob.sev.serv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Usamos fuerza 12 para mejor seguridad (valor por defecto es 10)
        return new BCryptPasswordEncoder(12);
    }
}