package mx.gob.sev.serv.service;

import mx.gob.sev.serv.dto.IntentoFallidoDTO;
import mx.gob.sev.serv.model.IntentoFallido;
import mx.gob.sev.serv.repository.IntentoFallidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class IntentoFallidoServiceImpl implements IntentoFallidoService {

    private static final Logger logger = LoggerFactory.getLogger(IntentoFallidoServiceImpl.class);
    private final IntentoFallidoRepository repository;

    public IntentoFallidoServiceImpl(IntentoFallidoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void registrarIntentoFallido(IntentoFallidoDTO intentoDTO) {
        try {
            if (intentoDTO == null) {
                throw new IllegalArgumentException("DTO de intento no puede ser nulo");
            }
            
            IntentoFallido intento = new IntentoFallido();
            intento.setCuenta(intentoDTO.getCuenta());
            intento.setIp(intentoDTO.getIp());
            intento.setDispositivo(intentoDTO.getDispositivo());
            intento.setFechaHora(LocalDateTime.now());
            
            repository.save(intento);
            logger.debug("Intento fallido registrado para cuenta: {}", intento.getCuenta());
        } catch (Exception e) {
            logger.error("Error al registrar intento fallido: {}", e.getMessage());
            throw new RuntimeException("Error al registrar intento fallido", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Integer contarIntentosRecientes(String cuenta) {
        try {
            if (cuenta == null || cuenta.trim().isEmpty()) {
                throw new IllegalArgumentException("Cuenta no puede estar vacía");
            }
            
            LocalDateTime desde = LocalDateTime.now().minusMinutes(30);
            Integer conteo = repository.countByCuentaAndFechaHoraAfter(cuenta.trim(), desde);
            
            logger.debug("Intentos recientes para {}: {}", cuenta, conteo);
            return conteo != null ? conteo : 0;
        } catch (Exception e) {
            logger.error("Error contando intentos para {}: {}", cuenta, e.getMessage());
            return 0;
        }
    }
}