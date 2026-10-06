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

import com.mx.cbtis.gestor.modelo.Proyectos;
import com.mx.cbtis.gestor.repository.ProtectosRepository;

@ExtendWith(MockitoExtension.class)
public class ProyectosControllerTest {

    @Mock
    private ProtectosRepository proyectosRepository;

    @InjectMocks
    private ProyectosController proyectosController;

    private Proyectos proyectoMock;

    @BeforeEach
    void setUp() {
        proyectoMock = new Proyectos();
        proyectoMock.setId_proyecto(1);
        proyectoMock.setNombre("Nuevo Proyecto ERP");
        proyectoMock.setEstado_proyecto("Activo");
    }

    @Test
    void testBuscarProyecto_Existente() {
        // Arrange
        when(proyectosRepository.findById(1)).thenReturn(Optional.of(proyectoMock));

        // Act
        Proyectos resultado = proyectosController.getProyecto(1); // Suponiendo que recibe un int

        // Assert
        assertEquals("Nuevo Proyecto ERP", resultado.getNombre(), "Debe retornar el nombre del proyecto si existe");
    }

    @Test
    void testAddProyecto_Exitoso() {
        // Arrange
        when(proyectosRepository.save(any(Proyectos.class))).thenReturn(proyectoMock);

        // Act
        int resultado = proyectosController.addProyecto(proyectoMock);

        // Assert
        assertEquals(1, resultado, "Debe retornar 1 al guardar un proyecto exitosamente");
    }
}
