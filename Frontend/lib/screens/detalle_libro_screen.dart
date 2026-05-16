import 'package:flutter/material.dart';
import 'package:bbiblioteca/l10n/app_localizations.dart';
import '../models/usuario_libro_dto.dart';
import '../services/usuario_libro_service.dart';

/// Pantalla de detalle para libros que ya estan asociados al usuario
/// Aquí se gestiona: favorito, estado y visualización basica
class DetalleLibroScreen extends StatefulWidget {
  final UsuarioLibroDTO libro;

  const DetalleLibroScreen({super.key, required this.libro});

  @override
  State<DetalleLibroScreen> createState() => _DetalleLibroScreenState();
}

class _DetalleLibroScreenState extends State<DetalleLibroScreen> {
  late UsuarioLibroDTO _libro;
  final UsuarioLibroService _usuarioLibroService = UsuarioLibroService();

  @override
  void initState() {
    super.initState();
    _libro = widget.libro;
  }

  /// Alterna el estado de favorito
  Future<void> _toggleFavorito() async {
    final nuevoFavorito = !_libro.favorito;

    await _usuarioLibroService.marcarFavorito(
      idUsuarioLibro: _libro.id,
      favorito: nuevoFavorito,
    );

    setState(() {
      _libro = _libro.copyWith(favorito: nuevoFavorito);
    });
  }

  /// Cambia el estado del usuario sobre el libro
  Future<void> _cambiarEstado(String texto, int idEstado) async {
    await _usuarioLibroService.cambiarEstado(
      idUsuarioLibro: _libro.id,
      idEstado: idEstado,
    );

    setState(() {
      _libro = _libro.copyWith(estadoUsuario: texto);
    });

    Navigator.pop(context, true);
  }

  @override
  Widget build(BuildContext context) {
    final t = AppLocalizations.of(context)!;

    return Scaffold(
      appBar: AppBar(title: Text(_libro.titulo)),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [

            /// Título
            Text(
              _libro.titulo,
              style: const TextStyle(fontSize: 22, fontWeight: FontWeight.bold),
            ),

            const SizedBox(height: 8),
            Text("${t.author}: ${_libro.autor}"),

            const SizedBox(height: 8),
            Text("${t.currentState}: ${_libro.estadoUsuario ?? t.noDescription}"),

            const SizedBox(height: 8),
            Text("${t.rating}: ${_libro.puntuacionPersonal ?? '—'} / 10"),

            const SizedBox(height: 20),

            /// Descripcion
            Text(
              t.description,
              style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),

            const SizedBox(height: 8),

            Text(
              _libro.descripcion?.isNotEmpty == true
                  ? _libro.descripcion!
                  : t.noDescription,
              style: const TextStyle(fontSize: 16),
            ),

            const SizedBox(height: 20),

            /// Boton de favorito
            ElevatedButton.icon(
              onPressed: _toggleFavorito,
              icon: Icon(
                _libro.favorito ? Icons.favorite : Icons.favorite_border,
                color: Colors.red,
              ),
              label: Text(
                _libro.favorito
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
