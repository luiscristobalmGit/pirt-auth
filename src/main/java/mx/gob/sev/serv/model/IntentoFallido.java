package mx.gob.sev.serv.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "intentos_fallidos") // Verifica que el nombre de la tabla sea correcto en tu BD
public class IntentoFallido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "cuenta", nullable = false, length = 100)
    private String cuenta;
    
    @Column(name = "ip", nullable = false, length = 45)
    private String ip;
    
    @Column(name = "dispositivo", length = 255)
    private String dispositivo;
    
    @Column(name = "fecha_hora", nullable = false, columnDefinition = "datetime")
    private LocalDateTime fechaHora;
    
    // Constructores
    public IntentoFallido() {
        // Constructor por defecto necesario para JPA
    }

    public IntentoFallido(String cuenta, String ip, LocalDateTime fechaHora) {
        this.cuenta = cuenta;
        this.ip = ip;
        this.fechaHora = fechaHora;
    }

    public IntentoFallido(String cuenta, String ip, String dispositivo, LocalDateTime fechaHora) {
        this.cuenta = cuenta;
        this.ip = ip;
        this.dispositivo = dispositivo;
        this.fechaHora = fechaHora;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    // Método toString() para logging/debugging
    @Override
    public String toString() {
        return "IntentoFallido{" +
                "id=" + id +
                ", cuenta='" + cuenta + '\'' +
                ", ip='" + ip + '\'' +
                ", dispositivo='" + dispositivo + '\'' +
                ", fechaHora=" + fechaHora +
                '}';
    }
}