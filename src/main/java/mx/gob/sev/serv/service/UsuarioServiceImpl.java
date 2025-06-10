package mx.gob.sev.serv.service;

import mx.gob.sev.serv.dto.UsuarioHashDTO;
import mx.gob.sev.serv.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    private final UsuarioRepository usuarioRepository;
    // private final PasswordEncoder passwordEncoder;

    // Eliminamos jwtTokenUtil ya que ahora el token se genera en el Controller
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, 
                            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        // this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioHashDTO obtenerDetallesUsuario(String cuenta) {
        try {
            if (cuenta == null || cuenta.isEmpty()) {
                return crearUsuarioHashInvalido("Cuenta no proporcionada");
            }

            Optional<UsuarioHashDTO> usuarioOpt = usuarioRepository.obtenerHashPorCuenta(cuenta);
            if (usuarioOpt.isEmpty()) {
                return crearUsuarioHashInvalido("Usuario no encontrado");
            }

            UsuarioHashDTO usuario = usuarioOpt.get();

            if (usuario.getCodigoError() != null) {
                String mensaje = usuario.getMensajeError() != null ? 
                    usuario.getMensajeError() : "Error en validación";
                return crearUsuarioHashInvalido(mensaje);
            }

            if (!Boolean.TRUE.equals(usuario.getUsuarioValido())) {
                return crearUsuarioHashInvalido("Usuario no válido o inactivo");
            }

            // Si todo está bien, retornamos el DTO con los datos del usuario
            return usuario;

        } catch (Exception e) {
            logger.error("Error al obtener detalles del usuario: {}", e.getMessage(), e);
            return crearUsuarioHashInvalido("Error en el servidor");
        }
    }

    private UsuarioHashDTO crearUsuarioHashInvalido(String mensajeError) {
        UsuarioHashDTO usuario = new UsuarioHashDTO();
        usuario.setUsuarioValido(false);
        usuario.setMensajeError(mensajeError);
        return usuario;
    }
}