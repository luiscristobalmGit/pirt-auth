package mx.gob.sev.serv.dto;

public class UsuarioHashDTO {

    // Datos de autenticación
    private String hash;
    private Long idRol;
    private Long idUsuario;
    private Boolean esBCrypt;
    private Boolean usuarioValido;

    // Información de error
    private Integer codigoError;
    private String mensajeError;

    // Constructor vacío
    public UsuarioHashDTO() {}

    // Constructor exitoso
    public UsuarioHashDTO(String hash, Long idRol, Long idUsuario, Boolean esBCrypt, Boolean usuarioValido) {
        this.hash = hash;
        this.idRol = idRol;
        this.idUsuario = idUsuario;
        this.esBCrypt = esBCrypt;
        this.usuarioValido = usuarioValido;
    }

    // Constructor con error
    public UsuarioHashDTO(Integer codigoError, String mensajeError) {
        this.codigoError = codigoError;
        this.mensajeError = mensajeError;
    }

    // Getters y setters
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Boolean getEsBCrypt() {
        return esBCrypt;
    }

    public void setEsBCrypt(Boolean esBCrypt) {
        this.esBCrypt = esBCrypt;
    }

    public Boolean getUsuarioValido() {
        return usuarioValido;
    }

    public void setUsuarioValido(Boolean usuarioValido) {
        this.usuarioValido = usuarioValido;
    }

    public Integer getCodigoError() {
        return codigoError;
    }

    public void setCodigoError(Integer codigoError) {
        this.codigoError = codigoError;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    @Override
    public String toString() {
        return "UsuarioHashDTO{" +
                "hash='" + (hash != null ? "***" : null) + '\'' +
                ", idRol=" + idRol +
                ", idUsuario=" + idUsuario +
                ", esBCrypt=" + esBCrypt +
                ", usuarioValido=" + usuarioValido +
                ", codigoError=" + codigoError +
                ", mensajeError='" + mensajeError + '\'' +
                '}';
    }
}
