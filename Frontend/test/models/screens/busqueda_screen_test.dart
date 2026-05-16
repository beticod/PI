import 'package:flutter_test/flutter_test.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:bbiblioteca/l10n/app_localizations.dart';
import 'package:bbiblioteca/screens/busqueda_screen.dart';
import 'package:bbiblioteca/providers/locale_provider.dart';

Widget wrapWithProviders(Widget child) {
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
    await dotenv.load(fileName: ".env.test");
  });

  testWidgets('La pantalla de búsqueda carga correctamente', (tester) async {
    await tester.pumpWidget(wrapWithProviders(const BusquedaScreen()));

    expect(find.byType(Scaffold), findsOneWidget);
  });
}
