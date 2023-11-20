package com.elissir.proyecto.controllers;

import com.elissir.proyecto.entidades.Admin;
import com.elissir.proyecto.services.AdminService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ControllersAdminTest {

    // La diferencia entre InjectMocks y Mock es que
    // InjectMocks crea una instancia de la clase sobre la que serán
    // inyectados los mocks, en este caso, sólo canción service.

    @InjectMocks
    private ControllersAdmin controllersAdmin;

    @Mock
    private AdminService adminService;

    private MockMvc mockMvc;

    //BeforeEach sirve para inicializar los mocks antes de cada test
    // Un mock es un objeto simulado que imita el comportamiento de objetos reales de una manera controlada.
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // Esto simula un entorno de servidor web donde se puede realizar una solicitud HTTP al controlador
        mockMvc = MockMvcBuilders.standaloneSetup(controllersAdmin).build();
    }

    @Test
    public void testCrearAdmin() throws Exception {
        // Crea admin de ejemplo
        Admin adminEjemplo = new Admin("Nombre Ejemplo", "Contrasenia Ejemplo");

        // Configura el comportamiento simulado del servicio
        when(adminService.crearAdmin(any(Admin.class))).thenReturn(adminEjemplo);

        // Realiza una solicitud POST simulada al controlador
        mockMvc.perform(post("/admin/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Nombre Ejemplo\",\"contrasenia\":\"Contrasenia Ejemplo\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.nombre").value("Nombre Ejemplo"));

        // Verifica que el servicio se llamó una vez un admin de ejemplo
        verify(adminService, times(1)).crearAdmin(any(Admin.class));
    }

    @Test
    public void testListarAdmin() throws Exception {

        // Crea una lista de amdmin de ejemplo
        List<Admin> adminEjemplo = Arrays.asList(
                new Admin("Admin1", "Contrasenia1"),
                new Admin("Admin2", "Contrasenia2"));

        when(adminService.listarAdmins()).thenReturn(adminEjemplo);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre").value("Admin1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nombre").value("Admin2"));

        verify(adminService, times(1)).listarAdmins();
    }

    @Test
    public void testObtenerAdminPorId() throws Exception {


        // ID de ejemplo para el admin
        Long id = 1L;

        // Crear admin de ejemplo que se espera que devuelva el servicio
        Admin adminEjemplo = new Admin("Nombre Ejemplo", "Contrasenia Ejemplo");
        adminEjemplo.setId_admin(id);

        // Configurar el comportamiento simulado del servicio
        when(adminService.obtenerAdminPorId(id)).thenReturn(adminEjemplo);

        // Realizar la solicitud GET al controlador

        mockMvc.perform(get("/admin/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id_admin").value(id))
                .andExpect(jsonPath("$.nombre").value("Nombre Ejemplo"));

        // Verificar que se llamó al servicio con el ID esperado
        verify(adminService, times(1)).obtenerAdminPorId(id);
    }

    @Test
    public void testActualizarAdmin() throws Exception {

        // ID de ejemplo para el admin
        Long id = 1L;

        // Crear admin de ejemplo que se espera que devuelva el servicio
        Admin adminEjemplo = new Admin("Nombre Ejemplo", "Contrasenia Ejemplo");
        adminEjemplo.setId_admin(id);

        when(adminService.actualizarAdmin(eq(id), any(Admin.class))).thenReturn(adminEjemplo);

        mockMvc.perform(MockMvcRequestBuilders.put("/admin/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Nuevo Nombre\",\"contrasenia\":\"Nueva Contrasenia\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id_admin").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Nombre Ejemplo"));

        verify(adminService, times(1)).actualizarAdmin(eq(id), any(Admin.class));
    }

    @Test
    public void testEliminarAdmin() throws Exception {
        // ID de ejemplo para el admin
        Long id = 1L;

        // Realizar la solicitud DELETE al controlador
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verificar que se llamó al servicio con el ID esperado
        verify(adminService, times(1)).eliminarAdmin(id);
    }
}
