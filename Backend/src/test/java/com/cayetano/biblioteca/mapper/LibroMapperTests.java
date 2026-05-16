package com.cayetano.biblioteca.mapper;

import com.cayetano.biblioteca.dto.LibroDTO;
import com.cayetano.biblioteca.entity.Autor;
import com.cayetano.biblioteca.entity.EstadoLibro;
import com.cayetano.biblioteca.entity.Libro;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibroMapperTest {

    private Libro crearLibro() {
        Libro libro = new Libro();
        libro.setIdLibro(1L);
        libro.setTitulo("El Quijote");
        libro.setAnioPublicacion(1605);
        libro.setValoracion(10);
        libro.setDescripcion("Obra maestra");
        libro.setImagen("quijote.jpg");

        Autor autor = new Autor();
        autor.setIdAutor(5L);
        autor.setNombre("Miguel");
        autor.setApellido("de Cervantes");
        libro.setAutor(autor);

        EstadoLibro estado = new EstadoLibro();
        estado.setIdEstado(3L);
        estado.setDescripcion("DISPONIBLE");
        libro.setEstado(estado);

        return libro;
    }

    @Test
    void testToDTO() {
        Libro libro = crearLibro();

        LibroDTO dto = LibroMapper.toDTO(libro);

        assertEquals(1L, dto.getId());
        assertEquals("El Quijote", dto.getTitulo());
        assertEquals(1605, dto.getAnioPublicacion());
        assertEquals(10, dto.getValoracion());
        assertEquals("Obra maestra", dto.getDescripcion());
        assertEquals("quijote.jpg", dto.getImagen());
        assertEquals("Miguel de Cervantes", dto.getAutor());
        assertEquals("DISPONIBLE", dto.getEstado());
    }

    @Test
    void testToDTO_AutorNull() {
        Libro libro = crearLibro();
        libro.setAutor(null);

        LibroDTO dto = LibroMapper.toDTO(libro);

        assertEquals("Desconocido", dto.getAutor());
    }

    @Test
    void testToDTO_EstadoNull() {
        Libro libro = crearLibro();
        libro.setEstado(null);

        LibroDTO dto = LibroMapper.toDTO(libro);

        assertEquals("Sin estado", dto.getEstado());
    }
}
