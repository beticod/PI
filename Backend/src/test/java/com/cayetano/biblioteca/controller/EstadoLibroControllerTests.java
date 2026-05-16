package com.cayetano.biblioteca.controller;

import com.cayetano.biblioteca.entity.EstadoLibro;
import com.cayetano.biblioteca.repository.EstadoLibroRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EstadoLibroControllerTest {

    @Test
    void testFindAll() {
        EstadoLibroRepository repository = mock(EstadoLibroRepository.class);
        EstadoLibroController controller = new EstadoLibroController(repository);

        EstadoLibro estado = new EstadoLibro();
        estado.setIdEstado(1L);
        estado.setDescripcion("Disponible");

        when(repository.findAll()).thenReturn(List.of(estado));

        List<EstadoLibro> resultado = controller.findAll();

        assertEquals(1, resultado.size());
        assertEquals("Disponible", resultado.get(0).getDescripcion());
    }

    @Test
    void testSave() {
        EstadoLibroRepository repository = mock(EstadoLibroRepository.class);
        EstadoLibroController controller = new EstadoLibroController(repository);

        EstadoLibro estado = new EstadoLibro();
        estado.setIdEstado(2L);
        estado.setDescripcion("Prestado");

        when(repository.save(estado)).thenReturn(estado);

        EstadoLibro resultado = controller.save(estado);

        assertNotNull(resultado);
        assertEquals("Prestado", resultado.getDescripcion());
        verify(repository, times(1)).save(estado);
    }
}
