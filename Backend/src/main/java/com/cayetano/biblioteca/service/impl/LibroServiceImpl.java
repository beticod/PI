package com.cayetano.biblioteca.service.impl;

import com.cayetano.biblioteca.dto.LibroCreateDTO;
import com.cayetano.biblioteca.entity.Autor;
import com.cayetano.biblioteca.entity.EstadoLibro;
import com.cayetano.biblioteca.entity.Libro;
import com.cayetano.biblioteca.entity.UsuarioLibro;
import com.cayetano.biblioteca.repository.AutorRepository;
import com.cayetano.biblioteca.repository.EstadoLibroRepository;
import com.cayetano.biblioteca.repository.LibroRepository;
import com.cayetano.biblioteca.repository.UsuarioLibroRepository;
import com.cayetano.biblioteca.repository.UsuarioRepository;
import com.cayetano.biblioteca.service.LibroService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LibroServiceImpl implements LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final EstadoLibroRepository estadoLibroRepository;
    private final UsuarioLibroRepository usuarioLibroRepository;
    private final UsuarioRepository usuarioRepository;

    public LibroServiceImpl(
            LibroRepository libroRepository,
            AutorRepository autorRepository,
            EstadoLibroRepository estadoLibroRepository,
            UsuarioLibroRepository usuarioLibroRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.estadoLibroRepository = estadoLibroRepository;
        this.usuarioLibroRepository = usuarioLibroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Devuelve todos los libros
    @Override
    public List<Libro> findAll() {
        return libroRepository.findAll();
    }

    // Busca un libro por ID o lanza excepción
    @Override
    public Libro findById(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }

    // Guarda un libro (no se usa desde el frontend)
    @Override
    public Libro save(Libro libro) {
        return libroRepository.save(libro);
    }

    // Actualiza un libro existente
    @Override
    public Libro update(Long id, Libro libro) {
        Libro existente = findById(id);

        existente.setTitulo(libro.getTitulo());
        existente.setAnioPublicacion(libro.getAnioPublicacion());
        existente.setAutor(libro.getAutor());
        existente.setEstado(libro.getEstado());
        existente.setValoracion(libro.getValoracion());
        existente.setDescripcion(libro.getDescripcion());

        return libroRepository.save(existente);
    }

    // Elimina un libro y sus relaciones usuario-libro
    @Override
    @Transactional
    public void delete(Long id) {

        if (!libroRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: el libro no existe");
        }

        // Primero borrar relaciones
        usuarioLibroRepository.deleteByLibroIdLibro(id);

        // Luego borrar el libro
        libroRepository.deleteById(id);
    }

    // Búsqueda por título o autor
    @Override
    public List<Libro> buscarLibros(String texto) {
        return libroRepository
                .findByTituloContainingIgnoreCaseOrAutorNombreContainingIgnoreCase(texto, texto);
    }

    // Cambia el estado global del libro (no del usuario)
    @Override
    public Libro cambiarEstado(Long idLibro, Long idEstado) {
        Libro libro = findById(idLibro);

        EstadoLibro estado = estadoLibroRepository.findById(idEstado)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        libro.setEstado(estado);
        return libroRepository.save(libro);
    }

    // Actualizar libro desde DTO (si no lo usas, puedes eliminarlo)
    @Override
    public Libro actualizarLibro(Long id, LibroCreateDTO dto) {
        throw new UnsupportedOperationException("Método no implementado porque no se usa");
    }

    // Crear libro + relación usuario-libro
    @Override
    public Libro crearLibro(LibroCreateDTO dto) {

        if (dto.autorNombre == null || dto.autorNombre.isBlank()) {
            throw new RuntimeException("El nombre del autor no puede estar vacío");
        }

        // Procesar nombre y apellido
        String[] partes = dto.autorNombre.trim().split(" ", 2);
        String nombre = partes[0];
        String apellido = partes.length > 1 ? partes[1] : "";

        // Buscar autor o crearlo
        Autor autor = autorRepository
                .findByNombreIgnoreCaseAndApellidoIgnoreCase(nombre, apellido)
                .orElseGet(() -> autorRepository.save(new Autor(null, nombre, apellido, null)));

        // Estado del libro
        EstadoLibro estado = estadoLibroRepository.findById(dto.estadoId)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        // Crear libro
        Libro libro = new Libro();
        libro.setTitulo(dto.titulo);
        libro.setAnioPublicacion(dto.anioPublicacion);
        libro.setAutor(autor);
        libro.setEstado(estado);
        libro.setValoracion(dto.valoracion);
        libro.setDescripcion(dto.descripcion);

        libro = libroRepository.save(libro);

        // Crear relacion usuario-libro (usuario 1 por defecto)
        UsuarioLibro ul = new UsuarioLibro();
        ul.setLibro(libro);
        ul.setUsuario(usuarioRepository.findById(1L).orElseThrow());
        ul.setEstado(estado);
        ul.setFavorito(false);
        ul.setPuntuacionPersonal(dto.valoracion);
        ul.setFechaLectura(dto.estadoId == 3 ? LocalDate.now() : null);

        usuarioLibroRepository.save(ul);

        return libro;
    }
}
