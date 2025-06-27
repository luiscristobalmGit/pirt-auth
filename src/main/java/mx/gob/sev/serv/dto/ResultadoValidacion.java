package mx.gob.sev.serv.dto;

public class ResultadoValidacion {
    private boolean valido;
    private Integer idRol;
    private String nombreRol;
    private String mensaje;
    private String token;

    // Constructores
    public ResultadoValidacion() {}

    public ResultadoValidacion(boolean valido, Integer idRol, String nombreRol, String mensaje) {
        this.valido = valido;
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.mensaje = mensaje;
    }

    public ResultadoValidacion(boolean valido, Integer idRol, String nombreRol, String mensaje, String token) {
        this.valido = valido;
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.mensaje = mensaje;
        this.token = token;
    }

    // Getters y Setters
    public boolean isValido() { return valido; }
    public void setValido(boolean valido) { this.valido = valido; }
    public Integer getIdRol() { return idRol; }
    public void setIdRol(Integer idRol) { this.idRol = idRol; }
    public String getNombreRol() { return nombreRol; }
    public void setNombreRol(String nombreRol) { this.nombreRol = nombreRol; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    @Override
    public String toString() {
        return "ResultadoValidacion{" +
               "valido=" + valido +
               ", idRol=" + idRol +
               ", nombreRol='" + nombreRol + '\'' +
               ", mensaje='" + mensaje + '\'' +
               ", token='" + (token != null ? "***MASKED***" : "null") + '\'' +
               '}';
    }
}