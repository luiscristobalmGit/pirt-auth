package mx.gob.sev.serv.repository;

import mx.gob.sev.serv.dto.UsuarioHashDTO;

import java.util.Optional;

public interface UsuarioRepositoryCustom {
    Optional<UsuarioHashDTO> obtenerHashPorCuenta(String cuenta);
}
