package mx.gob.sev.serv.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    
    @NotBlank(message = "La cuenta es requerida")
    private String cuenta;
    
    @NotBlank(message = "La contraseña es requerida")
    private String contrasena;

    // Getters y Setters
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
}