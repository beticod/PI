import 'package:flutter/material.dart';
import 'package:bbiblioteca/l10n/app_localizations.dart';
import '../services/usuario_libro_service.dart';
import '../models/usuario_libro_dto.dart';
import 'detalle_libro_screen.dart';

/// Pantalla que muestra los libros pendientes del usuario
class PendientesScreen extends StatefulWidget {
  const PendientesScreen({super.key});

  @override
  State<PendientesScreen> createState() => _PendientesScreenState();
}

class _PendientesScreenState extends State<PendientesScreen> {
  final UsuarioLibroService service = UsuarioLibroService();
  late Future<List<UsuarioLibroDTO>> futurePendientes;

  @override
  void initState() {
    super.initState();
    futurePendientes = service.getPendientes(1);
  }

  void refrescar() {
    setState(() {
      futurePendientes = service.getPendientes(1);
    });
  }

  @override
  Widget build(BuildContext context) {
    final t = AppLocalizations.of(context)!;

    return Scaffold(
      appBar: AppBar(title: Text(t.pendingBooks)),
      body: FutureBuilder<List<UsuarioLibroDTO>>(
        future: futurePendientes,
        builder: (context, snapshot) {
          if (!snapshot.hasData) {
            return const Center(child: CircularProgressIndicator());
          }

          final pendientes = snapshot.data!;

          if (pendientes.isEmpty) {
            return Center(child: Text(t.noDescription));
          }

          return ListView.builder(
            itemCount: pendientes.length,
            itemBuilder: (context, index) {
              final libro = pendientes[index];

              return ListTile(
                leading: const Icon(Icons.book_outlined),
                title: Text(libro.titulo),
                subtitle: Text("${t.author}: ${libro.autor}"),
                trailing: const Icon(Icons.hourglass_empty, color: Colors.orange),

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
