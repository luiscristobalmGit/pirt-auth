package mx.gob.sev.serv.config;

import mx.gob.sev.serv.model.Usuario;
import mx.gob.sev.serv.repository.UsuarioRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
public class InitPasswordEncryptor {

    private static final Logger log = LoggerFactory.getLogger(InitPasswordEncryptor.class);

    @Bean
    @Profile("prod")
    @Transactional
    public ApplicationRunner initEncryptPasswords(
        UsuarioRepository usuarioRepository,
        PasswordEncoder passwordEncoder
    ) {
        return args -> {
            try {
                log.info("🔐 Verificando contraseñas sin encriptar...");
                List<Usuario> usuarios = usuarioRepository.findAll();
                int contador = 0;

                for (Usuario usuario : usuarios) {
                    String password = usuario.getContrasena();
                    if (password != null && !password.startsWith("$2a$")) {
                        usuario.setContrasena(passwordEncoder.encode(password));
                        usuarioRepository.save(usuario);
                        contador++;
                        log.debug("➡️ Contraseña encriptada para usuario: {}", usuario.getCuenta());
                    }
                }
                log.info("✅ {} contraseñas procesadas y encriptadas", contador);
            } catch (Exception e) {
                log.error("❌ Error en la encriptación: {}", e.getMessage(), e);
            }
        };
    }
}