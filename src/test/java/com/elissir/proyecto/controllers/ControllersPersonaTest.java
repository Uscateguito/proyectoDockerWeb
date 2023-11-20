package com.elissir.proyecto.controllers;

import com.elissir.proyecto.entidades.Persona;
import com.elissir.proyecto.services.PersonaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ControllersPersonaTest {

    @InjectMocks
    private ControllersPersona controllersPersona;

    @Mock
    private PersonaService personaService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // Esto simula un entorno de servidor web donde se puede realizar una solicitud HTTP al controlador
        mockMvc = MockMvcBuilders.standaloneSetup(controllersPersona).build();
    }

    @Test
    public void testCrearPersona() throws Exception {
        // Crea una persona de ejemplo
        Persona personaEjemplo = new Persona("Nombre Ejemplo", "Apellido Ejemplo", "Correo Ejemplo", "Contrasenia Ejemplo");

        // Configura el comportamiento simulado del servicio
        when(personaService.crearPersona(any(Persona.class))).thenReturn(personaEjemplo);

        // Realiza una solicitud POST simulada al controlador
        mockMvc.perform(post("/persona/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Nombre Ejemplo\",\"apellido\":\"Apellido Ejemplo\",\"correo_electronico\":\"Correo Ejemplo\",\"contrasenia\":\"Contrasenia Ejemplo\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre").value("Nombre Ejemplo"))
                .andExpect(jsonPath("$.apellido").value("Apellido Ejemplo"))
                .andExpect(jsonPath("$.correo_electronico").value("Correo Ejemplo"));

        verify(personaService, times(1)).crearPersona(any(Persona.class));
    }

    @Test
    public void testListarPersona() throws Exception {

        List<Persona> personaEjemplo = List.of(
                new Persona("Nombre Ejemplo1", "Apellido Ejemplo1", "Correo Ejemplo1", "Contrasenia Ejemplo1"),
                new Persona("Nombre Ejemplo2", "Apellido Ejemplo2", "Correo Ejemplo2", "Contrasenia Ejemplo2"),
                new Persona("Nombre Ejemplo3", "Apellido Ejemplo3", "Correo Ejemplo3", "Contrasenia Ejemplo3")
        );

        when(personaService.listarPersonas()).thenReturn(personaEjemplo);

        mockMvc.perform(MockMvcRequestBuilders.get("/persona/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre").value("Nombre Ejemplo1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nombre").value("Nombre Ejemplo2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].nombre").value("Nombre Ejemplo3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].apellido").value("Apellido Ejemplo1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].apellido").value("Apellido Ejemplo2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].apellido").value("Apellido Ejemplo3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].correo_electronico").value("Correo Ejemplo1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].correo_electronico").value("Correo Ejemplo2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].correo_electronico").value("Correo Ejemplo3"));

        verify(personaService, times(1)).listarPersonas();
    }

    @Test
    public void testObtenerPersonaPorId() throws Exception {
        // ID de ejemplo para el admin
        Long id_persona = 1L;

        // Crear admin de ejemplo que se espera que devuelva el servicio
        Persona personaEjemplo = new Persona("Nombre Ejemplo", "Apellido Ejemplo", "Correo Ejemplo", "Contrasenia Ejemplo");
        personaEjemplo.setId_persona(id_persona);

        // Configurar el comportamiento simulado del servicio
        when(personaService.obtenerPersonaPorId(id_persona)).thenReturn(personaEjemplo);

        // Realizar la solicitud GET al controlador

        mockMvc.perform(get("/persona/{id}", id_persona))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id_persona").value(id_persona))
                .andExpect(jsonPath("$.nombre").value("Nombre Ejemplo"))
                .andExpect(jsonPath("$.apellido").value("Apellido Ejemplo"))
                .andExpect(jsonPath("$.correo_electronico").value("Correo Ejemplo"));

        // Verificar que se llamó al servicio con el ID esperado
        verify(personaService, times(1)).obtenerPersonaPorId(id_persona);
    }

    @Test
    public void testActualizarPersona() throws Exception {
        // ID de ejemplo para el admin
        Long id_persona = 1L;

        // Crear admin de ejemplo que se espera que devuelva el servicio
        Persona personaEjemplo = new Persona("Nombre Ejemplo", "Apellido Ejemplo", "Correo Ejemplo", "Contrasenia Ejemplo");
        personaEjemplo.setId_persona(id_persona);

        when(personaService.actualizarPersona(eq(id_persona), any(Persona.class))).thenReturn(personaEjemplo);

        mockMvc.perform(MockMvcRequestBuilders.put("/persona/{id}", id_persona)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Nuevo Nombre\",\"apellido\":\"Nuevo Apellido\",\"correo_electronico\":\"Nuevo Correo\",\"contrasenia\":\"Nueva Contrasenia\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id_persona").value(id_persona))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Nombre Ejemplo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apellido").value("Apellido Ejemplo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.correo_electronico").value("Correo Ejemplo"));

        verify(personaService, times(1)).actualizarPersona(eq(id_persona), any(Persona.class));
    }

    @Test
    public void testEliminarPersona() throws Exception {
        // ID de ejemplo para el admin
        Long id_persona = 1L;

        // Crear admin de ejemplo que se espera que devuelva el servicio
        Persona personaEjemplo = new Persona("Nombre Ejemplo", "Apellido Ejemplo", "Correo Ejemplo", "Contrasenia Ejemplo");
        personaEjemplo.setId_persona(id_persona);

        // Realizar la solicitud DELETE al controlador
        mockMvc.perform(MockMvcRequestBuilders.delete("/persona/{id}", id_persona))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(personaService, times(1)).eliminarPersona(id_persona);
    }


}
