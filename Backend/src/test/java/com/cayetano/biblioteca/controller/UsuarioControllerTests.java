package com.cayetano.biblioteca.controller;

import com.cayetano.biblioteca.dto.UsuarioLibroDTO;
import com.cayetano.biblioteca.entity.Usuario;
import com.cayetano.biblioteca.repository.UsuarioRepository;
import com.cayetano.biblioteca.service.UsuarioLibroService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Test
    void testFindAll() {
        UsuarioRepository repo = mock(UsuarioRepository.class);
        UsuarioLibroService usuarioLibroService = mock(UsuarioLibroService.class);
        UsuarioController controller = new UsuarioController(repo, usuarioLibroService);

        Usuario u = new Usuario();
        u.setIdUsuario(1L);
        u.setNombre("Juan");

        when(repo.findAll()).thenReturn(List.of(u));

        List<Usuario> resultado = controller.findAll();

        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
    }

    @Test
    void testFindById() {
        UsuarioRepository repo = mock(UsuarioRepository.class);
        UsuarioLibroService usuarioLibroService = mock(UsuarioLibroService.class);
        UsuarioController controller = new UsuarioController(repo, usuarioLibroService);

        Usuario u = new Usuario();
        u.setIdUsuario(10L);
        u.setNombre("Ana");

        when(repo.findById(10L)).thenReturn(Optional.of(u));

        Usuario resultado = controller.findById(10L);

        assertEquals("Ana", resultado.getNombre());
    }

    @Test
    void testSave() {
        UsuarioRepository repo = mock(UsuarioRepository.class);
        UsuarioLibroService usuarioLibroService = mock(UsuarioLibroService.class);
        UsuarioController controller = new UsuarioController(repo, usuarioLibroService);

        Usuario u = new Usuario();
        u.setNombre("Nuevo");

        when(repo.save(u)).thenReturn(u);

        Usuario resultado = controller.save(u);

        assertEquals("Nuevo", resultado.getNombre());
        verify(repo, times(1)).save(u);
    }

    @Test
    void testUpdate() {
        UsuarioRepository repo = mock(UsuarioRepository.class);
        UsuarioLibroService usuarioLibroService = mock(UsuarioLibroService.class);
        UsuarioController controller = new UsuarioController(repo, usuarioLibroService);

        Usuario existente = new Usuario();
        existente.setIdUsuario(5L);
        existente.setNombre("Viejo");
        existente.setEmail("viejo@mail.com");

        Usuario cambios = new Usuario();
        cambios.setNombre("Nuevo");
        cambios.setEmail("nuevo@mail.com");

        when(repo.findById(5L)).thenReturn(Optional.of(existente));
        when(repo.save(existente)).thenReturn(existente);

        Usuario resultado = controller.update(5L, cambios);

        assertEquals("Nuevo", resultado.getNombre());
        assertEquals("nuevo@mail.com", resultado.getEmail());
        verify(repo).save(existente);
    }

    @Test
void testGetLibrosLeyendo() {
    UsuarioRepository repo = mock(UsuarioRepository.class);
    UsuarioLibroService usuarioLibroService = mock(UsuarioLibroService.class);
    UsuarioController controller = new UsuarioController(repo, usuarioLibroService);

    UsuarioLibroDTO dto = new UsuarioLibroDTO();
    dto.setId(1L);

    when(usuarioLibroService.getLibrosPorEstado(3L, "LEYENDO"))
            .thenReturn(List.of(dto));

    List<UsuarioLibroDTO> resultado = controller.getLibrosLeyendo(3L);

    assertEquals(1, resultado.size());
    verify(usuarioLibroService).getLibrosPorEstado(3L, "LEYENDO");
}


    @Test
    void testDelete() {
        UsuarioRepository repo = mock(UsuarioRepository.class);
        UsuarioLibroService usuarioLibroService = mock(UsuarioLibroService.class);
        UsuarioController controller = new UsuarioController(repo, usuarioLibroService);

        controller.delete(8L);

        verify(repo, times(1)).deleteById(8L);
    }
}
