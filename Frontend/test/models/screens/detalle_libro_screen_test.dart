import 'package:flutter_test/flutter_test.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';

import 'package:bbiblioteca/l10n/app_localizations.dart';
import 'package:bbiblioteca/providers/locale_provider.dart';
import 'package:bbiblioteca/screens/detalle_libro_screen.dart';
import 'package:bbiblioteca/models/usuario_libro_dto.dart';

Widget wrap(Widget child) {
  return MultiProvider(
    providers: [
      ChangeNotifierProvider(create: (_) => LocaleProvider()),
    ],
    child: MaterialApp(
      home: child,
      localizationsDelegates: const [
        AppLocalizations.delegate,
        GlobalMaterialLocalizations.delegate,
        GlobalWidgetsLocalizations.delegate,
        GlobalCupertinoLocalizations.delegate,
      ],
      supportedLocales: const [
        Locale('es'),
        Locale('en'),
      ],
    ),
  );
}

void main() {
  setUpAll(() async {
    await dotenv.load(fileName: ".env.test"); // ← ESTO ES LO QUE FALTABA
  });

  testWidgets('DetalleLibroScreen se construye correctamente', (tester) async {
    final libroFake = UsuarioLibroDTO(
      id: 1,
      idLibro: 99,
      titulo: "Libro de prueba",
      autor: "Autor de prueba",
      estadoUsuario: "Pendiente",
      puntuacionPersonal: 7,
      descripcion: "Descripción de prueba",
      favorito: false,
    );

    await tester.pumpWidget(
      wrap(
        DetalleLibroScreen(libro: libroFake),
      ),
    );

    await tester.pumpAndSettle();

    expect(find.byType(Scaffold), findsOneWidget);
    expect(find.text("Libro de prueba"), findsWidgets);
    expect(find.textContaining("Autor de prueba"), findsOneWidget);
    expect(find.textContaining("Pendiente"), findsOneWidget);
    expect(find.textContaining("7"), findsOneWidget);
    expect(find.byIcon(Icons.favorite_border), findsOneWidget);
  });
}
