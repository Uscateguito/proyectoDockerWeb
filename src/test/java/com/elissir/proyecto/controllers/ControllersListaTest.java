package com.elissir.proyecto.controllers;

import com.elissir.proyecto.entidades.Lista;
import com.elissir.proyecto.services.ListaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ControllersListaTest {

    @InjectMocks
    private ControllersLista controllersLista;

    @Mock
    private ListaService listaService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // Esto simula un entorno de servidor web donde se puede realizar una solicitud HTTP al controlador
        mockMvc = MockMvcBuilders.standaloneSetup(controllersLista).build();
    }

    @Test
    public void testCrearLista() throws Exception {
        // Crea una lista de ejemplo
        Lista listaEjemplo = new Lista("Genero ejemplo");

        // Configura el comportamiento simulado del servicio
        when(listaService.crearLista(any(Lista.class))).thenReturn(listaEjemplo);

        // Realiza una solicitud POST simulada al controlador
        mockMvc.perform(post("/genero/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"genero\":\"Genero ejemplo\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.genero").value("Genero ejemplo"));

        verify(listaService, times(1)).crearLista(any(Lista.class));
    }

    @Test
    public void testListarLista() throws Exception {
        // Crea una lista de ejemplo
        List<Lista> listaEjemplo = Arrays.asList(
                new Lista("Genero ejemplo"),
                new Lista("Genero ejemplo 2"),
                new Lista("Genero ejemplo 3")
        );

        // Configura el comportamiento simulado del servicio
        when(listaService.listarListas()).thenReturn(listaEjemplo);

        // Realiza una solicitud GET simulada al controlador
        mockMvc.perform(get("/genero/list")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].genero").value("Genero ejemplo"))
                .andExpect(jsonPath("$[1].genero").value("Genero ejemplo 2"))
                .andExpect(jsonPath("$[2].genero").value("Genero ejemplo 3"));

        verify(listaService, times(1)).listarListas();
    }

    @Test
    public void testObtenerListaPorId() throws Exception {
        // ID de ejemplo para la lista
        Long id_lista = 1L;

        // Crear una lista de ejemplo que se espera que devuelva el servicio
        Lista listaEjemplo = new Lista("Genero ejemplo");
        listaEjemplo.setId_lista(id_lista);

        // Configurar el comportamiento simulado del servicio
        when(listaService.obtenerListaPorId(id_lista)).thenReturn(listaEjemplo);

        // Realiza una solicitud GET simulada al controlador
        mockMvc.perform(get("/genero/{id}", id_lista)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.genero").value("Genero ejemplo"));

        verify(listaService, times(1)).obtenerListaPorId(id_lista);
    }

    @Test
    public void testEliminarLista() throws Exception {
        // ID de ejemplo para la lista
        Long id_lista = 1L;

        // Crear una lista de ejemplo que se espera que devuelva el servicio
        Lista listaEjemplo = new Lista("Genero ejemplo");
        listaEjemplo.setId_lista(id_lista);

        // Realiza una solicitud DELETE simulada al controlador
        mockMvc.perform(MockMvcRequestBuilders.delete("/genero/{id}", id_lista))
                .andExpect(status().isOk());

        verify(listaService, times(1)).eliminarLista(id_lista);
    }

    @Test
    public void testActualizarLista() throws Exception {
        // ID de ejemplo para la lista
        Long id_lista = 1L;

        // Crear una lista de ejemplo que se espera que devuelva el servicio
        Lista listaEjemplo = new Lista("Genero ejemplo");
        listaEjemplo.setId_lista(id_lista);

        // Configurar el comportamiento simulado del servicio
        when(listaService.actualizarLista(eq(id_lista), any(Lista.class))).thenReturn(listaEjemplo);


        // Realiza una solicitud PUT simulada al controlador
        mockMvc.perform(MockMvcRequestBuilders.put("/genero/{id}", id_lista)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"genero\":\"Genero ejemplo\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.genero").value("Genero ejemplo"));

        verify(listaService, times(1)).actualizarLista(eq(id_lista), any(Lista.class));

    }


}
