package com.cayetano.biblioteca.service.impl;

import com.cayetano.biblioteca.entity.Autor;
import com.cayetano.biblioteca.repository.AutorRepository;
import com.cayetano.biblioteca.service.AutorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorServiceImpl implements AutorService {

    private final AutorRepository autorRepository;

    public AutorServiceImpl(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    // Devuelve todos los autores
    @Override
    public List<Autor> findAll() {
        return autorRepository.findAll();
    }

    // Busca un autor por ID o lanza excepción si no existe
    @Override
    public Autor findById(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor no encontrado"));
    }

    // Guarda un nuevo autor
    @Override
    public Autor save(Autor autor) {
        return autorRepository.save(autor);
    }

    // Actualiza un autor existente
    @Override
    public Autor update(Long id, Autor autor) {
        Autor existente = findById(id);
        existente.setNombre(autor.getNombre());
        existente.setApellido(autor.getApellido());
        return autorRepository.save(existente);
    }

    // Elimina un autor por ID
    @Override
    public void delete(Long id) {
        autorRepository.deleteById(id);
    }
}
