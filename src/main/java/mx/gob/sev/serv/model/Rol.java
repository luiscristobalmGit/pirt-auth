package mx.gob.sev.serv.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Roles", catalog = "PIRT", schema = "dbo")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", columnDefinition = "INTEGER") // Especifica el tipo exacto
    private Integer id;  // Cambiado de Long a Integer

    @Column(name = "IdUsuarios")
    private Integer idUsuarios;

    @Column(name = "IdTipoRol", columnDefinition = "INT")
    private Integer idTipoRol;

    @Column(name = "Activo", columnDefinition = "INT")
    private Integer activo; 

    @Column(name = "FechaCreacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "FechaModificacion")
    private LocalDateTime fechaModificacion;
}