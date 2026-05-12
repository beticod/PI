package com.cayetano.biblioteca.controller;

import com.cayetano.biblioteca.entity.EstadoLibro;
import com.cayetano.biblioteca.repository.EstadoLibroRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estados")
public class EstadoLibroController {

    private final EstadoLibroRepository estadoLibroRepository;

    public EstadoLibroController(EstadoLibroRepository estadoLibroRepository) {
        this.estadoLibroRepository = estadoLibroRepository;
    }

    // GET /api/estados → lista todos los estados
    @GetMapping
    public List<EstadoLibro> findAll() {
        return estadoLibroRepository.findAll();
    }

    // POST /api/estados → crea un nuevo estado
    // Si los estados son fijos y solo los cargas en DataLoader, podrías eliminar este endpoint.
    @PostMapping
    public EstadoLibro save(@RequestBody EstadoLibro estado) {
        return estadoLibroRepository.save(estado);
    }
}
