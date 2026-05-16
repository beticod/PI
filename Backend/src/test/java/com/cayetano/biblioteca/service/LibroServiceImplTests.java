package com.cayetano.biblioteca.service;

import com.cayetano.biblioteca.dto.LibroCreateDTO;
import com.cayetano.biblioteca.entity.*;
import com.cayetano.biblioteca.repository.*;
import com.cayetano.biblioteca.service.impl.LibroServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibroServiceImplTest {

    @Mock
    private LibroRepository libroRepository;

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private EstadoLibroRepository estadoLibroRepository;

    @Mock
    private UsuarioLibroRepository usuarioLibroRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private LibroServiceImpl service;

    // ---------------------------------------------------------
    // findAll
    // ---------------------------------------------------------
    @Test
    void testFindAll() {
        when(libroRepository.findAll()).thenReturn(List.of(new Libro()));

        List<Libro> lista = service.findAll();

        assertEquals(1, lista.size());
    }

    // ---------------------------------------------------------
    // findById
    // ---------------------------------------------------------
    @Test
    void testFindById_Encontrado() {
        Libro libro = new Libro();
        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));

        Libro resultado = service.findById(1L);

        assertEquals(libro, resultado);
    }

    @Test
    void testFindById_NoEncontrado() {
        when(libroRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.findById(1L));
    }

    // ---------------------------------------------------------
    // save
    // ---------------------------------------------------------
    @Test
    void testSave() {
        Libro libro = new Libro();
        when(libroRepository.save(libro)).thenReturn(libro);

        Libro resultado = service.save(libro);

        assertEquals(libro, resultado);
    }

    // ---------------------------------------------------------
    // update
    // ---------------------------------------------------------
    @Test
    void testUpdate() {
        Libro existente = new Libro();
        existente.setTitulo("Viejo");

        Libro nuevo = new Libro();
        nuevo.setTitulo("Nuevo");

        when(libroRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(libroRepository.save(existente)).thenReturn(existente);

        Libro resultado = service.update(1L, nuevo);

        assertEquals("Nuevo", resultado.getTitulo());
    }

    // ---------------------------------------------------------
    // delete
    // ---------------------------------------------------------
    @Test
    void testDelete_Existe() {
        when(libroRepository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(usuarioLibroRepository).deleteByLibroIdLibro(1L);
        verify(libroRepository).deleteById(1L);
    }

    @Test
    void testDelete_NoExiste() {
        when(libroRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> service.delete(1L));
    }

    // ---------------------------------------------------------
    // buscarLibros
    // ---------------------------------------------------------
    @Test
    void testBuscarLibros() {
        when(libroRepository.findByTituloContainingIgnoreCaseOrAutorNombreContainingIgnoreCase("a", "a"))
                .thenReturn(List.of(new Libro()));

        List<Libro> lista = service.buscarLibros("a");

        assertEquals(1, lista.size());
    }

    // ---------------------------------------------------------
    // cambiarEstado
    // ---------------------------------------------------------
    @Test
    void testCambiarEstado() {
        Libro libro = new Libro();
        EstadoLibro estado = new EstadoLibro();

        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));
        when(estadoLibroRepository.findById(2L)).thenReturn(Optional.of(estado));
        when(libroRepository.save(libro)).thenReturn(libro);

        Libro resultado = service.cambiarEstado(1L, 2L);

        assertEquals(estado, resultado.getEstado());
    }

    // ---------------------------------------------------------
    // crearLibro
    // ---------------------------------------------------------
    @Test
    void testCrearLibro() {
        LibroCreateDTO dto = new LibroCreateDTO();
        dto.titulo = "Nuevo Libro";
        dto.anioPublicacion = 2024;
        dto.autorNombre = "Juan Perez";
        dto.estadoId = 3L;
        dto.valoracion = 8;
        dto.descripcion = "Descripción";

        Autor autor = new Autor();
        when(autorRepository.findByNombreIgnoreCaseAndApellidoIgnoreCase("Juan", "Perez"))
                .thenReturn(Optional.of(autor));

        EstadoLibro estado = new EstadoLibro();
        when(estadoLibroRepository.findById(3L)).thenReturn(Optional.of(estado));

        Libro libroGuardado = new Libro();
        when(libroRepository.save(any())).thenReturn(libroGuardado);

        Usuario usuario = new Usuario();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Libro resultado = service.crearLibro(dto);

        assertNotNull(resultado);
        verify(usuarioLibroRepository).save(any());
    }
}
