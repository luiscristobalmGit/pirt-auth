package mx.gob.sev.serv.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Roles", schema = "dbo")
public class Rol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUsuarios", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdTipoRol", nullable = false)
    private TipoRol tipoRol;

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