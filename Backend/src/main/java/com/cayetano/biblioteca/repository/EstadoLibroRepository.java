package com.cayetano.biblioteca.repository;

import com.cayetano.biblioteca.entity.EstadoLibro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoLibroRepository extends JpaRepository<EstadoLibro, Long> {
}
