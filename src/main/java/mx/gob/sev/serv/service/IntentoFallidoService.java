package mx.gob.sev.serv.service;

import mx.gob.sev.serv.dto.IntentoFallidoDTO;

public interface IntentoFallidoService {
    void registrarIntentoFallido(IntentoFallidoDTO intento);
    Integer contarIntentosRecientes(String cuenta);
}