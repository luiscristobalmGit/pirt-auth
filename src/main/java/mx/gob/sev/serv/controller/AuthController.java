package mx.gob.sev.serv.controller;

import mx.gob.sev.serv.dto.JwtResponse;
import mx.gob.sev.serv.dto.LoginRequest;
import mx.gob.sev.serv.util.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth/usuarios")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(AuthenticationManager authenticationManager,
                         JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/validar")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // 1. Autenticar al usuario
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getCuenta(),
                    loginRequest.getContrasena()
                )
            );
            
            // 2. Obtener detalles del usuario
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            
            // 3. Obtener el primer rol (manteniendo tu estructura actual)
            String rol = userDetails.getAuthorities().iterator().next().getAuthority();
            
            // 4. Generar token JWT
            String token = jwtTokenUtil.generateToken(userDetails.getUsername(), rol);
            
            // 5. Retornar respuesta
            return ResponseEntity.ok(new JwtResponse(
                token, 
                userDetails.getUsername(),
                rol
            ));
            
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error en el servidor: " + e.getMessage());
        }
    }
}