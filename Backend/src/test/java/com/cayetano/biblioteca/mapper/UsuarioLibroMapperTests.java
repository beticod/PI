package com.cayetano.biblioteca.mapper;

import com.cayetano.biblioteca.dto.UsuarioLibroDTO;
import com.cayetano.biblioteca.entity.Autor;
import com.cayetano.biblioteca.entity.EstadoLibro;
import com.cayetano.biblioteca.entity.Libro;
import com.cayetano.biblioteca.entity.Usuario;
import com.cayetano.biblioteca.entity.UsuarioLibro;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioLibroMapperTest {

    private UsuarioLibro crearEntidad() {
        UsuarioLibro ul = new UsuarioLibro();
        ul.setIdUsuarioLibro(1L);
        ul.setFavorito(true);
        ul.setPuntuacionPersonal(8);
        ul.setFechaLectura(LocalDate.parse("2024-05-01"));

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(10L);
        usuario.setNombre("Usuario Test");
        ul.setUsuario(usuario);

        Autor autor = new Autor();
        autor.setIdAutor(5L);
        autor.setNombre("Autor Test");

        Libro libro = new Libro();
        libro.setIdLibro(20L);
        libro.setTitulo("Libro Test");
        libro.setAutor(autor);
        libro.setDescripcion("Descripción del libro");
        libro.setImagen("imagen.jpg");
        ul.setLibro(libro);

        EstadoLibro estado = new EstadoLibro();
        estado.setIdEstado(3L);
        estado.setDescripcion("LEYENDO");
        ul.setEstado(estado);

        return ul;
    }

    @Test
    void testToDTO() {
        UsuarioLibro ul = crearEntidad();

        UsuarioLibroDTO dto = UsuarioLibroMapper.toDTO(ul);

        assertEquals(1L, dto.getId());
        assertTrue(dto.isFavorito());
        assertEquals(8, dto.getPuntuacionPersonal());
        assertEquals("2024-05-01", dto.getFechaLectura());
        assertEquals(20L, dto.getIdLibro());
        assertEquals("Libro Test", dto.getTitulo());
        assertEquals("Autor Test", dto.getAutor());
        assertEquals("imagen.jpg", dto.getImagen());
        assertEquals("LEYENDO", dto.getEstadoUsuario());
        assertEquals("Descripción del libro", dto.getDescripcion());
    }

    @Test
    void testToDTO_Null() {
        UsuarioLibroDTO dto = UsuarioLibroMapper.toDTO(null);
        assertNull(dto);
    }
}
