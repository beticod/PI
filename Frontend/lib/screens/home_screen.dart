import 'package:bbiblioteca/l10n/app_localizations.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../providers/locale_provider.dart';
import 'favoritos_screen.dart';
import 'leidos_screen.dart';
import 'pendientes_screen.dart';
import 'busqueda_screen.dart';
import 'crear_libro_screen.dart';
import 'lista_libros_screen.dart';
import 'en_proceso_screen.dart';

/// Pantalla principal con navegación inferior
/// Contiene todas las secciones de la app
class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  int _selectedIndex = 0;

  /// Lista de pantallas asociadas a cada pestaña del BottomNavigationBar.
  final List<Widget> _screens = [
    FavoritosScreen(),
    LeidosScreen(),
    EnProcesoScreen(),
    PendientesScreen(),
    ListaLibrosScreen(),
    BusquedaScreen(),
  ];

  void _onItemTapped(int index) {
    setState(() => _selectedIndex = index);
  }

  @override
  Widget build(BuildContext context) {
    final t = AppLocalizations.of(context)!;

    return Scaffold(
      appBar: AppBar(
        title: Text(t.appTitle),
        actions: [
          /// Boton para cambiar idioma ES o EN
          IconButton(
            icon: const Icon(Icons.language),
            onPressed: () {
              Provider.of<LocaleProvider>(context, listen: false).toggleLocale();
            },
          ),
        ],
      ),

      /// Pantalla seleccionada
      body: _screens[_selectedIndex],

      /// Botón para crear un nuevo libro
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          Navigator.push(
            context,
            MaterialPageRoute(builder: (_) => const CrearLibroScreen()),
          );
        },
        child: const Icon(Icons.add),
      ),

      /// Barra de navegación inferior
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _selectedIndex,
        onTap: _onItemTapped,
        selectedItemColor: Colors.blue,
        unselectedItemColor: Colors.grey,
        items: [
          BottomNavigationBarItem(
            icon: const Icon(Icons.favorite),
            label: t.favorites,
          ),
          BottomNavigationBarItem(
            icon: const Icon(Icons.check_circle),
            label: t.readBooks,
          ),
          BottomNavigationBarItem(
            icon: const Icon(Icons.menu_book),
            label: t.inProgressBooks,
          ),
          BottomNavigationBarItem(
            icon: const Icon(Icons.hourglass_empty),
            label: t.pendingBooks,
          ),
          BottomNavigationBarItem(
            icon: const Icon(Icons.library_books),
            label: t.books,
          ),
          BottomNavigationBarItem(
            icon: const Icon(Icons.search),
            label: t.searchBooks,
          ),
        ],
      ),
    );
  }
}
