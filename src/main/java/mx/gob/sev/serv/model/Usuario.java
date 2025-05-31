package mx.gob.sev.serv.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Usuarios", schema = "PIRT")
public class Usuario {
    @Id
    @Column(name = "Id")
    private Long id;

    @Column(name = "Nombre", length = 100)
    private String nombre;

    @Column(name = "Paterno", length = 100)
    private String paterno;

    @Column(name = "Materno", length = 100)
    private String materno;

    @Column(name = "Cuenta", length = 50, unique = true)
    private String cuenta;

    @Column(name = "Contrasena", length = 100)
    private String contrasena;

    @Column(name = "Activo")
    private Boolean activo;

    @Column(name = "FechaCreacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "FechaModificacion")
    private LocalDateTime fechaModificacion;
}