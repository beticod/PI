/// DTO que representa la relacion usuario-libro
/// Contiene informacion del libro + estado personal del usuario
class UsuarioLibroDTO {
  final int id;                     // ID de la relacion usuario-libro
  final bool favorito;              // Si el usuario marco el libro como favorito
  final int? puntuacionPersonal;    // Puntuacion del usuario (0–10)
  final String? fechaLectura;       // Fecha en la que terminó de leerlo

  final int idLibro;                // ID del libro
  final String titulo;              // Título del libro
  final String autor;               // Autor del libro
  final String? imagen;             // URL de la imagen

  final String? estadoUsuario;      // Estado del usuario (LEIDO, LEYENDO, PENDIENTE)
  final String? estadoLibro;        // Estado global del libro
  final String? descripcion;        // Descripcion del libro

  UsuarioLibroDTO({
    required this.id,
    required this.favorito,
    this.puntuacionPersonal,
    this.fechaLectura,
    required this.idLibro,
    required this.titulo,
    required this.autor,
    this.imagen,
    this.estadoUsuario,
    this.estadoLibro,
    this.descripcion,
  });

  /// Crea un DTO desde JSON recibido del backend
  factory UsuarioLibroDTO.fromJson(Map<String, dynamic> json) {
    return UsuarioLibroDTO(
      id: json['id'],
      favorito: json['favorito'] ?? false,
      puntuacionPersonal: json['puntuacionPersonal'],
      fechaLectura: json['fechaLectura'],
      idLibro: json['idLibro'],
      titulo: json['titulo'],
      autor: json['autor'],
      imagen: json['imagen'],
      estadoUsuario: json['estadoUsuario'],
      estadoLibro: json['estadoLibro'],
      descripcion: json['descripcion'],
    );
  }

  /// Devuelve una copia del objeto con cambios puntuales
  UsuarioLibroDTO copyWith({
    int? id,
    bool? favorito,
    int? puntuacionPersonal,
    String? fechaLectura,
    int? idLibro,
    String? titulo,
    String? autor,
    String? imagen,
    String? estadoUsuario,
    String? estadoLibro,
    String? descripcion,
  }) {
    return UsuarioLibroDTO(
      id: id ?? this.id,
      favorito: favorito ?? this.favorito,
      puntuacionPersonal: puntuacionPersonal ?? this.puntuacionPersonal,
      fechaLectura: fechaLectura ?? this.fechaLectura,
      idLibro: idLibro ?? this.idLibro,
      titulo: titulo ?? this.titulo,
      autor: autor ?? this.autor,
      imagen: imagen ?? this.imagen,
      estadoUsuario: estadoUsuario ?? this.estadoUsuario,
      estadoLibro: estadoLibro ?? this.estadoLibro,
      descripcion: descripcion ?? this.descripcion,
    );
  }
}
