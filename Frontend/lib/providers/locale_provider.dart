import 'package:flutter/material.dart';

/// Provider encargado de gestionar el idioma de la aplicacion
/// Alterna entre español e ingles
class LocaleProvider extends ChangeNotifier {
  Locale _locale = const Locale('es');

  Locale get locale => _locale;

  /// Cambia entre ES o EN y notifica a los listeners
  void toggleLocale() {
    _locale = _locale.languageCode == 'es'
        ? const Locale('en')
        : const Locale('es');

    notifyListeners();
  }
}
