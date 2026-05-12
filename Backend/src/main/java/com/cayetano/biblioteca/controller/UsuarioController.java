package com.cayetano.biblioteca.controller;

import com.cayetano.biblioteca.dto.UsuarioLibroDTO;
import com.cayetano.biblioteca.entity.Usuario;
import com.cayetano.biblioteca.repository.UsuarioRepository;
import com.cayetano.biblioteca.service.UsuarioLibroService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioLibroService usuarioLibroService;

    public UsuarioController(UsuarioRepository usuarioRepository,
                             UsuarioLibroService usuarioLibroService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioLibroService = usuarioLibroService;
    }

    // GET /api/usuarios → lista todos los usuarios
    @GetMapping
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    // GET /api/usuarios/{id} → obtiene un usuario por ID
    @GetMapping("/{id}")
    public Usuario findById(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // POST /api/usuarios → crea un usuario
    @PostMapping
    public Usuario save(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // PUT /api/usuarios/{id} → actualiza un usuario
    @PutMapping("/{id}")
    public Usuario update(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        existente.setNombre(usuario.getNombre());
        existente.setEmail(usuario.getEmail());

        return usuarioRepository.save(existente);
    }

    // GET /api/usuarios/leyendo/{idUsuario}
    // Devuelve los libros que el usuario está leyendo
    @GetMapping("/leyendo/{idUsuario}")
    public List<UsuarioLibroDTO> getLibrosLeyendo(@PathVariable Long idUsuario) {
        return usuarioLibroService.getLibrosPorEstado(idUsuario, "LEYENDO");
    }

    // DELETE /api/usuarios/{id} → elimina un usuario
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
    }
}
