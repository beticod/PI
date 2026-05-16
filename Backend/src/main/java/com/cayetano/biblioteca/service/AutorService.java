package com.cayetano.biblioteca.service;

import com.cayetano.biblioteca.entity.Autor;
import java.util.List;

public interface AutorService {

    List<Autor> findAll();
    Autor findById(Long id);
    Autor save(Autor autor);
    Autor update(Long id, Autor autor);
    void delete(Long id);
}

