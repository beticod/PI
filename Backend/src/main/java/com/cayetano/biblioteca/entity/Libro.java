package com.cayetano.biblioteca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "libro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private Long idLibro;

    private String titulo;

    @Column(name = "anio_publicacion")
    private Integer anioPublicacion;

    private Integer valoracion;

    private String descripcion;
    private String imagen;

    // Cada libro tiene un autor
    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;

    // Estado global del libro
    @ManyToOne
    @JoinColumn(name = "id_estado")
    private EstadoLibro estado;

    // Un libro puede estar asociado a varios usuarios
    @JsonIgnore
    @OneToMany(mappedBy = "libro")
    private List<UsuarioLibro> usuarioLibros;
}
