package com.cayetano.biblioteca.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioLibroDTOTest {

    @Test
    void testSettersAndGetters() {
        UsuarioLibroDTO dto = new UsuarioLibroDTO();

        dto.setId(1L);
        dto.setFavorito(true);
        dto.setPuntuacionPersonal(7);
        dto.setFechaLectura("2024-05-01");
        dto.setIdLibro(20L);
        dto.setTitulo("El Señor de los Anillos");
        dto.setAutor("J.R.R. Tolkien");
        dto.setImagen("portada.jpg");
        dto.setEstadoUsuario("LEYENDO");
        dto.setEstadoLibro("DISPONIBLE");
        dto.setDescripcion("Un libro épico de fantasía");

        assertEquals(1L, dto.getId());
        assertTrue(dto.isFavorito());
        assertEquals(7, dto.getPuntuacionPersonal());
        assertEquals("2024-05-01", dto.getFechaLectura());
        assertEquals(20L, dto.getIdLibro());
        assertEquals("El Señor de los Anillos", dto.getTitulo());
        assertEquals("J.R.R. Tolkien", dto.getAutor());
        assertEquals("portada.jpg", dto.getImagen());
        assertEquals("LEYENDO", dto.getEstadoUsuario());
        assertEquals("DISPONIBLE", dto.getEstadoLibro());
        assertEquals("Un libro épico de fantasía", dto.getDescripcion());
    }
}
