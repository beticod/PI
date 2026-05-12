import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:flutter_dotenv/flutter_dotenv.dart';

/// Servicio para gestionar libros busqueda, creacion y cambio de estado
class LibroService {
  final String baseUrl = dotenv.env['API_URL']!;

  // ============================
  // BUSCAR LIBROS
  // ============================
  Future<List<dynamic>> buscarLibros(String texto) async {
    try {
      final encoded = Uri.encodeQueryComponent(texto);
      final url = Uri.parse("$baseUrl/api/libros/buscar?q=$encoded");

      final response = await http.get(url);

      if (response.statusCode == 200) {
        final body = jsonDecode(response.body);

        if (body is List) return body;

        throw Exception("Formato inesperado en la respuesta del servidor");
      } else {
        throw Exception("Error al buscar libros: código ${response.statusCode}");
      }
    } catch (e) {
      throw Exception("Error en buscarLibros: $e");
    }
  }

  // ============================
  // CREAR LIBRO
  // ============================
  Future<void> crearLibro({
    required String titulo,
    int? anio,
    String? autorNombre,
    int? estadoId,
    int? valoracion,
    String? descripcion,
  }) async {
    final url = Uri.parse("$baseUrl/api/libros");

    final body = {
      "titulo": titulo,
      "anioPublicacion": anio,
      "autorNombre": autorNombre,
      "estadoId": estadoId,
      "valoracion": valoracion,
      "descripcion": descripcion,
    };

    final response = await http.post(
      url,
      headers: {"Content-Type": "application/json"},
      body: jsonEncode(body),
    );

    if (response.statusCode != 200 && response.statusCode != 201) {
      throw Exception("Error al crear libro: código ${response.statusCode}");
    }
  }

  // ============================
  // CAMBIAR ESTADO GLOBAL DEL LIBRO
  // ============================
  Future<void> cambiarEstado({
    required int idLibro,
    required int idEstado,
  }) async {
    final url = Uri.parse("$baseUrl/api/libros/$idLibro/estado/$idEstado");

    final response = await http.put(url);

    if (response.statusCode != 200) {
      throw Exception("Error al cambiar estado del libro");
    }
  }
}
