package mx.gob.sev.serv.dto;

public class ResultadoValidacion {
    private boolean valido;
    private Long idRol;
    private String mensaje;
    private String token;

    public ResultadoValidacion() {}

    public ResultadoValidacion(boolean valido, Long idRol, String mensaje) {
        this.valido = valido;
        this.idRol = idRol;
        this.mensaje = mensaje;
    }

    public ResultadoValidacion(boolean valido, Long idRol, String mensaje, String token) {
        this.valido = valido;
        this.idRol = idRol;
        this.mensaje = mensaje;
        this.token = token;
    }

    // Getters y Setters
    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "ResultadoValidacion{" +
                "valido=" + valido +
                ", idRol=" + idRol +
                ", mensaje='" + mensaje + '\'' +
                ", token='" + (token != null ? "***MASKED***" : "null") + '\'' +
                '}';
    }
}