package com.cayetano.biblioteca.repository;

import com.cayetano.biblioteca.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    // Busca un autor por nombre y apellido
    Optional<Autor> findByNombreIgnoreCaseAndApellidoIgnoreCase(String nombre, String apellido);
}
