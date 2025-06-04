package mx.gob.sev.serv.service;

import mx.gob.sev.serv.dto.ResultadoValidacion;
import mx.gob.sev.serv.dto.UsuarioDTO;
import mx.gob.sev.serv.dto.UsuarioHashDTO;
import mx.gob.sev.serv.repository.UsuarioRepository;
import mx.gob.sev.serv.util.JwtTokenUtil;
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
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, 
                            PasswordEncoder passwordEncoder,
                            JwtTokenUtil jwtTokenUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public ResultadoValidacion validarUsuario(UsuarioDTO usuarioDTO) {
        try {
            if (usuarioDTO == null || usuarioDTO.getCuenta() == null || usuarioDTO.getContrasena() == null) {
                return new ResultadoValidacion(false, null, "Credenciales incompletas");
            }

            Optional<UsuarioHashDTO> usuarioOpt = usuarioRepository.obtenerHashPorCuenta(usuarioDTO.getCuenta());
            if (usuarioOpt.isEmpty()) {
                return new ResultadoValidacion(false, null, "Error al validar usuario");
            }

            UsuarioHashDTO usuario = usuarioOpt.get();

            if (usuario.getCodigoError() != null) {
                String mensaje = usuario.getMensajeError() != null ? usuario.getMensajeError() : "Error en validación";
                return new ResultadoValidacion(false, null, mensaje);
            }

            if (!Boolean.TRUE.equals(usuario.getUsuarioValido())) {
                return new ResultadoValidacion(false, null, "Usuario no válido o inactivo");
            }

            if (usuario.getHash() == null) {
                return new ResultadoValidacion(false, null, "Credenciales inválidas");
            }

            boolean credencialesValidas = Boolean.TRUE.equals(usuario.getEsBCrypt())
                    ? passwordEncoder.matches(usuarioDTO.getContrasena(), usuario.getHash())
                    : usuarioDTO.getContrasena().equals(usuario.getHash());

            return credencialesValidas
                    ? new ResultadoValidacion(
                        true, 
                        usuario.getIdRol(), 
                        "Autenticación exitosa",
                        jwtTokenUtil.generateToken(usuarioDTO.getCuenta(), usuario.getIdRol()))
                    : new ResultadoValidacion(false, null, "Credenciales inválidas");

        } catch (Exception e) {
            logger.error("Error al validar usuario: {}", e.getMessage(), e);
            return new ResultadoValidacion(false, null, "Error en el servidor");
        }
    }
}