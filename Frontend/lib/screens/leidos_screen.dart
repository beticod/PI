import 'package:flutter/material.dart';
import 'package:bbiblioteca/l10n/app_localizations.dart';
import '../services/usuario_libro_service.dart';
import '../models/usuario_libro_dto.dart';
import 'detalle_libro_screen.dart';

/// Pantalla que muestra los libros marcados como Leidos
class LeidosScreen extends StatefulWidget {
  const LeidosScreen({super.key});

  @override
  State<LeidosScreen> createState() => _LeidosScreenState();
}

class _LeidosScreenState extends State<LeidosScreen> {
  final UsuarioLibroService service = UsuarioLibroService();
  late Future<List<UsuarioLibroDTO>> futureLeidos;

  @override
  void initState() {
    super.initState();
    futureLeidos = service.getLeidos(1);
  }

  void refrescar() {
    setState(() {
      futureLeidos = service.getLeidos(1);
    });
  }

  @override
  Widget build(BuildContext context) {
    final t = AppLocalizations.of(context)!;

    return Scaffold(
      appBar: AppBar(title: Text(t.readBooks)),
      body: FutureBuilder<List<UsuarioLibroDTO>>(
        future: futureLeidos,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }

          if (snapshot.hasError) {
            return Center(child: Text("${t.error}: ${snapshot.error}"));
          }

          final leidos = snapshot.data ?? [];

          if (leidos.isEmpty) {
            return Center(child: Text(t.noDescription));
          }

          return ListView.builder(
            itemCount: leidos.length,
            itemBuilder: (context, index) {
              final libro = leidos[index];

              return ListTile(
                leading: const Icon(Icons.book, color: Colors.green),
                title: Text(libro.titulo),
                subtitle: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(libro.autor),
                    const SizedBox(height: 4),
                    Text("${t.read}: ${libro.fechaLectura ?? t.noDescription}"),
                    Text(
                      "${t.rating}: ${libro.puntuacionPersonal ?? '—'} / 10",
                      style: const TextStyle(fontWeight: FontWeight.bold),
                    ),
                  ],
                ),
                isThreeLine: true,
                trailing: const Icon(Icons.check_circle, color: Colors.green),

                /// Navega al detalle del libro
                onTap: () async {
                  final result = await Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (_) => DetalleLibroScreen(libro: libro),
                    ),
                  );

                  if (result == true) {
                    refrescar();
                  }
                },
              );
            },
          );
        },
      ),
    );
  }
}
