import 'package:flutter/material.dart';
import 'package:bbiblioteca/l10n/app_localizations.dart';
import '../services/libro_service.dart';
import 'detalle_libro_general_screen.dart';

/// Pantalla de busqueda de libros.
/// Permite buscar por titulo
class BusquedaScreen extends StatefulWidget {
  const BusquedaScreen({super.key});

  @override
  _BusquedaScreenState createState() => _BusquedaScreenState();
}

class _BusquedaScreenState extends State<BusquedaScreen> {
  final LibroService service = LibroService();
  final TextEditingController controller = TextEditingController();

  List<dynamic> resultados = [];
  bool buscando = false;

  /// Ejecuta la busqueda llamando al backend.
  Future<void> buscar() async {
    final texto = controller.text.trim();
    if (texto.isEmpty) return;

    setState(() => buscando = true);

    final data = await service.buscarLibros(texto);

    setState(() {
      resultados = data;
      buscando = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    final t = AppLocalizations.of(context)!;

    return Scaffold(
      appBar: AppBar(title: Text(t.searchBooks)),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            /// Campo de busqueda
            TextField(
              controller: controller,
              decoration: InputDecoration(
                labelText: t.searchByTitle,
                suffixIcon: IconButton(
                  icon: const Icon(Icons.search),
                  onPressed: buscar,
                ),
              ),
              onSubmitted: (_) => buscar(),
            ),

            const SizedBox(height: 20),

            /// Indicador de carga
            if (buscando)
              const Center(child: CircularProgressIndicator()),

            /// Lista de resultados
            if (!buscando)
              Expanded(
                child: ListView.builder(
                  itemCount: resultados.length,
                  itemBuilder: (context, index) {
                    final libro = resultados[index];

                    return ListTile(
                      leading: libro["imagen"] != null
                          ? ClipRRect(
                              borderRadius: BorderRadius.circular(6),
                              child: Image.network(
                                libro["imagen"],
                                width: 50,
                                height: 70,
                                fit: BoxFit.cover,
                              ),
                            )
                          : const Icon(Icons.book, size: 40),

                      title: Text(
                        libro["titulo"],
                        style: const TextStyle(fontWeight: FontWeight.bold),
                      ),

                      subtitle: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text("${t.author}: ${libro["autor"]}"),
                          if (libro["estado"] != null)
                            Text("${t.state}: ${libro["estado"]}"),
                          if (libro["descripcion"] != null)
                            Text(
                              libro["descripcion"],
                              maxLines: 1,
                              overflow: TextOverflow.ellipsis,
                            ),
                        ],
                      ),

                      /// Navega al detalle general del libro
                      onTap: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (_) =>
                                DetalleLibroGeneralScreen(libro: libro),
                          ),
                        );
                      },
                    );
                  },
                ),
              ),
          ],
        ),
      ),
    );
  }
}
