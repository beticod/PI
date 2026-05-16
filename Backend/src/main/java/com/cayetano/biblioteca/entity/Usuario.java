package com.cayetano.biblioteca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    private String nombre;
    private String email;

    // Un usuario puede tener muchos libros asociados
    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<UsuarioLibro> usuarioLibros;
}
