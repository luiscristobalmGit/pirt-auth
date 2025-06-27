package mx.gob.sev.serv.service;

import mx.gob.sev.serv.dto.UsuarioHashDTO;

public interface UsuarioService {
    UsuarioHashDTO validarCredenciales(String cuenta, String contrasena);
    UsuarioHashDTO obtenerDetallesUsuario(String cuenta);
    void migrarContrasena(String cuenta, String contrasenaPlana);
}