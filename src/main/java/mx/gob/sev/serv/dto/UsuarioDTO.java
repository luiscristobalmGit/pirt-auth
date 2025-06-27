package mx.gob.sev.serv.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioDTO {
    @NotBlank(message = "La cuenta no puede estar vacía")
    @Size(min = 4, max = 20, message = "La cuenta debe tener entre 4 y 20 caracteres")
    private String cuenta;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String contrasena;

    // Constructores
    public UsuarioDTO() {}

    public UsuarioDTO(String cuenta, String contrasena) {
        this.cuenta = cuenta;
        this.contrasena = contrasena;
    }

    // Getters y Setters
    public String getCuenta() { return cuenta; }
    public void setCuenta(String cuenta) { this.cuenta = cuenta; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
               "cuenta='" + cuenta + '\'' +
               ", contrasena='[PROTEGIDA]'" +
               '}';
    }
}