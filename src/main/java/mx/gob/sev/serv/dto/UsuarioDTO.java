package mx.gob.sev.serv.dto;

import jakarta.validation.constraints.NotBlank;

public class UsuarioDTO {

    @NotBlank(message = "La cuenta no puede estar vacía")
    private String cuenta;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String contrasena;

    public UsuarioDTO() {}

    public UsuarioDTO(String cuenta, String contrasena) {
        this.cuenta = cuenta;
        this.contrasena = contrasena;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "cuenta='" + cuenta + '\'' +
                ", contrasena='[PROTEGIDA]'" +
                '}';
    }
}
