package com.cayetano.biblioteca.controller;

import com.cayetano.biblioteca.entity.Autor;
import com.cayetano.biblioteca.service.AutorService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutorControllerTest {

    @Test
    void testFindAll() {
        AutorService autorService = mock(AutorService.class);
        AutorController controller = new AutorController(autorService);

        Autor autor = new Autor();
        autor.setIdAutor(1L);
        autor.setNombre("Autor de prueba");

        when(autorService.findAll()).thenReturn(List.of(autor));

        List<Autor> resultado = controller.findAll();

        assertEquals(1, resultado.size());
        assertEquals("Autor de prueba", resultado.get(0).getNombre());
    }
}
