package com.cayetano.biblioteca.mapper;

import com.cayetano.biblioteca.dto.LibroDTO;
import com.cayetano.biblioteca.entity.Libro;

public class LibroMapper {

    public static LibroDTO toDTO(Libro libro) {
        LibroDTO dto = new LibroDTO();

        dto.setId(libro.getIdLibro());
        dto.setTitulo(libro.getTitulo());
        dto.setAnioPublicacion(libro.getAnioPublicacion());
        dto.setValoracion(libro.getValoracion());
        dto.setDescripcion(libro.getDescripcion());
        dto.setImagen(libro.getImagen());

        // Autor
        if (libro.getAutor() != null) {
            String nombreCompleto = libro.getAutor().getNombre();
            if (libro.getAutor().getApellido() != null) {
                nombreCompleto += " " + libro.getAutor().getApellido();
            }
            dto.setAutor(nombreCompleto.trim());
        } else {
            dto.setAutor("Desconocido");
        }

        // Estado global del libro
        dto.setEstado(
                libro.getEstado() != null
                        ? libro.getEstado().getDescripcion()
                        : "Sin estado"
        );

        return dto;
    }
}
