package com.cayetano.biblioteca.controller;

import com.cayetano.biblioteca.dto.UsuarioLibroDTO;
import com.cayetano.biblioteca.entity.UsuarioLibro;
import com.cayetano.biblioteca.mapper.UsuarioLibroMapper;
import com.cayetano.biblioteca.repository.UsuarioLibroRepository;
import com.cayetano.biblioteca.service.UsuarioLibroService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario-libro")
public class UsuarioLibroController {

    private final UsuarioLibroRepository usuarioLibroRepository;
    private final UsuarioLibroService usuarioLibroService;

    public UsuarioLibroController(UsuarioLibroRepository usuarioLibroRepository,
                                  UsuarioLibroService usuarioLibroService) {
        this.usuarioLibroRepository = usuarioLibroRepository;
        this.usuarioLibroService = usuarioLibroService;
    }

    // GET /api/usuario-libro → lista todas las relaciones
    @GetMapping
    public List<UsuarioLibro> findAll() {
        return usuarioLibroRepository.findAll();
    }

    // GET /api/usuario-libro/{idUsuario}/{idLibro}
    // Obtiene la relación usuario-libro si existe
    @GetMapping("/{idUsuario}/{idLibro}")
    public ResponseEntity<UsuarioLibroDTO> obtenerRelacion(
            @PathVariable Long idUsuario,
            @PathVariable Long idLibro) {

        return usuarioLibroRepository
                .findByUsuarioIdUsuarioAndLibroIdLibro(idUsuario, idLibro)
                .map(ul -> ResponseEntity.ok(UsuarioLibroMapper.toDTO(ul)))
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/usuario-libro/{idUsuario}/{idLibro}
    // Crea la relación si no existe
    @PostMapping("/{idUsuario}/{idLibro}")
    public UsuarioLibroDTO crearRelacion(
            @PathVariable Long idUsuario,
            @PathVariable Long idLibro) {

        UsuarioLibro ul = usuarioLibroService.getOrCreate(idUsuario, idLibro);
        return UsuarioLibroMapper.toDTO(ul);
    }

    // DELETE /api/usuario-libro/{id} → elimina relación
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        usuarioLibroRepository.deleteById(id);
    }

    // PUT /favorito → marca o desmarca favorito
    @PutMapping("/{idUsuarioLibro}/favorito")
    public UsuarioLibroDTO marcarFavorito(
            @PathVariable Long idUsuarioLibro,
            @RequestParam boolean favorito) {

        UsuarioLibro ul = usuarioLibroService.marcarFavorito(idUsuarioLibro, favorito);
        return UsuarioLibroMapper.toDTO(ul);
    }

    // PUT /puntuacion → actualiza puntuación personal
    @PutMapping("/{idUsuarioLibro}/puntuacion")
    public UsuarioLibroDTO actualizarPuntuacion(
            @PathVariable Long idUsuarioLibro,
            @RequestParam Integer puntuacion) {

        UsuarioLibro ul = usuarioLibroService.actualizarPuntuacion(idUsuarioLibro, puntuacion);
        return UsuarioLibroMapper.toDTO(ul);
    }

    // GET favoritos, leídos, pendientes, leyendo
    @GetMapping("/usuario/{idUsuario}/favoritos")
    public List<UsuarioLibroDTO> listarFavoritos(@PathVariable Long idUsuario) {
        return usuarioLibroService.listarFavoritos(idUsuario)
                .stream()
                .map(UsuarioLibroMapper::toDTO)
                .toList();
    }

    @GetMapping("/usuario/{idUsuario}/leidos")
    public List<UsuarioLibroDTO> listarLeidos(@PathVariable Long idUsuario) {
        return usuarioLibroService.listarLeidos(idUsuario)
                .stream()
                .map(UsuarioLibroMapper::toDTO)
                .toList();
    }

    @GetMapping("/usuario/{idUsuario}/pendientes")
    public List<UsuarioLibroDTO> listarPendientes(@PathVariable Long idUsuario) {
        return usuarioLibroService.listarPendientes(idUsuario)
                .stream()
                .map(UsuarioLibroMapper::toDTO)
                .toList();
    }

    @GetMapping("/usuario/{idUsuario}/leyendo")
    public List<UsuarioLibroDTO> listarLeyendo(@PathVariable Long idUsuario) {
        return usuarioLibroService.getLibrosPorEstado(idUsuario, "LEYENDO");
    }

    // PUT /estado → cambia el estado del usuario-libro
    @PutMapping("/{idUsuarioLibro}/estado/{idEstado}")
    public UsuarioLibroDTO cambiarEstado(
            @PathVariable Long idUsuarioLibro,
            @PathVariable Long idEstado) {

        UsuarioLibro ul = usuarioLibroService.cambiarEstado(idUsuarioLibro, idEstado);
        return UsuarioLibroMapper.toDTO(ul);
    }
}
