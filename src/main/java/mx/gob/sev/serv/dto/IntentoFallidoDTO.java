package mx.gob.sev.serv.dto;

import java.time.LocalDateTime;

public class IntentoFallidoDTO {
    private Long id;  // Cambiado a Long para coincidir con bigint de BD
    private String cuenta;
    private String ip;
    private String dispositivo;
    private LocalDateTime fechaHora;

    // Constructores
    public IntentoFallidoDTO() {}

    public IntentoFallidoDTO(String cuenta, String ip, LocalDateTime fechaHora) {
        this.cuenta = cuenta;
        this.ip = ip;
        this.fechaHora = fechaHora;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCuenta() { return cuenta; }
    public void setCuenta(String cuenta) { this.cuenta = cuenta; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public String getDispositivo() { return dispositivo; }
    public void setDispositivo(String dispositivo) { this.dispositivo = dispositivo; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    @Override
    public String toString() {
        return "IntentoFallidoDTO{" +
               "id=" + id +
               ", cuenta='" + cuenta + '\'' +
               ", ip='" + ip + '\'' +
               ", dispositivo='" + dispositivo + '\'' +
               ", fechaHora=" + fechaHora +
               '}';
    }
}