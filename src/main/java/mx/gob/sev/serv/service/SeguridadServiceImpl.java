package mx.gob.sev.serv.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SeguridadServiceImpl implements SeguridadService {

    private static final Logger logger = LoggerFactory.getLogger(SeguridadServiceImpl.class);

    @Override
    public void registrarEventoSeguridad(String cuenta, String tipoEvento, String detalles) {
        logger.warn("Evento de seguridad - Tipo: {} - Cuenta: {} - Detalles: {}", 
                   tipoEvento, cuenta, detalles);
        // Implementar lógica de registro en base de datos o sistema externo
    }

    @Override
    public void notificarIntentoSospechoso(String cuenta, Integer intentos) {
        String mensaje = String.format(
            "Se detectaron %d intentos fallidos recientes para la cuenta %s", 
            intentos, cuenta
        );
        logger.warn(mensaje);
        registrarEventoSeguridad(cuenta, "INTENTO_FALLIDO", mensaje);
        // Implementar notificación a administradores
    }
}