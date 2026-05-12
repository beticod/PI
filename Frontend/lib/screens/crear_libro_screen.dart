import 'package:flutter/material.dart';
import 'package:bbiblioteca/l10n/app_localizations.dart';
import '../services/libro_service.dart';
import '../services/estado_service.dart';

/// Pantalla para crear un nuevo libro
/// Envía los datos al backend usando LibroService
class CrearLibroScreen extends StatefulWidget {
  const CrearLibroScreen({super.key});

  @override
  _CrearLibroScreenState createState() => _CrearLibroScreenState();
}

class _CrearLibroScreenState extends State<CrearLibroScreen> {
  final _formKey = GlobalKey<FormState>();

  // Controladores de los campos del formulario
  final tituloController = TextEditingController();
  final fechaController = TextEditingController();
  final autorController = TextEditingController();
  final valoracionController = TextEditingController();
  final descripcionController = TextEditingController();

  final LibroService libroService = LibroService();
  final EstadoService estadoService = EstadoService();

  List<dynamic> estados = [];
  int? estadoSeleccionado;

  @override
  void initState() {
    super.initState();

    // Cargar estados desde el backend
    estadoService.getEstados().then((data) {
      setState(() => estados = data);
    });
  }

  @override
  Widget build(BuildContext context) {
    final t = AppLocalizations.of(context)!;

    return Scaffold(
      appBar: AppBar(title: Text(t.addBook)),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: ListView(
            children: [

              /// Titulo del libro
              TextFormField(
                controller: tituloController,
                decoration: InputDecoration(labelText: t.title),
                validator: (v) => v!.isEmpty ? t.error : null,
              ),

              /// Año de publicacion
              TextFormField(
                controller: fechaController,
                decoration: InputDecoration(labelText: t.year),
                keyboardType: TextInputType.number,
                validator: (v) => v!.isEmpty ? t.error : null,
              ),

              const SizedBox(height: 20),

              /// Autor
              TextFormField(
                controller: autorController,
                decoration: InputDecoration(labelText: t.author),
                validator: (v) => v!.isEmpty ? t.error : null,
              ),

              const SizedBox(height: 20),

              /// Estado inicial del libro
              DropdownButtonFormField<int>(
                value: estadoSeleccionado,
                items: estados.map((e) {
                  return DropdownMenuItem<int>(
                    value: e["idEstado"],
                    child: Text(e["descripcion"]),
                  );
                }).toList(),
                onChanged: (value) {
                  setState(() => estadoSeleccionado = value);
                },
                decoration: InputDecoration(labelText: t.state),
                validator: (v) => v == null ? t.error : null,
              ),

              const SizedBox(height: 20),

              /// Valoracion personal
              TextFormField(
                controller: valoracionController,
                decoration: InputDecoration(labelText: t.rating),
                keyboardType: TextInputType.number,
                validator: (v) {
                  if (v == null || v.isEmpty) return null;
                  final valor = int.tryParse(v);
                  if (valor == null || valor < 0 || valor > 10) return t.error;
                  return null;
                },
              ),

              const SizedBox(height: 20),

              /// Descripcion del libro
              TextFormField(
                controller: descripcionController,
                decoration: InputDecoration(labelText: t.description),
                maxLines: 3,
              ),

              const SizedBox(height: 30),

              /// Boton de guardar
              ElevatedButton(
                onPressed: () async {
                  if (_formKey.currentState!.validate()) {
                    await libroService.crearLibro(
                      titulo: tituloController.text,
                      anio: int.tryParse(fechaController.text),
                      autorNombre: autorController.text,
                      estadoId: estadoSeleccionado,
                      valoracion: int.tryParse(valoracionController.text),
                      descripcion: descripcionController.text,
                    );

                    Navigator.pop(context);
                  }
                },
                child: Text(t.save),
              )
            ],
          ),
        ),
      ),
    );
  }
}
