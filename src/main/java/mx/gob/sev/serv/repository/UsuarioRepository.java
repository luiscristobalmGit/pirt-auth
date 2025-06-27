package mx.gob.sev.serv.repository;

import mx.gob.sev.serv.dto.UsuarioHashDTO;
import mx.gob.sev.serv.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    @Query("SELECT new mx.gob.sev.serv.dto.UsuarioHashDTO(" +
           "u.contrasena, " +
           "COALESCE((SELECT r.tipoRol.id FROM Rol r WHERE r.usuario.id = u.id AND r.activo = 1), 0), " +
           "u.id, " +
           "CASE WHEN u.contrasena LIKE '$2a$%' THEN 1 ELSE 0 END, " +
           "u.activo) " +
           "FROM Usuario u " +
           "WHERE u.cuenta = :cuenta")
    Optional<UsuarioHashDTO> obtenerHashPorCuenta(@Param("cuenta") String cuenta);
    
    Optional<Usuario> findByCuenta(String cuenta);
    boolean existsByCuenta(String cuenta);
}