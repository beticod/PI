package com.cayetano.biblioteca.service;

import com.cayetano.biblioteca.dto.LibroCreateDTO;
import com.cayetano.biblioteca.entity.Libro;

import java.util.List;

public interface LibroService {

    List<Libro> findAll();
    Libro findById(Long id);
    Libro save(Libro libro);
    Libro update(Long id, Libro libro);
    void delete(Long id);

    List<Libro> buscarLibros(String texto);

    Libro crearLibro(LibroCreateDTO dto);

    Libro actualizarLibro(Long id, LibroCreateDTO dto);

    Libro cambiarEstado(Long idLibro, Long idEstado);
}

