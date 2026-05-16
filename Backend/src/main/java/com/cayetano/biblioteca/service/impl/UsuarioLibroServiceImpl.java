package com.cayetano.biblioteca.service.impl;

import com.cayetano.biblioteca.dto.UsuarioLibroDTO;
import com.cayetano.biblioteca.entity.EstadoLibro;
import com.cayetano.biblioteca.entity.Libro;
import com.cayetano.biblioteca.entity.Usuario;
import com.cayetano.biblioteca.entity.UsuarioLibro;
import com.cayetano.biblioteca.mapper.UsuarioLibroMapper;
import com.cayetano.biblioteca.repository.EstadoLibroRepository;
import com.cayetano.biblioteca.repository.LibroRepository;
import com.cayetano.biblioteca.repository.UsuarioLibroRepository;
import com.cayetano.biblioteca.repository.UsuarioRepository;
import com.cayetano.biblioteca.service.UsuarioLibroService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UsuarioLibroServiceImpl implements UsuarioLibroService {

    private final UsuarioLibroRepository usuarioLibroRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;
    private final EstadoLibroRepository estadoLibroRepository;

    public UsuarioLibroServiceImpl(
            UsuarioLibroRepository usuarioLibroRepository,
            UsuarioRepository usuarioRepository,
            LibroRepository libroRepository,
            EstadoLibroRepository estadoLibroRepository
    ) {
        this.usuarioLibroRepository = usuarioLibroRepository;
        this.usuarioRepository = usuarioRepository;
        this.libroRepository = libroRepository;
        this.estadoLibroRepository = estadoLibroRepository;
    }

    // Obtiene la relación usuario-libro o la crea si no existe
    @Override
    public UsuarioLibro getOrCreate(Long idUsuario, Long idLibro) {

        return usuarioLibroRepository
                .findByUsuarioIdUsuarioAndLibroIdLibro(idUsuario, idLibro)
                .orElseGet(() -> {

                    Usuario usuario = usuarioRepository.findById(idUsuario)
                            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                    Libro libro = libroRepository.findById(idLibro)
                            .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

                    // Estado inicial → PENDIENTE (id = 1)
                    EstadoLibro estadoPendiente = estadoLibroRepository.findById(1L)
                            .orElseThrow(() -> new RuntimeException("Estado pendiente no encontrado"));

                    UsuarioLibro nuevo = new UsuarioLibro();
                    nuevo.setUsuario(usuario);
                    nuevo.setLibro(libro);
                    nuevo.setFavorito(false);
                    nuevo.setPuntuacionPersonal(null);
                    nuevo.setFechaLectura(null);
                    nuevo.setEstado(estadoPendiente);

                    return usuarioLibroRepository.save(nuevo);
                });
    }

    // Marca o desmarca un libro como favorito
    @Override
    public UsuarioLibro marcarFavorito(Long idUsuarioLibro, boolean favorito) {
        UsuarioLibro ul = usuarioLibroRepository.findById(idUsuarioLibro)
                .orElseThrow(() -> new RuntimeException("No existe la relación usuario-libro"));

        ul.setFavorito(favorito);
        return usuarioLibroRepository.save(ul);
    }

    // Lista de favoritos del usuario
    @Override
    public List<UsuarioLibro> listarFavoritos(Long idUsuario) {
        return usuarioLibroRepository.findByUsuarioIdUsuarioAndFavoritoTrue(idUsuario);
    }

    // Lista de libros leídos (estado = 3)
    @Override
    public List<UsuarioLibro> listarLeidos(Long idUsuario) {
        return usuarioLibroRepository.findByUsuarioIdUsuarioAndEstadoIdEstado(idUsuario, 3L);
    }

    // Lista de libros pendientes (estado = 1)
    @Override
    public List<UsuarioLibro> listarPendientes(Long idUsuario) {
        return usuarioLibroRepository.findByUsuarioIdUsuarioAndEstadoIdEstado(idUsuario, 1L);
    }

    // Lista de libros por estado usando descripción (LEYENDO, LEIDO, PENDIENTE)
    @Override
    public List<UsuarioLibroDTO> getLibrosPorEstado(Long idUsuario, String estado) {
        List<UsuarioLibro> lista =
                usuarioLibroRepository.findByUsuarioIdUsuarioAndEstadoDescripcion(idUsuario, estado);

        return lista.stream()
                .map(UsuarioLibroMapper::toDTO)
                .toList();
    }

    // Cambia el estado del usuario sobre un libro
    @Override
    public UsuarioLibro cambiarEstado(Long idUsuarioLibro, Long idEstado) {

        UsuarioLibro ul = usuarioLibroRepository.findById(idUsuarioLibro)
                .orElseThrow(() -> new RuntimeException("No existe la relación usuario-libro"));

        EstadoLibro estado = estadoLibroRepository.findById(idEstado)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        ul.setEstado(estado);

        // Si pasa a LEÍDO (id = 3), registrar fecha
        if (idEstado == 3) {
            ul.setFechaLectura(LocalDate.now());
        } else {
            ul.setFechaLectura(null);
        }

        return usuarioLibroRepository.save(ul);
    }

    // Actualiza la puntuación personal del usuario
    @Override
    public UsuarioLibro actualizarPuntuacion(Long idUsuarioLibro, Integer puntuacion) {
        UsuarioLibro ul = usuarioLibroRepository.findById(idUsuarioLibro)
                .orElseThrow(() -> new RuntimeException("No existe la relación usuario-libro"));

        ul.setPuntuacionPersonal(puntuacion);

        return usuarioLibroRepository.save(ul);
    }
}
