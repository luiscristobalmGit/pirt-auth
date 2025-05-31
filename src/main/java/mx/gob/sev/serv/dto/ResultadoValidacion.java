package mx.gob.sev.serv.dto;

public class ResultadoValidacion {

    private boolean valido;
    private Long idRol;
    private String mensaje;

    public ResultadoValidacion() {}

    public ResultadoValidacion(boolean valido, Long idRol, String mensaje) {
        this.valido = valido;
        this.idRol = idRol;
        this.mensaje = mensaje;
    }

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

    @Override
    public String toString() {
        return "ResultadoValidacion{" +
                "valido=" + valido +
                ", idRol=" + idRol +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}
