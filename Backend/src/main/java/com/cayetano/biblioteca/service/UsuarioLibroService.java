package com.cayetano.biblioteca.service;

import com.cayetano.biblioteca.dto.UsuarioLibroDTO;
import com.cayetano.biblioteca.entity.UsuarioLibro;

import java.util.List;

public interface UsuarioLibroService {

    UsuarioLibro getOrCreate(Long idUsuario, Long idLibro);

    UsuarioLibro marcarFavorito(Long idUsuarioLibro, boolean favorito);

    List<UsuarioLibro> listarFavoritos(Long idUsuario);

    List<UsuarioLibro> listarLeidos(Long idUsuario);
    List<UsuarioLibro> listarPendientes(Long idUsuario);

    List<UsuarioLibroDTO> getLibrosPorEstado(Long idUsuario, String estado);

    UsuarioLibro cambiarEstado(Long idUsuarioLibro, Long idEstado);

    UsuarioLibro actualizarPuntuacion(Long idUsuarioLibro, Integer puntuacion);
}

