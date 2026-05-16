package com.cayetano.biblioteca.service;

import com.cayetano.biblioteca.dto.UsuarioLibroDTO;
import com.cayetano.biblioteca.entity.*;
import com.cayetano.biblioteca.mapper.UsuarioLibroMapper;
import com.cayetano.biblioteca.repository.*;
import com.cayetano.biblioteca.service.impl.UsuarioLibroServiceImpl;
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
class UsuarioLibroServiceImplTest {

    @Mock
    private UsuarioLibroRepository usuarioLibroRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private LibroRepository libroRepository;

    @Mock
    private EstadoLibroRepository estadoLibroRepository;

    @InjectMocks
    private UsuarioLibroServiceImpl service;

    // ---------------------------------------------------------
    // getOrCreate
    // ---------------------------------------------------------
    @Test
    void testGetOrCreate_DevuelveExistente() {
        UsuarioLibro existente = new UsuarioLibro();
        when(usuarioLibroRepository.findByUsuarioIdUsuarioAndLibroIdLibro(1L, 2L))
                .thenReturn(Optional.of(existente));

        UsuarioLibro resultado = service.getOrCreate(1L, 2L);

        assertEquals(existente, resultado);
        verify(usuarioLibroRepository, never()).save(any());
    }

    @Test
    void testGetOrCreate_CreaNuevo() {
        when(usuarioLibroRepository.findByUsuarioIdUsuarioAndLibroIdLibro(1L, 2L))
                .thenReturn(Optional.empty());

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Libro libro = new Libro();
        libro.setIdLibro(2L);
        when(libroRepository.findById(2L)).thenReturn(Optional.of(libro));

        EstadoLibro estado = new EstadoLibro();
        estado.setIdEstado(1L);
        when(estadoLibroRepository.findById(1L)).thenReturn(Optional.of(estado));

        UsuarioLibro guardado = new UsuarioLibro();
        when(usuarioLibroRepository.save(any())).thenReturn(guardado);

        UsuarioLibro resultado = service.getOrCreate(1L, 2L);

        assertNotNull(resultado);
        verify(usuarioLibroRepository).save(any());
    }

    // ---------------------------------------------------------
    // marcarFavorito
    // ---------------------------------------------------------
    @Test
    void testMarcarFavorito() {
        UsuarioLibro ul = new UsuarioLibro();
        when(usuarioLibroRepository.findById(5L)).thenReturn(Optional.of(ul));
        when(usuarioLibroRepository.save(ul)).thenReturn(ul);

        UsuarioLibro resultado = service.marcarFavorito(5L, true);

        assertTrue(resultado.isFavorito());
        verify(usuarioLibroRepository).save(ul);
    }

    // ---------------------------------------------------------
    // listarFavoritos / listarLeidos / listarPendientes
    // ---------------------------------------------------------
    @Test
    void testListarFavoritos() {
        when(usuarioLibroRepository.findByUsuarioIdUsuarioAndFavoritoTrue(1L))
                .thenReturn(List.of(new UsuarioLibro()));

        List<UsuarioLibro> lista = service.listarFavoritos(1L);

        assertEquals(1, lista.size());
    }

    @Test
    void testListarLeidos() {
        when(usuarioLibroRepository.findByUsuarioIdUsuarioAndEstadoIdEstado(1L, 3L))
                .thenReturn(List.of(new UsuarioLibro()));

        List<UsuarioLibro> lista = service.listarLeidos(1L);

        assertEquals(1, lista.size());
    }

    @Test
    void testListarPendientes() {
        when(usuarioLibroRepository.findByUsuarioIdUsuarioAndEstadoIdEstado(1L, 1L))
                .thenReturn(List.of(new UsuarioLibro()));

        List<UsuarioLibro> lista = service.listarPendientes(1L);

        assertEquals(1, lista.size());
    }

    // ---------------------------------------------------------
    // getLibrosPorEstado
    // ---------------------------------------------------------
    @Test
    void testGetLibrosPorEstado() {
        UsuarioLibro ul = new UsuarioLibro();
        ul.setLibro(new Libro());
        ul.getLibro().setTitulo("Libro Test");

        when(usuarioLibroRepository.findByUsuarioIdUsuarioAndEstadoDescripcion(1L, "LEYENDO"))
                .thenReturn(List.of(ul));

        List<UsuarioLibroDTO> lista = service.getLibrosPorEstado(1L, "LEYENDO");

        assertEquals(1, lista.size());
        assertEquals("Libro Test", lista.get(0).getTitulo());
    }

    // ---------------------------------------------------------
    // cambiarEstado
    // ---------------------------------------------------------
    @Test
    void testCambiarEstado_Leido() {
        UsuarioLibro ul = new UsuarioLibro();
        when(usuarioLibroRepository.findById(10L)).thenReturn(Optional.of(ul));

        EstadoLibro estado = new EstadoLibro();
        estado.setIdEstado(3L);
        when(estadoLibroRepository.findById(3L)).thenReturn(Optional.of(estado));

        when(usuarioLibroRepository.save(ul)).thenReturn(ul);

        UsuarioLibro resultado = service.cambiarEstado(10L, 3L);

        assertEquals(estado, resultado.getEstado());
        assertNotNull(resultado.getFechaLectura());
    }

    @Test
    void testCambiarEstado_NoLeido() {
        UsuarioLibro ul = new UsuarioLibro();
        when(usuarioLibroRepository.findById(10L)).thenReturn(Optional.of(ul));

        EstadoLibro estado = new EstadoLibro();
        estado.setIdEstado(2L);
        when(estadoLibroRepository.findById(2L)).thenReturn(Optional.of(estado));

        when(usuarioLibroRepository.save(ul)).thenReturn(ul);

        UsuarioLibro resultado = service.cambiarEstado(10L, 2L);

        assertEquals(estado, resultado.getEstado());
        assertNull(resultado.getFechaLectura());
    }

    // ---------------------------------------------------------
    // actualizarPuntuacion
    // ---------------------------------------------------------
    @Test
    void testActualizarPuntuacion() {
        UsuarioLibro ul = new UsuarioLibro();
        when(usuarioLibroRepository.findById(7L)).thenReturn(Optional.of(ul));
        when(usuarioLibroRepository.save(ul)).thenReturn(ul);

        UsuarioLibro resultado = service.actualizarPuntuacion(7L, 9);

        assertEquals(9, resultado.getPuntuacionPersonal());
        verify(usuarioLibroRepository).save(ul);
    }
}
