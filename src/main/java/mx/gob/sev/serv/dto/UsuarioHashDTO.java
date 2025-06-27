package mx.gob.sev.serv.dto;

public class UsuarioHashDTO {
    private String hash;
    private Integer idTipoRol;
    private Integer idUsuario;
    private Integer esBCrypt;
    private Integer usuarioValido;
    private Integer codigoError;
    private String mensajeError;

    // Constructor para resultados exitosos
    public UsuarioHashDTO(String hash, Integer idTipoRol, Integer idUsuario, 
                         Integer esBCrypt, Integer usuarioValido) {
        this.hash = hash;
        this.idTipoRol = idTipoRol;
        this.idUsuario = idUsuario;
        this.esBCrypt = esBCrypt;
        this.usuarioValido = usuarioValido;
    }

    // Constructor para errores
    public UsuarioHashDTO(Integer codigoError, String mensajeError) {
        this.codigoError = codigoError;
        this.mensajeError = mensajeError;
        this.usuarioValido = 0;
    }

    // Getters y Setters
    public String getHash() { return hash; }
    public Integer getIdTipoRol() { return idTipoRol; }
    public Integer getIdUsuario() { return idUsuario; }
    public Integer getEsBCrypt() { return esBCrypt; }
    public Integer getUsuarioValido() { return usuarioValido; }
    public Integer getCodigoError() { return codigoError; }
    public String getMensajeError() { return mensajeError; }

    public void setHash(String hash) { this.hash = hash; }
    public void setIdTipoRol(Integer idTipoRol) { this.idTipoRol = idTipoRol; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public void setEsBCrypt(Integer esBCrypt) { this.esBCrypt = esBCrypt; }
    public void setUsuarioValido(Integer usuarioValido) { this.usuarioValido = usuarioValido; }
    public void setCodigoError(Integer codigoError) { this.codigoError = codigoError; }
    public void setMensajeError(String mensajeError) { this.mensajeError = mensajeError; }
}