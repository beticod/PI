package com.cayetano.biblioteca.dto;

import lombok.Data;

@Data

public class LibroDTO {

    private Long id;
    private String titulo;
    private Integer anioPublicacion;
    private Integer valoracion;

    private String descripcion;
    private String imagen;

    private String autor;
    private String estado;
}


