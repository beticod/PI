package com.cayetano.biblioteca.service.impl;

import com.cayetano.biblioteca.entity.Autor;
import com.cayetano.biblioteca.repository.AutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutorServiceImplTest {

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorServiceImpl service;

    // ---------------------------------------------------------
    // findAll
    // ---------------------------------------------------------
    @Test
    void testFindAll() {
        when(autorRepository.findAll()).thenReturn(List.of(new Autor()));

        List<Autor> lista = service.findAll();

        assertEquals(1, lista.size());
    }

    // ---------------------------------------------------------
    // findById
    // ---------------------------------------------------------
    @Test
    void testFindById_Encontrado() {
        Autor autor = new Autor();
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));

        Autor resultado = service.findById(1L);

        assertEquals(autor, resultado);
    }

    @Test
    void testFindById_NoEncontrado() {
        when(autorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.findById(1L));
    }

    // ---------------------------------------------------------
    // save
    // ---------------------------------------------------------
    @Test
    void testSave() {
        Autor autor = new Autor();
        when(autorRepository.save(autor)).thenReturn(autor);

        Autor resultado = service.save(autor);

        assertEquals(autor, resultado);
    }

    // ---------------------------------------------------------
    // update
    // ---------------------------------------------------------
    @Test
    void testUpdate() {
        Autor existente = new Autor();
        existente.setNombre("Viejo");
        existente.setApellido("ApellidoViejo");

        Autor nuevo = new Autor();
        nuevo.setNombre("Nuevo");
        nuevo.setApellido("ApellidoNuevo");

        when(autorRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(autorRepository.save(existente)).thenReturn(existente);

        Autor resultado = service.update(1L, nuevo);

        assertEquals("Nuevo", resultado.getNombre());
        assertEquals("ApellidoNuevo", resultado.getApellido());
    }

    // ---------------------------------------------------------
    // delete
    // ---------------------------------------------------------
    @Test
    void testDelete() {
        service.delete(1L);

        verify(autorRepository).deleteById(1L);
    }
}
