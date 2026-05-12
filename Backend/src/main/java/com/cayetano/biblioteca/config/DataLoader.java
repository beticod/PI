package com.cayetano.biblioteca.config;

import com.cayetano.biblioteca.entity.*;
import com.cayetano.biblioteca.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    // Repositorios necesarios para cargar datos iniciales
    private final AutorRepository autorRepository;
    private final EstadoLibroRepository estadoLibroRepository;
    private final LibroRepository libroRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioLibroRepository usuarioLibroRepository;

    public DataLoader(
            AutorRepository autorRepository,
            EstadoLibroRepository estadoLibroRepository,
            LibroRepository libroRepository,
            UsuarioRepository usuarioRepository,
            UsuarioLibroRepository usuarioLibroRepository
    ) {
        this.autorRepository = autorRepository;
        this.estadoLibroRepository = estadoLibroRepository;
        this.libroRepository = libroRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioLibroRepository = usuarioLibroRepository;
    }

    @Override
    public void run(String... args) {

        // Si ya hay libros, asumimos que los datos iniciales están cargados
        // (más fiable que mirar solo autores)
        if (libroRepository.count() > 0) return;

        // -------------------------
        // AUTORES
        // -------------------------
        Autor autor1 = autorRepository.save(new Autor(null, "Gabriel", "García Márquez", null));
        Autor autor2 = autorRepository.save(new Autor(null, "J.R.R.", "Tolkien", null));

        // -------------------------
        // ESTADOS
        // -------------------------
        // OJO: aquí usas "LEIDO", "LEYENDO", "PENDIENTE"
        // En el frontend usas "Leído", "En proceso", "Pendiente".
        // Lo ideal sería unificar nombres o usar IDs fijos.
        EstadoLibro estLeido = estadoLibroRepository.save(new EstadoLibro(null, "LEIDO"));
        EstadoLibro estLeyendo = estadoLibroRepository.save(new EstadoLibro(null, "LEYENDO"));
        EstadoLibro estPendiente = estadoLibroRepository.save(new EstadoLibro(null, "PENDIENTE"));

        // -------------------------
        // USUARIOS
        // -------------------------
        Usuario user1 = usuarioRepository.save(new Usuario(null, "Juan Pérez", "juan@example.com", null));
        Usuario user2 = usuarioRepository.save(new Usuario(null, "Ana López", "ana@example.com", null));

        // -------------------------
        // LIBROS
        // -------------------------
        Libro libro1 = new Libro();
        libro1.setTitulo("Cien años de soledad");
        libro1.setAnioPublicacion(1967);
        libro1.setValoracion(10);
        libro1.setAutor(autor1);
        libro1.setEstado(estLeido);
        libro1 = libroRepository.save(libro1);

        Libro libro2 = new Libro();
        libro2.setTitulo("El Señor de los Anillos");
        libro2.setAnioPublicacion(1954);
        libro2.setValoracion(9);
        libro2.setAutor(autor2);
        libro2.setEstado(estPendiente);
        libro2 = libroRepository.save(libro2);

        // -------------------------
        // USUARIO-LIBRO
        // -------------------------
        UsuarioLibro ul1 = new UsuarioLibro();
        ul1.setUsuario(user1);
        ul1.setLibro(libro1);
        ul1.setFavorito(true);
        ul1.setPuntuacionPersonal(9);
        ul1.setFechaLectura(LocalDate.now());
        ul1.setEstado(estLeido); // si solo usas estadoUsuario en DTO, este campo podría sobrar

        UsuarioLibro ul2 = new UsuarioLibro();
        ul2.setUsuario(user2);
        ul2.setLibro(libro2);
        ul2.setFavorito(false);
        ul2.setPuntuacionPersonal(8);
        ul2.setFechaLectura(LocalDate.now().minusDays(10));
        ul2.setEstado(estPendiente);

        usuarioLibroRepository.save(ul1);
        usuarioLibroRepository.save(ul2);

        System.out.println("Datos iniciales cargados.");
    }
}
