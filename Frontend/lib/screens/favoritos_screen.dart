import 'package:flutter/material.dart';
import 'package:bbiblioteca/l10n/app_localizations.dart';
import '../services/usuario_libro_service.dart';
import '../models/usuario_libro_dto.dart';
import 'detalle_libro_screen.dart';

/// Pantalla que muestra los libros marcados como favoritos por el usuario
class FavoritosScreen extends StatelessWidget {
  final UsuarioLibroService service = UsuarioLibroService();

  FavoritosScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final t = AppLocalizations.of(context)!;

    return Scaffold(
      appBar: AppBar(title: Text(t.favorites)),

      body: FutureBuilder<List<UsuarioLibroDTO>>(
        future: service.getFavoritos(1),
        builder: (context, snapshot) {

          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }

          if (snapshot.hasError) {
            return Center(child: Text("${t.error}: ${snapshot.error}"));
          }

          final favoritos = snapshot.data ?? [];

          if (favoritos.isEmpty) {
            return Center(child: Text(t.noDescription));
          }

          return ListView.builder(
            itemCount: favoritos.length,
            itemBuilder: (context, index) {
              final libro = favoritos[index];

              return ListTile(
                leading: const Icon(Icons.book),
                title: Text(libro.titulo),
                subtitle: Text(libro.autor),
                trailing: const Icon(Icons.favorite, color: Colors.red),

                /// Navega al detalle del libro
                onTap: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (_) => DetalleLibroScreen(libro: libro),
                    ),
                  );
                },
              );
            },
          );
        },
      ),
    );
  }
}
