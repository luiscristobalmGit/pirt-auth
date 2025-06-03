package mx.gob.sev.serv.controller;

import mx.gob.sev.serv.dto.ResultadoValidacion;
import mx.gob.sev.serv.dto.UsuarioDTO;
import mx.gob.sev.serv.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = {"http://localhost", "http://localhost:8100", "http://localhost:4200", "http://localhost:8080"})
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/validar")
    public ResponseEntity<ResultadoValidacion> validarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            ResultadoValidacion resultado = usuarioService.validarUsuario(usuarioDTO);

            if (resultado.isValido()) {
                return ResponseEntity.ok(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resultado);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint adicional para verificar que el servicio está arriba
    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Servicio de usuarios funcionando correctamente");
    }
}