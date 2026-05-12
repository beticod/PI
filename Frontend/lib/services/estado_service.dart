import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:flutter_dotenv/flutter_dotenv.dart';

/// Servicio para obtener los estados de libro (LEIDO, LEYENDO, PENDIENTE)
class EstadoService {
  final String baseUrl = dotenv.env['API_URL']!;

  Future<List<dynamic>> getEstados() async {
    final url = Uri.parse("$baseUrl/api/estados");
    final response = await http.get(url);

    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception("Error al cargar estados");
    }
  }
}
