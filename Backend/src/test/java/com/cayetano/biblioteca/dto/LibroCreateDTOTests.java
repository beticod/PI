package com.cayetano.biblioteca.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LibroCreateDTOTest {

    @Test
    void testSettersAndGetters() {
        LibroCreateDTO dto = new LibroCreateDTO();

        dto.setTitulo("Mi libro");
        dto.setAnioPublicacion(2024);
        dto.setAutorNombre("Autor Prueba");
        dto.setEstadoId(3L);
        dto.setValoracion(9);
        dto.setDescripcion("Descripción de prueba");

        assertEquals("Mi libro", dto.getTitulo());
        assertEquals(2024, dto.getAnioPublicacion());
        assertEquals("Autor Prueba", dto.getAutorNombre());
        assertEquals(3L, dto.getEstadoId());
        assertEquals(9, dto.getValoracion());
        assertEquals("Descripción de prueba", dto.getDescripcion());
    }
}
