package com.cayetano.biblioteca.mapper;

import com.cayetano.biblioteca.dto.UsuarioLibroDTO;
import com.cayetano.biblioteca.entity.UsuarioLibro;

public class UsuarioLibroMapper {

    public static UsuarioLibroDTO toDTO(UsuarioLibro ul) {

        if (ul == null) {
            return null;
        }

        UsuarioLibroDTO dto = new UsuarioLibroDTO();

        dto.setId(ul.getIdUsuarioLibro());
        dto.setFavorito(ul.isFavorito());
        dto.setPuntuacionPersonal(ul.getPuntuacionPersonal());

        if (ul.getFechaLectura() != null) {
            dto.setFechaLectura(ul.getFechaLectura().toString());
        }

        if (ul.getLibro() != null) {
            dto.setIdLibro(ul.getLibro().getIdLibro());
            dto.setTitulo(ul.getLibro().getTitulo());
            dto.setImagen(ul.getLibro().getImagen());
            dto.setDescripcion(ul.getLibro().getDescripcion());

            if (ul.getLibro().getAutor() != null) {
                dto.setAutor(ul.getLibro().getAutor().getNombre());
            }

            if (ul.getLibro().getEstado() != null) {
                dto.setEstadoLibro(ul.getLibro().getEstado().getDescripcion());
            }
        }

        if (ul.getEstado() != null) {
            dto.setEstadoUsuario(ul.getEstado().getDescripcion());
        }

        return dto;
    }
}
