package com.cayetano.biblioteca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "autor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_autor")
    private Long idAutor;

    // Nombre y apellido del autor
    private String nombre;
    private String apellido;

    // Un autor puede tener varios libros
    // Se ignora en JSON para evitar recursión infinita
    @JsonIgnore
    @OneToMany(mappedBy = "autor")
    private List<Libro> libros;
}
