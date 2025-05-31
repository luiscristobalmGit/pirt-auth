package mx.gob.sev.serv.service;

import mx.gob.sev.serv.dto.ResultadoValidacion;
import mx.gob.sev.serv.dto.UsuarioDTO;

public interface UsuarioService {
    ResultadoValidacion validarUsuario(UsuarioDTO usuarioDTO);
}