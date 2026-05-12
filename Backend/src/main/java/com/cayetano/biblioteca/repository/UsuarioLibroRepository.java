package com.cayetano.biblioteca.repository;

import com.cayetano.biblioteca.entity.UsuarioLibro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UsuarioLibroRepository extends JpaRepository<UsuarioLibro, Long> {

    // Obtiene la relacion usuario-libro si existe
    Optional<UsuarioLibro> findByUsuarioIdUsuarioAndLibroIdLibro(Long idUsuario, Long idLibro);

    // Libros marcados como favoritos por un usuario
    List<UsuarioLibro> findByUsuarioIdUsuarioAndFavoritoTrue(Long idUsuario);

    // Libros por estado (LEIDO, LEYENDO, PENDIENTE)
    List<UsuarioLibro> findByUsuarioIdUsuarioAndEstadoIdEstado(Long idUsuario, Long idEstado);

    // Buscar por descripción del estado
    List<UsuarioLibro> findByUsuarioIdUsuarioAndEstadoDescripcion(Long idUsuario, String descripcion);

    // Necesario para borrar un libro y sus relaciones
    void deleteByLibroIdLibro(Long idLibro);
}
