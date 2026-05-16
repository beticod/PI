package com.cayetano.biblioteca.mapper;

import com.cayetano.biblioteca.dto.UsuarioLibroDTO;
import com.cayetano.biblioteca.entity.UsuarioLibro;

public class UsuarioLibroMapper {

    public static UsuarioLibroDTO toDTO(UsuarioLibro ul) {
        UsuarioLibroDTO dto = new UsuarioLibroDTO();

        dto.setId(ul.getIdUsuarioLibro());
        dto.setFavorito(ul.isFavorito());
        dto.setPuntuacionPersonal(ul.getPuntuacionPersonal());

        // Fecha de lectura
        if (ul.getFechaLectura() != null) {
            dto.setFechaLectura(ul.getFechaLectura().toString());
        }

        // Datos del libro
        dto.setIdLibro(ul.getLibro().getIdLibro());
        dto.setTitulo(ul.getLibro().getTitulo());
        dto.setAutor(ul.getLibro().getAutor().getNombre());
        dto.setImagen(ul.getLibro().getImagen());

        // Estado del usuario
        if (ul.getEstado() != null) {
            dto.setEstadoUsuario(ul.getEstado().getDescripcion());
        }

        // Estado global del libro
        if (ul.getLibro().getEstado() != null) {
            dto.setEstadoLibro(ul.getLibro().getEstado().getDescripcion());
        }

        // Descripcion del libro
        dto.setDescripcion(ul.getLibro().getDescripcion());

        return dto;
    }
}
