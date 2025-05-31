package mx.gob.sev.serv.repository;

import mx.gob.sev.serv.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryCustom {
    // No agregar métodos aquí que estén en la interfaz Custom
}
