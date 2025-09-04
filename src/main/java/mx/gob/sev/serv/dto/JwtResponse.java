package mx.gob.sev.serv.dto;

public class JwtResponse {
    private String token;
    private String username;
    private String rol;
    private Integer idUsuario;  // Nuevo campo

    public JwtResponse(String token, String username, String rol, Integer idUsuario) {
        this.token = token;
        this.username = username;
        this.rol = rol;
        this.idUsuario = idUsuario;
    }

    // Getters
    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getRol() {
        return rol;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }
}