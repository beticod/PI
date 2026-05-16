import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:flutter_dotenv/flutter_dotenv.dart';

/// Servicio para obtener autores desde el backend
class AutorService {
  final String baseUrl = dotenv.env['API_URL']!;

  /// Devuelve la lista de autores
  Future<List<dynamic>> getAutores() async {
    final url = Uri.parse("$baseUrl/api/autores");
    final response = await http.get(url);

    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception("Error al cargar autores");
    }
  }
}
