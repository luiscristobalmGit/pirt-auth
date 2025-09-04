package mx.gob.sev.serv.controller;

import mx.gob.sev.serv.dto.JwtResponse;
import mx.gob.sev.serv.dto.LoginRequest;
import mx.gob.sev.serv.seguridad.UsuarioDetalles;
import mx.gob.sev.serv.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
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
            logger.info("Intento de login para cuenta: {}", loginRequest.getCuenta());
            
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getCuenta().trim(),
                    loginRequest.getContrasena()
                )
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UsuarioDetalles userDetails = (UsuarioDetalles) authentication.getPrincipal();
            
            String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
            
            String token = jwtTokenUtil.generateToken(userDetails);
            
            logger.info("Autenticación exitosa para usuario: {} (ID: {})", 
                userDetails.getUsername(), 
                userDetails.getIdUsuario());
            
            return ResponseEntity.ok(new JwtResponse(
                token,
                userDetails.getUsername(),
                roles,
                userDetails.getIdUsuario()
            ));
            
        } catch (BadCredentialsException e) {
            logger.warn("Credenciales inválidas para usuario: {}", loginRequest.getCuenta());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("mensaje", "Credenciales inválidas"));
        } catch (Exception e) {
            logger.error("Error en autenticación: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("mensaje", "Error en el servidor", "error", e.getMessage()));
        }
    }
}