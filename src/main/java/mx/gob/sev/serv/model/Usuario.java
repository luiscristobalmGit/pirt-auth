package mx.gob.sev.serv.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Usuarios", catalog = "PIRT", schema = "dbo")

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", columnDefinition = "INTEGER") // Especifica el tipo exacto
    private Integer id;  // Cambiado de Long a Integer

    @Column(name = "Nombre", length = 100)
    private String nombre;

    @Column(name = "Paterno", length = 100)
    private String paterno;

    @Column(name = "Materno", length = 100)
    private String materno;

    @Column(columnDefinition = "nchar(50)") // o el tamaño adecuado
    private String cuenta;

    @Column(name = "Contrasena", length = 100)
    private String contrasena;

    @Column(name = "Activo", columnDefinition = "INT")
    private Integer activo; 

    @Column(name = "FechaCreacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "FechaModificacion")
    private LocalDateTime fechaModificacion;
}