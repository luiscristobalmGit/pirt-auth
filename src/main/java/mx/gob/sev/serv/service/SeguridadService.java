    package mx.gob.sev.serv.service;

    public interface SeguridadService {
        void registrarEventoSeguridad(String cuenta, String tipoEvento, String detalles);
        void notificarIntentoSospechoso(String cuenta, Integer intentos);
    }