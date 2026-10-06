package com.mx.cbtis.gestor.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mx.cbtis.gestor.modelo.Tareas;
import com.mx.cbtis.gestor.repository.TareasRepository;

@ExtendWith(MockitoExtension.class)
public class TareasControllerTest {

    @Mock
    private TareasRepository tareasRepository;

    @InjectMocks
    private TareasController tareasController;

    private Tareas tareaMock;

    @BeforeEach
    void setUp() {
        tareaMock = new Tareas();
        tareaMock.setId_tarea(1);
        tareaMock.setNombre("Diseñar Base de Datos");
        tareaMock.setEstado("Pendiente");
        tareaMock.setId_proyecto(1);
    }

    @Test
    void testBuscarTarea_Existente() {
        // Arrange
        when(tareasRepository.findById(1)).thenReturn(Optional.of(tareaMock));

        // Act
        Tareas resultado = tareasController.getTarea(1); 

        // Assert
        assertEquals("Pendiente", resultado.getEstado(), "Debe retornar el estado de la tarea buscada");
    }

    @Test
    void testAddTareas_Exitoso() {
        // Arrange
        when(tareasRepository.save(any(Tareas.class))).thenReturn(tareaMock);

        // Act
        int resultado = tareasController.addTareas(tareaMock);

        // Assert
        assertEquals(1, resultado, "Debe retornar 1 al guardar una tarea exitosamente");
    }
}
