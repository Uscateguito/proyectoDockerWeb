package com.elissir.proyecto.controllers;

import com.elissir.proyecto.entidades.*;
import com.elissir.proyecto.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ControllersRelacionesTest {

    @InjectMocks
    private ControllersRelaciones controllersRelaciones;

    @Mock
    private CancionService cancionService;

    @Mock
    private ListaService listaService;

    @Mock
    private PersonaService personaService;

    @Mock
    private Persona_CancionService persona_CancionService;

    @Mock
    private Persona_ListaService persona_ListaService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // Esto simula un entorno de servidor web donde se puede realizar una solicitud HTTP al controlador
        mockMvc = MockMvcBuilders.standaloneSetup(controllersRelaciones).build();
    }

    @Test
    public void testObtenerPersona_CancionPorId() throws Exception {

        // IDs de ejemplo

        Long id_persona = 1L;
        Long id_cancion = 1L;

        // Crear una lista de ejemplo que se espera que devuelva el servicio
        Persona personaEjemplo = new Persona("Nombre Ejemplo", "Apellido Ejemplo", "Correo Ejemplo", "Contrasenia Ejemplo");
        Cancion cancionEjemplo = new Cancion("Nombre Ejemplo", "Artista Ejemplo", "Album Ejemplo", "Duracion Ejemplo");
        Persona_Cancion persona_cancionEjemplo = new Persona_Cancion();

        // Configurar el comportamiento simulado del servicio
        when(personaService.obtenerPersonaPorId(id_persona)).thenReturn(personaEjemplo);
        when(cancionService.obtenerCancionPorId(id_cancion)).thenReturn(cancionEjemplo);
        when(persona_CancionService.obtenerpersona_cancionPorObjetos(cancionEjemplo, personaEjemplo)).thenReturn(persona_cancionEjemplo);

        // Realiza una solicitud GET simulada al controlador
        mockMvc.perform(get("/relaciones/persona/{idPersona}/cancion/{idCancion}", id_persona, id_cancion)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.like").value(1));

        verify(persona_CancionService, times(1)).obtenerpersona_cancionPorObjetos(cancionEjemplo, personaEjemplo);
    }

    @Test
    public void testObtenerPersona_ListaPorId() throws Exception {

        // IDs de ejemplo

        Long id_persona = 1L;
        Long id_lista = 1L;

        // Crear una lista de ejemplo que se espera que devuelva el servicio
        Persona personaEjemplo = new Persona("Nombre Ejemplo", "Apellido Ejemplo", "Correo Ejemplo", "Contrasenia Ejemplo");
        Lista listaEjemplo = new Lista("Genero Ejemplo");
        Persona_Lista persona_listaEjemplo = new Persona_Lista();

        // Configurar el comportamiento simulado del servicio
        when(personaService.obtenerPersonaPorId(id_persona)).thenReturn(personaEjemplo);
        when(listaService.obtenerListaPorId(id_lista)).thenReturn(listaEjemplo);
        when(persona_ListaService.obtenerpersona_listaPorObjetos(listaEjemplo, personaEjemplo)).thenReturn(persona_listaEjemplo);

        // Realiza una solicitud GET simulada al controlador
        mockMvc.perform(get("/relaciones/persona/{idPersona}/genero/{idGenero}", id_persona, id_lista)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.like").value(1));

        verify(persona_ListaService, times(1)).obtenerpersona_listaPorObjetos(listaEjemplo, personaEjemplo);
    }

    // Preguntar al profe cómo realizar una segunda invocación en la que debería salir 0.
    @Test
    public void testAgregarCancion() throws Exception {
        // IDs de ejemplo

        Long id_persona = 1L;
        Long id_cancion = 1L;

        // Crear una lista de ejemplo que se espera que devuelva el servicio
        Persona personaEjemplo = new Persona("Nombre Ejemplo", "Apellido Ejemplo", "Correo Ejemplo", "Contrasenia Ejemplo");
        Cancion cancionEjemplo = new Cancion("Nombre Ejemplo", "Artista Ejemplo", "Album Ejemplo", "Duracion Ejemplo");
        Persona_Cancion persona_cancionEjemplo = new Persona_Cancion();
        Persona_Cancion persona_cancionEjemplo2 = new Persona_Cancion();

        // Relaciones necesarias para la prueba
        persona_cancionEjemplo.setPersona(personaEjemplo);
        persona_cancionEjemplo.setCancion(cancionEjemplo);
        personaEjemplo.agregarCancion(persona_cancionEjemplo);
        cancionEjemplo.agregarPersona(persona_cancionEjemplo);

        persona_cancionEjemplo2.setLike(0);


        // Configurar el comportamiento simulado del servicio
        when(personaService.obtenerPersonaPorId(id_persona)).thenReturn(personaEjemplo);
        when(cancionService.obtenerCancionPorId(id_cancion)).thenReturn(cancionEjemplo);
        // Asumiendo que la relación ya existe, debe haber un cambio en el like, en este caso, si a la persona le gustaba,
        // ahora no le gusta y viceversa.
        when(persona_CancionService.actualizarpersona_cancion(eq(cancionEjemplo), eq(personaEjemplo), eq(persona_cancionEjemplo)))
                .thenReturn(persona_cancionEjemplo2);

        // Realiza una solicitud PUT simulada al controlador
        mockMvc.perform(MockMvcRequestBuilders.put("/relaciones/persona/{idPersona}/cancion/{idCancion}", id_persona, id_cancion)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.like").value(0));

        verify(persona_CancionService, times(1)).actualizarpersona_cancion(cancionEjemplo, personaEjemplo, persona_cancionEjemplo);
    }

    @Test
    public void testAgregarLista() throws Exception {
        // IDs de ejemplo

        Long id_persona = 1L;
        Long id_lista = 1L;

        // Crear una lista de ejemplo que se espera que devuelva el servicio
        Persona personaEjemplo = new Persona("Nombre Ejemplo", "Apellido Ejemplo", "Correo Ejemplo", "Contrasenia Ejemplo");
        Lista listaEjemplo = new Lista("Genero Ejemplo");
        Persona_Lista persona_listaEjemplo = new Persona_Lista();
        Persona_Lista persona_listaEjemplo2 = new Persona_Lista();

        // Relaciones necesarias para la prueba
        persona_listaEjemplo.setPersona(personaEjemplo);
        persona_listaEjemplo.setLista(listaEjemplo);
        personaEjemplo.agregarLista(persona_listaEjemplo);
        listaEjemplo.agregarPersona(persona_listaEjemplo);

        persona_listaEjemplo2.setLike(0);


        // Configurar el comportamiento simulado del servicio
        when(personaService.obtenerPersonaPorId(id_persona)).thenReturn(personaEjemplo);
        when(listaService.obtenerListaPorId(id_lista)).thenReturn(listaEjemplo);
        // Asumiendo que la relación ya existe, debe haber un cambio en el like, en este caso, si a la persona le gustaba,
        // ahora no le gusta y viceversa.
        when(persona_ListaService.actualizarpersona_lista(eq(listaEjemplo), eq(personaEjemplo), eq(persona_listaEjemplo)))
                .thenReturn(persona_listaEjemplo2);

        // Realiza una solicitud PUT simulada al controlador
        mockMvc.perform(MockMvcRequestBuilders.put("/relaciones/persona/{idPersona}/genero/{idGenero}", id_persona, id_lista)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.like").value(0));

        verify(persona_ListaService, times(1)).actualizarpersona_lista(listaEjemplo, personaEjemplo, persona_listaEjemplo);
    }

    @Test
    public void testAgregarCancionALista() throws Exception {

        // IDs de ejemplo

        Long id_cancion = 1L;
        Long id_lista = 1L;

        // Crear una lista de ejemplo que se espera que devuelva el servicio

        Lista listaEjemplo = new Lista("Genero Ejemplo");
        Cancion cancionEjemplo = new Cancion("Nombre Ejemplo", "Artista Ejemplo", "Album Ejemplo", "Duracion Ejemplo");

        // Configurar el comportamiento simulado del servicio
        when(listaService.obtenerListaPorId(id_lista)).thenReturn(listaEjemplo);
        when(cancionService.obtenerCancionPorId(id_cancion)).thenReturn(cancionEjemplo);
        when(listaService.actualizarLista(eq(id_lista), any(Lista.class))).thenReturn(listaEjemplo);

        // Realiza una solicitud PUT simulada al controlador
        mockMvc.perform(MockMvcRequestBuilders.put("/relaciones/genero/{idLista}/cancion/{idCancion}", id_lista, id_cancion)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.listCanciones.length()").value(1));


    }
}
