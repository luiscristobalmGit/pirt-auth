package mx.gob.sev.serv.service;

import mx.gob.sev.serv.dto.UsuarioHashDTO;
//import mx.gob.sev.serv.dto.UsuarioDTO;

public interface UsuarioService {
    UsuarioHashDTO obtenerDetallesUsuario(String cuenta);
}