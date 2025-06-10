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

            if (!query.execute()) {
                logger.warn("SP ejecutado pero no retornó resultados");
                return Optional.empty();
            }

            Object[] row = (Object[]) query.getSingleResult();
            if (row == null || row.length < 5) {
                logger.warn("Resultado del SP incompleto");
                return Optional.empty();
            }

            UsuarioHashDTO result = mapRowToUsuarioHashDTO(row);
            return Optional.ofNullable(result);

        } catch (Exception e) {
            logger.error("Error al ejecutar SP 'sp_ObtenerHashUsuario'", e);
            return Optional.of(new UsuarioHashDTO(-1, "Error en el servidor: " + e.getMessage()));
        }
    }

    private UsuarioHashDTO mapRowToUsuarioHashDTO(Object[] row) {
        try {
            String hash = row[0] != null ? row[0].toString() : null;
            Long idRol = row[1] != null ? ((Number) row[1]).longValue() : null;
            Long idUsuario = row[2] != null ? ((Number) row[2]).longValue() : null;
            boolean esBCrypt = row[3] != null && ((Number) row[3]).intValue() == 1;
            boolean usuarioValido = row[4] != null && ((Number) row[4]).intValue() == 1;

            if (row.length > 5 && row[5] != null) {
                int codigoError = ((Number) row[5]).intValue();
                String mensajeError = row.length > 6 ? row[6].toString() : "Error desconocido";
                return new UsuarioHashDTO(codigoError, mensajeError);
            }

            return new UsuarioHashDTO(hash, idRol, idUsuario, esBCrypt, usuarioValido);
        } catch (Exception e) {
            logger.error("Error mapeando resultado del SP", e);
            return new UsuarioHashDTO(-4, "Error procesando resultado");
        }
    }
}