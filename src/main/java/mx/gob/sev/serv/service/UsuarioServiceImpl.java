package mx.gob.sev.serv.service;

import mx.gob.sev.serv.dto.UsuarioHashDTO;
import mx.gob.sev.serv.model.Usuario;
import mx.gob.sev.serv.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, 
                            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioHashDTO validarCredenciales(String cuenta, String contrasena) {
        if (cuenta == null || cuenta.trim().isEmpty()) {
            return new UsuarioHashDTO(400, "Cuenta no proporcionada");
        }

        if (contrasena == null || contrasena.trim().isEmpty()) {
            return new UsuarioHashDTO(400, "Contraseña no proporcionada");
        }

        try {
            Optional<UsuarioHashDTO> resultado = usuarioRepository.obtenerHashPorCuenta(cuenta.trim());
            
            if (resultado.isEmpty()) {
                logger.warn("Cuenta no encontrada: {}", cuenta);
                return new UsuarioHashDTO(404, "Credenciales inválidas");
            }

            UsuarioHashDTO usuario = resultado.get();

            if (usuario.getUsuarioValido() != 1) {
                logger.warn("Usuario inactivo: {}", cuenta);
                return new UsuarioHashDTO(403, "Usuario inactivo");
            }

            // Verificar contraseña
            boolean coincide;
            if (usuario.getEsBCrypt() == 1) {
                coincide = passwordEncoder.matches(contrasena, usuario.getHash());
            } else {
                coincide = contrasena.equals(usuario.getHash());
                // Migrar automáticamente si la contraseña coincide y no es BCrypt
                if (coincide) {
                    migrarContrasena(cuenta, contrasena);
                }
            }

            if (!coincide) {
                logger.warn("Contraseña incorrecta para cuenta: {}", cuenta);
                return new UsuarioHashDTO(401, "Credenciales inválidas");
            }

            logger.info("Autenticación exitosa para cuenta: {}", cuenta);
            return usuario;

        } catch (Exception e) {
            logger.error("Error validando credenciales para {}: {}", cuenta, e.getMessage());
            return new UsuarioHashDTO(500, "Error en el servidor");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioHashDTO obtenerDetallesUsuario(String cuenta) {
        if (cuenta == null || cuenta.trim().isEmpty()) {
            return new UsuarioHashDTO(400, "Cuenta no proporcionada");
        }

        try {
            Optional<UsuarioHashDTO> resultado = usuarioRepository.obtenerHashPorCuenta(cuenta.trim());
            return resultado.orElse(new UsuarioHashDTO(404, "Usuario no encontrado"));
        } catch (Exception e) {
            logger.error("Error obteniendo usuario {}: {}", cuenta, e.getMessage());
            return new UsuarioHashDTO(500, "Error en el servidor");
        }
    }

    @Override
    public void migrarContrasena(String cuenta, String contrasenaPlana) {
        try {
            // Validaciones básicas
            if (cuenta == null || cuenta.trim().isEmpty()) {
                throw new IllegalArgumentException("Cuenta no proporcionada");
            }
            if (contrasenaPlana == null || contrasenaPlana.trim().isEmpty()) {
                throw new IllegalArgumentException("Contraseña no proporcionada");
            }

            // Buscar usuario
            Usuario usuario = usuarioRepository.findByCuenta(cuenta.trim())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Verificar si ya es BCrypt
            if (usuario.getContrasena().startsWith("$2a$")) {
                logger.info("La contraseña ya está en BCrypt para {}", cuenta);
                return;
            }

            // Migrar a BCrypt
            String hashBCrypt = passwordEncoder.encode(contrasenaPlana.trim());
            usuario.setContrasena(hashBCrypt);
            usuarioRepository.save(usuario);
            
            logger.info("Contraseña migrada exitosamente a BCrypt para cuenta: {}", cuenta);
            
        } catch (Exception e) {
            logger.error("Error migrando contraseña para {}: {}", cuenta, e.getMessage());
            throw new RuntimeException("Error migrando contraseña: " + e.getMessage());
        }
    }
}