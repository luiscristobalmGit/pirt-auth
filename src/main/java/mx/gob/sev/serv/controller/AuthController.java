package mx.gob.sev.serv.controller;

import mx.gob.sev.serv.dto.JwtResponse;
import mx.gob.sev.serv.dto.LoginRequest;
import mx.gob.sev.serv.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth/usuarios")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(AuthenticationManager authenticationManager,
                        JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/validar")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            logger.debug("Intentando autenticar usuario: {}", loginRequest.getCuenta());
            
            // Validar campos de entrada
            if (loginRequest.getCuenta() == null || loginRequest.getCuenta().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                    "mensaje", "La cuenta es requerida"
                ));
            }

            if (loginRequest.getContrasena() == null || loginRequest.getContrasena().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                    "mensaje", "La contraseña es requerida"
                ));
            }

            // Autenticación
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getCuenta().trim(),
                    loginRequest.getContrasena()
                )
            );
            
            // Generar token JWT
            String username = authentication.getName();
            String rol = authentication.getAuthorities().iterator().next().getAuthority();
            String token = jwtTokenUtil.generateToken(username, rol);
            
            logger.info("Autenticación exitosa para usuario: {}", username);
            
            return ResponseEntity.ok(new JwtResponse(
                token,
                username,
                rol
            ));
            
        } catch (BadCredentialsException e) {
            logger.warn("Credenciales inválidas para usuario: {}", loginRequest.getCuenta());
            return ResponseEntity.status(401).body(Map.of(
                "mensaje", "Credenciales inválidas"
            ));
        } catch (IllegalArgumentException e) {
            logger.error("Error de configuración JWT: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of(
                "mensaje", "Error de configuración del servidor",
                "detalle", "Problema con la clave de encriptación"
            ));
        } catch (AuthenticationException e) {
            logger.error("Error de autenticación para usuario {}: {}", loginRequest.getCuenta(), e.getMessage());
            return ResponseEntity.status(401).body(Map.of(
                "mensaje", "Autenticación fallida"
            ));
        } catch (Exception e) {
            logger.error("Error inesperado durante autenticación: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of(
                "mensaje", "Error en el servidor",
                "detalle", e.getMessage()
            ));
        }
    }
}