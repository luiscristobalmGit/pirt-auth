package mx.gob.sev.serv.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import mx.gob.sev.serv.dto.UsuarioHashDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepositoryCustom {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioRepositoryImpl.class);
    private final EntityManager entityManager;

    public UsuarioRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<UsuarioHashDTO> obtenerHashPorCuenta(String cuenta) {
        try {
            StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("dbo.sp_ObtenerHashUsuario")
                .registerStoredProcedureParameter("Cuenta", String.class, ParameterMode.IN)
                .setParameter("Cuenta", cuenta);

            query.execute();

            Object[] result = (Object[]) query.getSingleResult();
            
            if (result == null || result.length < 5) {
                return Optional.empty();
            }

            String hash = result[0] != null ? result[0].toString() : null;
            Integer idTipoRol = result[1] != null ? ((Number) result[1]).intValue() : null;
            Integer idUsuario = result[2] != null ? ((Number) result[2]).intValue() : null;
            Integer esBCrypt = result[3] != null ? ((Number) result[3]).intValue() : 0;
            Integer usuarioValido = result[4] != null ? ((Number) result[4]).intValue() : 0;

            // Manejo de errores desde el SP
            if (result.length > 5 && result[5] != null && ((Number) result[5]).intValue() != 0) {
                Integer codigoError = ((Number) result[5]).intValue();
                String mensajeError = result.length > 6 && result[6] != null ? result[6].toString() : "Error desconocido";
                return Optional.of(new UsuarioHashDTO(codigoError, mensajeError));
            }

            if (hash == null || idUsuario == null) {
                return Optional.empty();
            }

            return Optional.of(new UsuarioHashDTO(hash, idTipoRol, idUsuario, esBCrypt, usuarioValido));

        } catch (Exception e) {
            logger.error("Error al ejecutar SP para cuenta {}: {}", cuenta, e.getMessage(), e);
            return Optional.of(new UsuarioHashDTO(500, "Error en el servidor"));
        }
    }
}