package mx.gob.sev.serv.controller;

import mx.gob.sev.serv.dto.ResultadoValidacion;
import mx.gob.sev.serv.dto.UsuarioDTO;
import mx.gob.sev.serv.dto.UsuarioHashDTO;
import mx.gob.sev.serv.service.UsuarioService;
import mx.gob.sev.serv.util.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public UsuarioController(UsuarioService usuarioService,
                           AuthenticationManager authenticationManager,
                           JwtTokenUtil jwtTokenUtil) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/validar")
    public ResponseEntity<ResultadoValidacion> validarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            // 1. Autenticar con Spring Security
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getCuenta(), usuarioDTO.getContrasena())
            );
            
            // 2. Obtener detalles adicionales del usuario (rol, etc.)
            UsuarioHashDTO usuarioHashDTO = usuarioService.obtenerDetallesUsuario(usuarioDTO.getCuenta());
            
            if (usuarioHashDTO == null || !usuarioHashDTO.getUsuarioValido()) {
                return ResponseEntity.ok(
                    new ResultadoValidacion(false, null, "Usuario no encontrado o inválido")
                );
            }
            
            // 3. Generar token JWT
            String token = jwtTokenUtil.generateToken(usuarioDTO.getCuenta(), usuarioHashDTO.getIdRol());
            
            // 4. Retornar resultado exitoso
            return ResponseEntity.ok(
                new ResultadoValidacion(
                    true,
                    usuarioHashDTO.getIdRol(),
                    "Autenticación exitosa",
                    token
                )
            );
            
        } catch (Exception e) {
            return ResponseEntity.ok(
                new ResultadoValidacion(false, null, "Error en autenticación: " + e.getMessage())
            );
        }
    }
}