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

import java.util.List;

@Configuration
@Profile("prod") // Solo ejecuta esto cuando se active el perfil 'prod'
public class InitPasswordEncryptor {

    private static final Logger log = LoggerFactory.getLogger(InitPasswordEncryptor.class);

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public InitPasswordEncryptor(UsuarioRepository usuarioRepository,
                                 PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public ApplicationRunner initEncryptPasswords() {
        return args -> {
            try {
                log.info("🔐 Verificando contraseñas sin encriptar...");

                List<Usuario> usuarios = usuarioRepository.findAll();
                int contador = 0;

                for (Usuario usuario : usuarios) {
                    String password = usuario.getContrasena();

                    if (password != null && !password.startsWith("$2a$")) {
                        String encodedPassword = passwordEncoder.encode(password);
                        usuario.setContrasena(encodedPassword);
                        usuarioRepository.save(usuario);
                        contador++;
                        log.debug("➡️ Contraseña encriptada para usuario: {}", usuario.getCuenta());
                    }
                }

                log.info("✅ {} contraseñas procesadas y encriptadas", contador);

            } catch (Exception e) {
                log.error("❌ Error en la encriptación de contraseñas: {}", e.getMessage(), e);
                // No relanzar la excepción para evitar detener el arranque de la app
            }
        };
    }
}
