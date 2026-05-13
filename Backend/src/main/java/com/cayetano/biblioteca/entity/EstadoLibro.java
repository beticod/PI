package com.cayetano.biblioteca.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estado_libro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoLibro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    private Long idEstado;

    @Column(name = "estado_fijo")
    private Long idEstadoFijo;

    private String descripcion;

    // Constructor útil para DataLoader (sin id autogenerado)
    public EstadoLibro(Long idEstadoFijo, String descripcion) {
        this.idEstadoFijo = idEstadoFijo;
        this.descripcion = descripcion;
    }
}
