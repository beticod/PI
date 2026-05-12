import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:bbiblioteca/l10n/app_localizations.dart';

import '../services/usuario_libro_service.dart';
import '../models/usuario_libro_dto.dart';

/// Pantalla que muestra el detalle completo de un libro
/// Incluye: descripcion, estado del usuario, favorito, puntuación y eliminacion
class DetalleLibroGeneralScreen extends StatefulWidget {
  final Map<String, dynamic> libro;

  const DetalleLibroGeneralScreen({super.key, required this.libro});

  @override
  State<DetalleLibroGeneralScreen> createState() =>
      _DetalleLibroGeneralScreenState();
}

class _DetalleLibroGeneralScreenState extends State<DetalleLibroGeneralScreen> {
  UsuarioLibroDTO? relacion;
  final int idUsuario = 1; // Usuario fijo
  final UsuarioLibroService _service = UsuarioLibroService();

  @override
  void initState() {
    super.initState();
    _cargarRelacion();
  }

  /// Carga la relacion usuario-libro o la crea si no existe
  Future<void> _cargarRelacion() async {
    final existente =
        await _service.obtenerRelacion(idUsuario, widget.libro["id"]);

    if (existente != null) {
      setState(() => relacion = existente);
    } else {
      final creada =
          await _service.crearRelacion(idUsuario, widget.libro["id"]);
      setState(() => relacion = creada);
    }
  }

  /// Alterna el estado de favorito
  Future<void> _toggleFavorito() async {
    final nuevo = !relacion!.favorito;

    await _service.marcarFavorito(
      idUsuarioLibro: relacion!.id,
      favorito: nuevo,
    );

    setState(() {
      relacion = relacion!.copyWith(favorito: nuevo);
    });
  }

  /// Cambia el estado del usuario sobre el libro
  Future<void> _cambiarEstado(String texto, int idEstado) async {
    await _service.cambiarEstado(
      idUsuarioLibro: relacion!.id,
      idEstado: idEstado,
    );

    setState(() {
      relacion = relacion!.copyWith(estadoUsuario: texto);
    });

    Navigator.pop(context, true);
  }

  /// Elimina el libro completamente del sistema
  Future<void> _eliminarLibro() async {
    final t = AppLocalizations.of(context)!;
    final idLibro = widget.libro["id"];
    final url = Uri.parse("${dotenv.env['API_URL']}/api/libros/$idLibro");

    final response = await http.delete(url);

    if (response.statusCode == 200 || response.statusCode == 204) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text(t.deleteBook)),
      );
      Navigator.pop(context, true);
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text(t.error)),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    final t = AppLocalizations.of(context)!;

    if (relacion == null) {
      return Scaffold(
        appBar: AppBar(title: Text(widget.libro["titulo"])),
        body: Center(child: Text(t.loading)),
      );
    }

    return Scaffold(
      appBar: AppBar(title: Text(relacion!.titulo)),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [

            /// Imagen del libro
            if (widget.libro["imagen"] != null)
              Center(
                child: ClipRRect(
                  borderRadius: BorderRadius.circular(8),
                  child: Image.network(
                    widget.libro["imagen"],
                    height: 220,
                    fit: BoxFit.cover,
                  ),
                ),
              ),

            const SizedBox(height: 20),

            /// Título
            Text(
              relacion!.titulo,
              style: const TextStyle(fontSize: 22, fontWeight: FontWeight.bold),
            ),

            const SizedBox(height: 8),
            Text("${t.author}: ${relacion!.autor}"),
            const SizedBox(height: 8),
            Text("${t.currentState}: ${relacion!.estadoUsuario ?? t.noDescription}"),
            const SizedBox(height: 8),
            Text("${t.rating}: ${relacion!.puntuacionPersonal ?? '—'} / 10"),

            const SizedBox(height: 20),

            /// Descripcion
            Text(
              t.description,
              style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),

            const SizedBox(height: 8),

            Text(
              widget.libro["descripcion"]?.isNotEmpty == true
                  ? widget.libro["descripcion"]
                  : t.noDescription,
              style: const TextStyle(fontSize: 16),
            ),

            const SizedBox(height: 20),

            /// Boton de favorito
            ElevatedButton.icon(
              onPressed: _toggleFavorito,
              icon: Icon(
                relacion!.favorito ? Icons.favorite : Icons.favorite_border,
                color: Colors.red,
              ),
              label: Text(
                relacion!.favorito
                    ? t.removeFromFavorites
                    : t.addToFavorites,
              ),
            ),

            const SizedBox(height: 20),

            /// Cambio de estado
            Text(
              t.changeState,
              style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),

            const SizedBox(height: 10),

            Wrap(
              spacing: 10,
              children: [
                _estadoButton(t.pending, 1),
                _estadoButton(t.inProgress, 2),
                _estadoButton(t.read, 3),
              ],
            ),

            const SizedBox(height: 30),

            /// Boton de eliminar libro
            ElevatedButton(
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.red,
              ),
              onPressed: _eliminarLibro,
              child: Text(t.deleteBook),
            ),
          ],
        ),
      ),
    );
  }

  /// Boton reutilizable para cambiar estado
  Widget _estadoButton(String texto, int idEstado) {
    return ElevatedButton(
      onPressed: () => _cambiarEstado(texto, idEstado),
      child: Text(texto),
    );
  }
}
