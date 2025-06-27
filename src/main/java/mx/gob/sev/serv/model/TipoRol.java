package mx.gob.sev.serv.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "TipoRol", schema = "dbo")
public class TipoRol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    
    @Column(name = "TipoRol", nullable = false, length = 50, unique = true)
    private String nombre;
    
    @Column(name = "Activo", nullable = false)
    private Integer activo = 1;
    
    @Column(name = "FechaCreacion", updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    @Column(name = "FechaModificacion")
    private LocalDateTime fechaModificacion;

    @PrePersist
    protected void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void preUpdate() {
        this.fechaModificacion = LocalDateTime.now();
    }
}