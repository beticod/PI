package com.cayetano.biblioteca.repository;

import com.cayetano.biblioteca.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    // Buscar libros por autor
    List<Libro> findByAutorIdAutor(Long idAutor);

    // Buscar libros por estado global
    List<Libro> findByEstadoIdEstado(Long idEstado);

    // Ordenaciones opcionales (elimínalas si no las usas)
    List<Libro> findAllByOrderByValoracionDesc();
    List<Libro> findAllByOrderByValoracionAsc();
    List<Libro> findAllByOrderByTituloAsc();
    List<Libro> findAllByOrderByTituloDesc();

    // Busqueda por titulo
    List<Libro> findByTituloContainingIgnoreCaseOrAutorNombreContainingIgnoreCase(
            String titulo,
            String nombreAutor
    );
}
