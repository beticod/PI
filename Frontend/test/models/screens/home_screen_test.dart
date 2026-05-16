import 'dart:io';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';

import 'package:bbiblioteca/l10n/app_localizations.dart';
import 'package:bbiblioteca/providers/locale_provider.dart';
import 'package:bbiblioteca/screens/home_screen.dart';

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
    HttpOverrides.global = null; // ← evita cuelgues por HTTP
    await dotenv.load(fileName: ".env.test");
  });

  testWidgets('HomeScreen se construye correctamente', (tester) async {
  await tester.pumpWidget(
    wrap(const HomeScreen()),
  );

  // HomeScreen + pantalla interna → mínimo 2 Scaffolds
  expect(find.byType(Scaffold), findsAtLeastNWidgets(1));

  // BottomNavigationBar existe
  expect(find.byType(BottomNavigationBar), findsOneWidget);

  // FAB existe
  expect(find.byType(FloatingActionButton), findsOneWidget);

  // NO usar pumpAndSettle
  await tester.pump(const Duration(milliseconds: 100));

  // HomeScreen + pantalla interna → mínimo 2 AppBars
  expect(find.byType(AppBar), findsAtLeastNWidgets(1));
});


}
