package mx.gob.sev.serv.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Roles", schema = "PIRT")
public class Rol {
    @Id
    @Column(name = "Id")
    private Long id;

    @Column(name = "IdUsuarios")
    private Long idUsuarios;

    @Column(name = "IdTipoRol")
    private Long idTipoRol;

    @Column(name = "Activo")
    private Boolean activo;

    @Column(name = "FechaCreacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "FechaModificacion")
    private LocalDateTime fechaModificacion;
}