import 'package:flutter_test/flutter_test.dart';
import 'package:bbiblioteca/models/usuario_libro_dto.dart';

void main() {
  group('UsuarioLibroDTO', () {
    test('Debe crear una instancia correctamente', () {
      final dto = UsuarioLibroDTO(
        id: 1,
        favorito: true,
        puntuacionPersonal: 5,
        fechaLectura: '2024-01-01',
        idLibro: 10,
        titulo: 'Libro de prueba',
        autor: 'Autor X',
        imagen: 'url.png',
        estadoUsuario: 'LEIDO',
        estadoLibro: 'DISPONIBLE',
      );

      expect(dto.id, 1);
      expect(dto.favorito, true);
      expect(dto.titulo, 'Libro de prueba');
    });

    test('Debe convertir JSON a DTO', () {
      final json = {
        'id': 1,
        'favorito': true,
        'puntuacionPersonal': 4,
        'fechaLectura': '2024-01-01',
        'idLibro': 10,
        'titulo': 'Libro',
        'autor': 'Autor',
        'imagen': 'img.png',
        'estadoUsuario': 'LEYENDO',
        'estadoLibro': 'DISPONIBLE'
      };

      final dto = UsuarioLibroDTO.fromJson(json);

      expect(dto.idLibro, 10);
      expect(dto.estadoUsuario, 'LEYENDO');
    });
  });
}
