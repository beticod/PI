package com.cayetano.biblioteca.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "usuario_libro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioLibro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario_libro")
    private Long idUsuarioLibro;

    // Relación N:1 → un usuario puede tener muchos libros asociados
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    // Relación N:1 → un libro puede estar asociado a muchos usuarios
    @ManyToOne
    @JoinColumn(name = "id_libro")
    private Libro libro;

    // Si el usuario marcó el libro como favorito
    private boolean favorito;

    // Puntuación personal del usuario (0–10)
    @Column(name = "puntuacion_personal")
    private Integer puntuacionPersonal;

    // Fecha en la que el usuario terminó de leer el libro
    @Column(name = "fecha_lectura")
    private LocalDate fechaLectura;

    // Estado del libro PARA ESTE usuario (LEIDO, LEYENDO, PENDIENTE)
    @ManyToOne
    @JoinColumn(name = "id_estado")
    private EstadoLibro estado;
}
