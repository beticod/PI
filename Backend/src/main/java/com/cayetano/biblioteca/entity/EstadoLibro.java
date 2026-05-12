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

    // LEIDO, LEYENDO, PENDIENTE
    @Column(nullable = false)
    private String descripcion;
}
