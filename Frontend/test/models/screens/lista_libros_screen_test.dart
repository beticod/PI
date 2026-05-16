import 'dart:io';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';

import 'package:bbiblioteca/l10n/app_localizations.dart';
import 'package:bbiblioteca/providers/locale_provider.dart';
import 'package:bbiblioteca/screens/lista_libros_screen.dart';

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
    HttpOverrides.global = null; // evita cuelgues por HTTP
    await dotenv.load(fileName: ".env.test");
  });

  testWidgets('ListaLibrosScreen se construye correctamente', (tester) async {
    await tester.pumpWidget(
      wrap(const ListaLibrosScreen()),
    );

    // loading visible
    expect(find.byType(CircularProgressIndicator), findsOneWidget);

    // NO usar pumpAndSettle → se cuelga
    await tester.pump(const Duration(milliseconds: 100));

    // La pantalla existe
    expect(find.byType(Scaffold), findsOneWidget);

    // El AppBar existe
    expect(find.byType(AppBar), findsAtLeastNWidgets(1));
  });
}
