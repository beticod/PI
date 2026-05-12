import 'package:flutter/material.dart';
import 'package:bbiblioteca/l10n/app_localizations.dart';
import '../services/usuario_libro_service.dart';
import '../models/usuario_libro_dto.dart';
import 'detalle_libro_screen.dart';

/// Pantalla que muestra los libros que el usuario tiene En proceso
class EnProcesoScreen extends StatefulWidget {
  const EnProcesoScreen({super.key});

  @override
  State<EnProcesoScreen> createState() => _EnProcesoScreenState();
}

class _EnProcesoScreenState extends State<EnProcesoScreen> {
  final UsuarioLibroService _service = UsuarioLibroService();
  List<UsuarioLibroDTO> _libros = [];
  bool _cargando = true;

  @override
  void initState() {
    super.initState();
    cargarLibros();
  }

  /// Carga los libros en proceso desde el backend
  Future<void> cargarLibros() async {
    try {
      final datos = await _service.getLibrosEnProceso(1);
      setState(() {
        _libros = datos;
        _cargando = false;
      });
    } catch (e) {
      setState(() => _cargando = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    final t = AppLocalizations.of(context)!;

    return Scaffold(
      appBar: AppBar(title: Text(t.inProgressBooks)),
      body: _cargando
          ? const Center(child: CircularProgressIndicator())
          : _libros.isEmpty
              ? Center(child: Text(t.noDescription))
              : ListView.builder(
                  itemCount: _libros.length,
                  itemBuilder: (context, index) {
                    final libro = _libros[index];

                    return ListTile(
                      leading: const Icon(Icons.menu_book),
                      title: Text(libro.titulo),
                      subtitle: Text("${t.author}: ${libro.autor}"),

                      /// Navega al detalle del libro
                      onTap: () async {
                        final result = await Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (_) => DetalleLibroScreen(libro: libro),
                          ),
                        );

                        if (result == true) {
                          await cargarLibros();
                        }
                      },
                    );
                  },
                ),
    );
  }
}
