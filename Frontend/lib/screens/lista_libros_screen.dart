import 'package:flutter/material.dart';
import 'package:bbiblioteca/l10n/app_localizations.dart';
import 'package:bbiblioteca/screens/detalle_libro_general_screen.dart';
import 'package:bbiblioteca/services/libro_service.dart';

/// Pantalla que muestra todos los libros disponibles en el sistema
class ListaLibrosScreen extends StatefulWidget {
  const ListaLibrosScreen({super.key});

  @override
  State<ListaLibrosScreen> createState() => _ListaLibrosScreenState();
}

class _ListaLibrosScreenState extends State<ListaLibrosScreen> {
  final LibroService libroService = LibroService();
  List<dynamic> libros = [];
  bool cargando = true;

  @override
  void initState() {
    super.initState();
    cargarLibros();
  }

  /// Carga todos los libros desde el backend
  Future<void> cargarLibros() async {
    try {
      final data = await libroService.buscarLibros("");
      setState(() {
        libros = data;
        cargando = false;
      });
    } catch (e) {
      setState(() => cargando = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    final t = AppLocalizations.of(context)!;

    return Scaffold(
      appBar: AppBar(title: Text(t.books)),
      body: cargando
          ? const Center(child: CircularProgressIndicator())
          : libros.isEmpty
              ? Center(child: Text(t.noDescription))
              : ListView.builder(
                  itemCount: libros.length,
                  itemBuilder: (context, index) {
                    final libro = libros[index];

                    return ListTile(
                      title: Text(libro["titulo"] ?? t.noDescription),
                      subtitle: Text(
                        libro["autor"] != null
                            ? "${t.author}: ${libro["autor"]}"
                            : t.noDescription,
                      ),

                      /// Navega al detalle general del libro
                      onTap: () async {
                        final result = await Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (_) =>
                                DetalleLibroGeneralScreen(libro: libro),
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
