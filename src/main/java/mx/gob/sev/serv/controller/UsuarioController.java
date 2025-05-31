package mx.gob.sev.serv.controller;

import mx.gob.sev.serv.dto.ResultadoValidacion;
import mx.gob.sev.serv.dto.UsuarioDTO;
import mx.gob.sev.serv.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Endpoint para validar un usuario con cuenta y contraseña.
     * POST /usuarios/validar
     * 
     * @param usuarioDTO Objeto con 'cuenta' y 'contrasena'
     * @return ResultadoValidacion con estado y rol (si aplica)
     */
    @PostMapping("/validar")
    public ResponseEntity<ResultadoValidacion> validarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        ResultadoValidacion resultado = usuarioService.validarUsuario(usuarioDTO);

        if (resultado.isValido()) {
            return ResponseEntity.ok(resultado); // HTTP 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resultado); // HTTP 401 Unauthorized
        }
    }
}
