import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:flutter_dotenv/flutter_dotenv.dart';
import '../models/usuario_libro_dto.dart';

/// Servicio para gestionar la relacion usuario-libro:
/// favoritos, estados, puntuacion, creacion, etc
class UsuarioLibroService {
  final String baseUrl = dotenv.env['API_URL']!;

  // ============================
  // FAVORITOS
  // ============================
  Future<List<UsuarioLibroDTO>> getFavoritos(int idUsuario) async {
    final url = Uri.parse("$baseUrl/api/usuario-libro/usuario/$idUsuario/favoritos");
    final response = await http.get(url);

    if (response.statusCode == 200) {
      final List data = jsonDecode(response.body);
      return data.map((e) => UsuarioLibroDTO.fromJson(e)).toList();
    } else {
      throw Exception("Error al obtener favoritos");
    }
  }

  // ============================
  // LEIDOS
  // ============================
  Future<List<UsuarioLibroDTO>> getLeidos(int idUsuario) async {
    final url = Uri.parse("$baseUrl/api/usuario-libro/usuario/$idUsuario/leidos");
    final response = await http.get(url);

    if (response.statusCode == 200) {
      final List data = jsonDecode(response.body);
      return data.map((e) => UsuarioLibroDTO.fromJson(e)).toList();
    } else {
      throw Exception("Error al obtener leídos");
    }
  }

  // ============================
  // PENDIENTES
  // ============================
  Future<List<UsuarioLibroDTO>> getPendientes(int idUsuario) async {
    final url = Uri.parse("$baseUrl/api/usuario-libro/usuario/$idUsuario/pendientes");
    final response = await http.get(url);

    if (response.statusCode == 200) {
      final List data = jsonDecode(response.body);
      return data.map((e) => UsuarioLibroDTO.fromJson(e)).toList();
    } else {
      throw Exception("Error al obtener pendientes");
    }
  }

  // ============================
  // EN PROCESO
  // ============================
  Future<List<UsuarioLibroDTO>> getLibrosEnProceso(int idUsuario) async {
    final url = Uri.parse("$baseUrl/api/usuario-libro/usuario/$idUsuario/leyendo");
    final response = await http.get(url);

    if (response.statusCode == 200) {
      final List data = jsonDecode(response.body);
      return data.map((e) => UsuarioLibroDTO.fromJson(e)).toList();
    } else {
      throw Exception("Error al obtener libros en proceso");
    }
  }

  // ============================
  // FAVORITO
  // ============================
  Future<UsuarioLibroDTO> marcarFavorito({
    required int idUsuarioLibro,
    required bool favorito,
  }) async {
    final url = Uri.parse(
      "$baseUrl/api/usuario-libro/$idUsuarioLibro/favorito?favorito=$favorito",
    );

    final response = await http.put(url);

    if (response.statusCode == 200) {
      return UsuarioLibroDTO.fromJson(jsonDecode(response.body));
    } else {
      throw Exception("Error al marcar favorito");
    }
  }

  // ============================
  // RELACION USUARIO-LIBRO
  // ============================
  Future<UsuarioLibroDTO?> obtenerRelacion(int idUsuario, int idLibro) async {
    final url = Uri.parse("$baseUrl/api/usuario-libro/$idUsuario/$idLibro");
    final response = await http.get(url);

    if (response.statusCode == 200) {
      return UsuarioLibroDTO.fromJson(jsonDecode(response.body));
    } else if (response.statusCode == 404) {
      return null;
    } else {
      throw Exception("Error al obtener relación usuario-libro");
    }
  }

  Future<UsuarioLibroDTO> crearRelacion(int idUsuario, int idLibro) async {
    final url = Uri.parse("$baseUrl/api/usuario-libro/$idUsuario/$idLibro");
    final response = await http.post(url);

    if (response.statusCode == 200) {
      return UsuarioLibroDTO.fromJson(jsonDecode(response.body));
    } else {
      throw Exception("Error al crear relación usuario-libro");
    }
  }

  // ============================
  // CAMBIAR ESTADO DEL USUARIO
  // ============================
  Future<void> cambiarEstado({
    required int idUsuarioLibro,
    required int idEstado,
  }) async {
    final url = Uri.parse(
      "$baseUrl/api/usuario-libro/$idUsuarioLibro/estado/$idEstado",
    );

    final response = await http.put(url);

    if (response.statusCode != 200) {
      throw Exception("Error al cambiar estado");
    }
  }
}
