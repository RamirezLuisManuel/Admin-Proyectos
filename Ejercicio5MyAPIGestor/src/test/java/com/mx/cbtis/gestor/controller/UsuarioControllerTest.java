package com.mx.cbtis.gestor.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mx.cbtis.gestor.modelo.Usuario;
import com.mx.cbtis.gestor.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuarioMock;

    @BeforeEach
    void setUp() {
        usuarioMock = new Usuario();
        usuarioMock.setId_usuario(1);
        usuarioMock.setNombre("Juan Perez");
        usuarioMock.setCorreo_electronico("juan@test.com");
        usuarioMock.setContrasena("12345");
    }

    @Test
    void testGetUsuarioLogin_Exitoso() {
        // Arrange (Preparación)
        Usuario credenciales = new Usuario();
        credenciales.setCorreo_electronico("juan@test.com");
        credenciales.setContrasena("12345");

        when(usuarioRepository.findByCorreoPass(anyString(), anyString()))
                .thenReturn(Optional.of(usuarioMock));

        // Act (Ejecución)
        Usuario resultado = usuarioController.getUsuarioLogin(credenciales);

        // Assert (Validación)
        assertEquals("Juan Perez", resultado.getNombre(), "El nombre debe coincidir si el login es exitoso");
    }

    @Test
    void testGetUsuarioLogin_Fallido() {
        // Arrange (Preparación)
        Usuario credenciales = new Usuario();
        credenciales.setCorreo_electronico("juan@test.com");
        credenciales.setContrasena("incorrecta");

        when(usuarioRepository.findByCorreoPass(anyString(), anyString()))
                .thenReturn(Optional.empty());

        // Act (Ejecución)
        Usuario resultado = usuarioController.getUsuarioLogin(credenciales);

        // Assert (Validación)
        assertEquals("000", resultado.getNombre(), "Si falla el login, debe retornar nombre '000'");
    }
}
