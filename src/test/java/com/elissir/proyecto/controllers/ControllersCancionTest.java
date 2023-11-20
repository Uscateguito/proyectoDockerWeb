package com.elissir.proyecto.controllers;

import com.elissir.proyecto.entidades.Cancion;
import com.elissir.proyecto.services.CancionService;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ControllersCancionTest {

    // La diferencia entre InjectMocks y Mock es que
    // InjectMocks crea una instancia de la clase sobre la que serán
    // inyectados los mocks, en este caso, sólo canción service.

    @InjectMocks
    private ControllersCancion controllersCancion;

    @Mock
    private CancionService cancionService;

    private MockMvc mockMvc;

    //BeforeEach sirve para inicializar los mocks antes de cada test
    // Un mock es un objeto simulado que imita el comportamiento de objetos reales de una manera controlada.
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // Esto simula un entorno de servidor web donde se puede realizar una solicitud HTTP al controlador
        mockMvc = MockMvcBuilders.standaloneSetup(controllersCancion).build();
    }

    @Test
    public void testCrearCancion() throws Exception {
        // Crea una canción de ejemplo
        Cancion cancionEjemplo = new Cancion("Nombre Ejemplo", "Artista Ejemplo", "Album Ejemplo", "Duracion Ejemplo");

        // Configura el comportamiento simulado del servicio
        when(cancionService.crearCancion(any(Cancion.class))).thenReturn(cancionEjemplo);

        // Realiza una solicitud POST simulada al controlador
        mockMvc.perform(post("/cancion/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Nombre Ejemplo\",\"artista\":\"Artista Ejemplo\",\"album\":\"Album Ejemplo\",\"duracion\":\"Duracion Ejemplo\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.nombre").value("Nombre Ejemplo"))
                .andExpect(jsonPath("$.artista").value("Artista Ejemplo"))
                .andExpect(jsonPath("$.album").value("Album Ejemplo"))
                .andExpect(jsonPath("$.duracion").value("Duracion Ejemplo"));

        // Verifica que el servicio se llamó una vez con la canción de ejemplo
        verify(cancionService, times(1)).crearCancion(any(Cancion.class));
    }

    @Test
    public void testListarCanciones() throws Exception {
        // Configura una lista de canciones de ejemplo
        List<Cancion> cancionesEjemplo = Arrays.asList(
                new Cancion("Cancion1", "Artista1", "Album1", "Duracion1"),
                new Cancion("Cancion2", "Artista2", "Album2", "Duracion2"));

        when(cancionService.listarCanciones()).thenReturn(cancionesEjemplo);

        mockMvc.perform(MockMvcRequestBuilders.get("/cancion/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre").value("Cancion1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nombre").value("Cancion2"));

        verify(cancionService, times(1)).listarCanciones();
    }

    @Test
    public void testObtenerCancionPorId() throws Exception {
        // ID de ejemplo para la canción
        Long id = 1L;

        // Crear una canción de ejemplo que se espera que devuelva el servicio
        Cancion cancionEjemplo = new Cancion("Nombre Ejemplo", "Artista Ejemplo", "Album Ejemplo", "Duracion Ejemplo");
        cancionEjemplo.setId_cancion(id);

        // Configurar el comportamiento simulado del servicio
        when(cancionService.obtenerCancionPorId(id)).thenReturn(cancionEjemplo);

        // Realizar la solicitud GET al controlador
        mockMvc.perform(get("/cancion/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id_cancion").value(id))
                .andExpect(jsonPath("$.nombre").value("Nombre Ejemplo"))
                .andExpect(jsonPath("$.artista").value("Artista Ejemplo"))
                .andExpect(jsonPath("$.album").value("Album Ejemplo"))
                .andExpect(jsonPath("$.duracion").value("Duracion Ejemplo"));

        // Verificar que se llamó al servicio con el ID esperado
        verify(cancionService, times(1)).obtenerCancionPorId(id);
    }

    @Test
    public void testActualizarCancion() throws Exception {
        Long idCancion = 1L;
        Cancion cancionEjemplo = new Cancion("Nombre Ejemplo", "Artista Ejemplo", "Album Ejemplo", "Duracion Ejemplo");
        cancionEjemplo.setId_cancion(idCancion);

//        when(cancionService.actualizarCancion(eq(idCancion), any(Cancion.class))):
//        Establece una expectativa utilizando Mockito. Indica que cuando
//        se llame al método actualizarCancion del servicio cancionService con un argumento igual
//        al valor idCancion y cualquier instancia de la clase Cancion, se debe realizar una
//        acción específica. En este caso, la acción específica es retornar cancionEjemplo.
//
//         thenReturn(cancionEjemplo): Esto indica que cuando se cumpla la condición especificada en el when,
//         es decir, cuando se llame al método actualizarCancion con los argumentos indicados,
//         Mockito debe devolver el objeto cancionEjemplo.

        when(cancionService.actualizarCancion(eq(idCancion), any(Cancion.class))).thenReturn(cancionEjemplo);

        mockMvc.perform(MockMvcRequestBuilders.put("/cancion/{id}", idCancion)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Nuevo Nombre\",\"artista\":\"Nuevo Artista\",\"album\":\"Nuevo Album\",\"duracion\":\"Nueva Duracion\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id_cancion").value(idCancion))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Nombre Ejemplo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.artista").value("Artista Ejemplo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.album").value("Album Ejemplo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.duracion").value("Duracion Ejemplo"));

        verify(cancionService, times(1)).actualizarCancion(eq(idCancion), any(Cancion.class));
    }

    @Test
    public void testEliminarCancion() throws Exception {
        Long idCancion = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/cancion/{id}", idCancion))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(cancionService, times(1)).eliminarCancion(eq(idCancion));
    }
}
