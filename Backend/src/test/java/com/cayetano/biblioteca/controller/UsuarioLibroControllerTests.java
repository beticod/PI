package com.cayetano.biblioteca.controller;

import com.cayetano.biblioteca.dto.UsuarioLibroDTO;
import com.cayetano.biblioteca.entity.Autor;
import com.cayetano.biblioteca.entity.EstadoLibro;
import com.cayetano.biblioteca.entity.Libro;
import com.cayetano.biblioteca.entity.Usuario;
import com.cayetano.biblioteca.entity.UsuarioLibro;
import com.cayetano.biblioteca.repository.UsuarioLibroRepository;
import com.cayetano.biblioteca.service.UsuarioLibroService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioLibroControllerTest {

    private UsuarioLibro crearUL(Long idUL) {
        UsuarioLibro ul = new UsuarioLibro();
        ul.setIdUsuarioLibro(idUL);

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setNombre("TestUser");
        ul.setUsuario(usuario);

        Autor autor = new Autor();
        autor.setIdAutor(1L);
        autor.setNombre("Autor Test");

        Libro libro = new Libro();
        libro.setIdLibro(10L);
        libro.setTitulo("Libro Test");
        libro.setAutor(autor);
        ul.setLibro(libro);

        EstadoLibro estado = new EstadoLibro();
        estado.setIdEstado(5L);
        estado.setDescripcion("LEYENDO");
        ul.setEstado(estado);

        return ul;
    }

    @Test
    void testFindAll() {
        UsuarioLibroRepository repo = mock(UsuarioLibroRepository.class);
        UsuarioLibroService service = mock(UsuarioLibroService.class);
        UsuarioLibroController controller = new UsuarioLibroController(repo, service);

        UsuarioLibro ul = crearUL(1L);

        when(repo.findAll()).thenReturn(List.of(ul));

        List<UsuarioLibro> resultado = controller.findAll();

        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getIdUsuarioLibro());
    }

    @Test
    void testObtenerRelacionEncontrada() {
        UsuarioLibroRepository repo = mock(UsuarioLibroRepository.class);
        UsuarioLibroService service = mock(UsuarioLibroService.class);
        UsuarioLibroController controller = new UsuarioLibroController(repo, service);

        UsuarioLibro ul = crearUL(5L);

        when(repo.findByUsuarioIdUsuarioAndLibroIdLibro(1L, 2L))
                .thenReturn(Optional.of(ul));

        ResponseEntity<UsuarioLibroDTO> respuesta = controller.obtenerRelacion(1L, 2L);

        assertEquals(200, respuesta.getStatusCode().value());
        assertNotNull(respuesta.getBody());
    }

    @Test
    void testObtenerRelacionNoEncontrada() {
        UsuarioLibroRepository repo = mock(UsuarioLibroRepository.class);
        UsuarioLibroService service = mock(UsuarioLibroService.class);
        UsuarioLibroController controller = new UsuarioLibroController(repo, service);

        when(repo.findByUsuarioIdUsuarioAndLibroIdLibro(1L, 2L))
                .thenReturn(Optional.empty());

        ResponseEntity<UsuarioLibroDTO> respuesta = controller.obtenerRelacion(1L, 2L);

        assertEquals(404, respuesta.getStatusCode().value());
    }

    @Test
    void testCrearRelacion() {
        UsuarioLibroRepository repo = mock(UsuarioLibroRepository.class);
        UsuarioLibroService service = mock(UsuarioLibroService.class);
        UsuarioLibroController controller = new UsuarioLibroController(repo, service);

        UsuarioLibro ul = crearUL(10L);

        when(service.getOrCreate(1L, 2L)).thenReturn(ul);

        UsuarioLibroDTO resultado = controller.crearRelacion(1L, 2L);

        assertNotNull(resultado);
        verify(service).getOrCreate(1L, 2L);
    }

    @Test
    void testDelete() {
        UsuarioLibroRepository repo = mock(UsuarioLibroRepository.class);
        UsuarioLibroService service = mock(UsuarioLibroService.class);
        UsuarioLibroController controller = new UsuarioLibroController(repo, service);

        controller.delete(7L);

        verify(repo).deleteById(7L);
    }

    @Test
    void testMarcarFavorito() {
        UsuarioLibroRepository repo = mock(UsuarioLibroRepository.class);
        UsuarioLibroService service = mock(UsuarioLibroService.class);
        UsuarioLibroController controller = new UsuarioLibroController(repo, service);

        UsuarioLibro ul = crearUL(3L);

        when(service.marcarFavorito(3L, true)).thenReturn(ul);

        UsuarioLibroDTO resultado = controller.marcarFavorito(3L, true);

        assertNotNull(resultado);
        verify(service).marcarFavorito(3L, true);
    }

    @Test
    void testActualizarPuntuacion() {
        UsuarioLibroRepository repo = mock(UsuarioLibroRepository.class);
        UsuarioLibroService service = mock(UsuarioLibroService.class);
        UsuarioLibroController controller = new UsuarioLibroController(repo, service);

        UsuarioLibro ul = crearUL(4L);

        when(service.actualizarPuntuacion(4L, 8)).thenReturn(ul);

        UsuarioLibroDTO resultado = controller.actualizarPuntuacion(4L, 8);

        assertNotNull(resultado);
        verify(service).actualizarPuntuacion(4L, 8);
    }

    @Test
    void testListarFavoritos() {
        UsuarioLibroRepository repo = mock(UsuarioLibroRepository.class);
        UsuarioLibroService service = mock(UsuarioLibroService.class);
        UsuarioLibroController controller = new UsuarioLibroController(repo, service);

        UsuarioLibro ul = crearUL(1L);

        when(service.listarFavoritos(9L)).thenReturn(List.of(ul));

        List<UsuarioLibroDTO> resultado = controller.listarFavoritos(9L);

        assertEquals(1, resultado.size());
        verify(service).listarFavoritos(9L);
    }

    @Test
    void testListarLeidos() {
        UsuarioLibroRepository repo = mock(UsuarioLibroRepository.class);
        UsuarioLibroService service = mock(UsuarioLibroService.class);
        UsuarioLibroController controller = new UsuarioLibroController(repo, service);

        UsuarioLibro ul = crearUL(1L);

        when(service.listarLeidos(9L)).thenReturn(List.of(ul));

        List<UsuarioLibroDTO> resultado = controller.listarLeidos(9L);

        assertEquals(1, resultado.size());
        verify(service).listarLeidos(9L);
    }

    @Test
    void testListarPendientes() {
        UsuarioLibroRepository repo = mock(UsuarioLibroRepository.class);
        UsuarioLibroService service = mock(UsuarioLibroService.class);
        UsuarioLibroController controller = new UsuarioLibroController(repo, service);

        UsuarioLibro ul = crearUL(1L);

        when(service.listarPendientes(9L)).thenReturn(List.of(ul));

        List<UsuarioLibroDTO> resultado = controller.listarPendientes(9L);

        assertEquals(1, resultado.size());
        verify(service).listarPendientes(9L);
    }

    @Test
    void testListarLeyendo() {
        UsuarioLibroRepository repo = mock(UsuarioLibroRepository.class);
        UsuarioLibroService service = mock(UsuarioLibroService.class);
        UsuarioLibroController controller = new UsuarioLibroController(repo, service);

        UsuarioLibroDTO dto = new UsuarioLibroDTO();
        dto.setId(1L);

        when(service.getLibrosPorEstado(9L, "LEYENDO"))
                .thenReturn(List.of(dto));

        List<UsuarioLibroDTO> resultado = controller.listarLeyendo(9L);

        assertEquals(1, resultado.size());
        verify(service).getLibrosPorEstado(9L, "LEYENDO");
    }

    @Test
    void testCambiarEstado() {
        UsuarioLibroRepository repo = mock(UsuarioLibroRepository.class);
        UsuarioLibroService service = mock(UsuarioLibroService.class);
        UsuarioLibroController controller = new UsuarioLibroController(repo, service);

        UsuarioLibro ul = crearUL(1L);

        when(service.cambiarEstado(1L, 2L)).thenReturn(ul);

        UsuarioLibroDTO resultado = controller.cambiarEstado(1L, 2L);

        assertNotNull(resultado);
        verify(service).cambiarEstado(1L, 2L);
    }
}
