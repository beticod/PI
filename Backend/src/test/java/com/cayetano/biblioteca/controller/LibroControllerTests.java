package com.cayetano.biblioteca.controller;

import com.cayetano.biblioteca.dto.LibroCreateDTO;
import com.cayetano.biblioteca.dto.LibroDTO;
import com.cayetano.biblioteca.entity.Libro;
import com.cayetano.biblioteca.mapper.LibroMapper;
import com.cayetano.biblioteca.service.LibroService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LibroControllerTest {

    @Test
    void testFindAll() {
        LibroService service = mock(LibroService.class);
        LibroController controller = new LibroController(service);

        Libro libro = new Libro();
        libro.setIdLibro(1L);
        libro.setTitulo("Libro de prueba");

        when(service.findAll()).thenReturn(List.of(libro));

        List<LibroDTO> resultado = controller.findAll();

        assertEquals(1, resultado.size());
        assertEquals("Libro de prueba", resultado.get(0).getTitulo());
    }

    @Test
    void testFindById() {
        LibroService service = mock(LibroService.class);
        LibroController controller = new LibroController(service);

        Libro libro = new Libro();
        libro.setIdLibro(10L);
        libro.setTitulo("Mi libro");

        when(service.findById(10L)).thenReturn(libro);

        LibroDTO resultado = controller.findById(10L);

        assertEquals("Mi libro", resultado.getTitulo());
    }

    @Test
    void testSave() {
        LibroService service = mock(LibroService.class);
        LibroController controller = new LibroController(service);

        LibroCreateDTO dto = new LibroCreateDTO();
        dto.setTitulo("Nuevo libro");

        Libro libro = new Libro();
        libro.setIdLibro(5L);
        libro.setTitulo("Nuevo libro");

        when(service.crearLibro(dto)).thenReturn(libro);

        LibroDTO resultado = controller.save(dto);

        assertEquals("Nuevo libro", resultado.getTitulo());
        verify(service, times(1)).crearLibro(dto);
    }

    @Test
    void testUpdate() {
        LibroService service = mock(LibroService.class);
        LibroController controller = new LibroController(service);

        LibroCreateDTO dto = new LibroCreateDTO();
        dto.setTitulo("Actualizado");

        Libro libro = new Libro();
        libro.setIdLibro(3L);
        libro.setTitulo("Actualizado");

        when(service.actualizarLibro(3L, dto)).thenReturn(libro);

        LibroDTO resultado = controller.update(3L, dto);

        assertEquals("Actualizado", resultado.getTitulo());
        verify(service).actualizarLibro(3L, dto);
    }

    @Test
    void testDelete() {
        LibroService service = mock(LibroService.class);
        LibroController controller = new LibroController(service);

        controller.delete(7L);

        verify(service, times(1)).delete(7L);
    }

    @Test
    void testBuscarLibros() {
        LibroService service = mock(LibroService.class);
        LibroController controller = new LibroController(service);

        Libro libro = new Libro();
        libro.setIdLibro(1L);
        libro.setTitulo("Harry Potter");

        when(service.buscarLibros("Harry")).thenReturn(List.of(libro));

        List<LibroDTO> resultado = controller.buscarLibros("Harry");

        assertEquals(1, resultado.size());
        assertEquals("Harry Potter", resultado.get(0).getTitulo());
    }

    @Test
    void testCambiarEstado() {
        LibroService service = mock(LibroService.class);
        LibroController controller = new LibroController(service);

        Libro libro = new Libro();
        libro.setIdLibro(1L);
        libro.setTitulo("Libro X");

        when(service.cambiarEstado(1L, 2L)).thenReturn(libro);

        LibroDTO resultado = controller.cambiarEstado(1L, 2L);

        assertEquals("Libro X", resultado.getTitulo());
        verify(service).cambiarEstado(1L, 2L);
    }
}
