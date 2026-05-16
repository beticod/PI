package com.cayetano.biblioteca.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LibroDTOTest {

    @Test
    void testSettersAndGetters() {
        LibroDTO dto = new LibroDTO();

        dto.setId(1L);
        dto.setTitulo("El Quijote");
        dto.setAnioPublicacion(1605);
        dto.setValoracion(10);
        dto.setDescripcion("Obra maestra de la literatura española");
        dto.setImagen("quijote.jpg");
        dto.setAutor("Miguel de Cervantes");
        dto.setEstado("DISPONIBLE");

        assertEquals(1L, dto.getId());
        assertEquals("El Quijote", dto.getTitulo());
        assertEquals(1605, dto.getAnioPublicacion());
        assertEquals(10, dto.getValoracion());
        assertEquals("Obra maestra de la literatura española", dto.getDescripcion());
        assertEquals("quijote.jpg", dto.getImagen());
        assertEquals("Miguel de Cervantes", dto.getAutor());
        assertEquals("DISPONIBLE", dto.getEstado());
    }
}
