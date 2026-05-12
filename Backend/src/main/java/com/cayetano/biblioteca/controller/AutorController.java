package com.cayetano.biblioteca.controller;

import com.cayetano.biblioteca.entity.Autor;
import com.cayetano.biblioteca.service.AutorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

    private final AutorService autorService;

    // Inyección por constructor: buena práctica
    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    // GET /api/autores → lista todos los autores
    @GetMapping
    public List<Autor> findAll() {
        return autorService.findAll();
    }

    // GET /api/autores/{id} → obtiene un autor por id
    @GetMapping("/{id}")
    public Autor findById(@PathVariable Long id) {
        return autorService.findById(id);
    }

    // POST /api/autores → crea un nuevo autor
    @PostMapping
    public Autor save(@RequestBody Autor autor) {
        return autorService.save(autor);
    }

    // PUT /api/autores/{id} → actualiza un autor existente
    @PutMapping("/{id}")
    public Autor update(@PathVariable Long id, @RequestBody Autor autor) {
        return autorService.update(id, autor);
    }

    // DELETE /api/autores/{id} → elimina un autor
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        autorService.delete(id);
    }
}
