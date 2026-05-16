package com.cayetano.biblioteca.controller;

import com.cayetano.biblioteca.dto.LibroCreateDTO;
import com.cayetano.biblioteca.dto.LibroDTO;
import com.cayetano.biblioteca.mapper.LibroMapper;
import com.cayetano.biblioteca.service.LibroService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    // GET /api/libros → lista todos los libros (en formato DTO)
    @GetMapping
    public List<LibroDTO> findAll() {
        return libroService.findAll()
                .stream()
                .map(LibroMapper::toDTO)
                .toList();
    }

    // GET /api/libros/{id} → obtiene un libro por id
    @GetMapping("/{id}")
    public LibroDTO findById(@PathVariable Long id) {
        return LibroMapper.toDTO(libroService.findById(id));
    }

    // POST /api/libros → crea un libro a partir de un DTO de creación
    @PostMapping
    public LibroDTO save(@RequestBody LibroCreateDTO dto) {
        return LibroMapper.toDTO(libroService.crearLibro(dto));
    }

    // PUT /api/libros/{id} → actualiza un libro existente
    @PutMapping("/{id}")
    public LibroDTO update(@PathVariable Long id, @RequestBody LibroCreateDTO dto) {
        return LibroMapper.toDTO(libroService.actualizarLibro(id, dto));
    }

    // DELETE /api/libros/{id} → elimina un libro
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        libroService.delete(id);
    }

    // GET /api/libros/buscar?q=texto → búsqueda de libros por título/criterio
    // Si quisieras ser más REST puro, podrías usar GET /api/libros?q=texto
    @GetMapping("/buscar")
    public List<LibroDTO> buscarLibros(@RequestParam String q) {
        return libroService.buscarLibros(q)
                .stream()
                .map(LibroMapper::toDTO)
                .toList();
    }

    // PUT /api/libros/{idLibro}/estado/{idEstado}
    // Cambia el estado del libro (no del usuario-libro).
    // Si en el frontend solo cambias el estado del usuario-libro, este endpoint podría no ser necesario.
    @PutMapping("/{idLibro}/estado/{idEstado}")
    public LibroDTO cambiarEstado(
            @PathVariable Long idLibro,
            @PathVariable Long idEstado
    ) {
        return LibroMapper.toDTO(libroService.cambiarEstado(idLibro, idEstado));
    }
}
