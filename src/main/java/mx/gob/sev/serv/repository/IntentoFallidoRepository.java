package mx.gob.sev.serv.repository;

import mx.gob.sev.serv.model.IntentoFallido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface IntentoFallidoRepository extends JpaRepository<IntentoFallido, Integer> {
    Integer countByCuentaAndFechaHoraAfter(String cuenta, LocalDateTime fecha);
}