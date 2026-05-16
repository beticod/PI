package com.cayetano.biblioteca.dto;

import lombok.Data;

@Data
public class UsuarioLibroDTO {

    private Long id;
    private boolean favorito;
    private Integer puntuacionPersonal;
    private String fechaLectura;

    private Long idLibro;
    private String titulo;
    private String autor;
    private String imagen;

    private String estadoUsuario;
    private String estadoLibro;

    private String descripcion;
}

