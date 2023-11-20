package com.elissir.proyecto.services;

import com.elissir.proyecto.entidades.Admin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class AdminServiceTest {

    @Autowired
    AdminService adminService;

    @Test
    public void TestCambioPassword() {
        Admin admin = new Admin("test1_nombre", "1234");
        Assertions.assertNotEquals("1234", admin.getContrasenia());
    }

    @Test
    public void TestcrearAdmin() {
        Admin adminTest = new Admin("test1_nombre", "1234");
        Admin adminComparacion = adminService.crearAdmin(adminTest);
        Assertions.assertEquals(adminTest.getNombre(), adminComparacion.getNombre());
        Assertions.assertEquals(adminTest.getContrasenia(), adminComparacion.getContrasenia());

        // Borro el elemnto de la base de datos para no afectar a los otros test
        adminService.eliminarAdmin(adminComparacion.getId_admin());
    }

    @Test
    public void TestmodificarAdmin() {
        // La primera parte es crear el admin y la segunda es modificarlo
        // 1. Creo el admin

        Admin adminBase = new Admin("test1_nombre", "test_contrasenia");
        adminService.crearAdmin(adminBase);

        // 2. Lo modifico

        Admin adminModificado = new Admin("test2_nombre", "test_contrasenia");
        Admin adminComparacion = adminService.actualizarAdmin(adminBase.getId_admin(), adminModificado);
        Assertions.assertEquals(adminModificado.getNombre(), adminComparacion.getNombre());
//        las constraseñas no son iguales porque se encriptan a pesar de tener los mismos caracteres

//        Assertions.assertEquals(adminModificado.getContrasenia(), adminComparacion.getContrasenia());

        // Borro el elemnto de la base de datos para no afectar a los otros test
        adminService.eliminarAdmin(adminComparacion.getId_admin());
    }

    @Test
    public void TestlistarAdmins() {
        Admin adminBase  = new Admin("test1_nombre", "test_contrasenia");
        Admin adminBase2 = new Admin("test2_nombre", "test_contrasenia");
        Admin adminBase3 = new Admin("test3_nombre", "test_contrasenia");
        adminService.crearAdmin(adminBase);
        adminService.crearAdmin(adminBase2);
        adminService.crearAdmin(adminBase3);
        Assertions.assertEquals(3, adminService.listarAdmins().size());

        // Borro el elemnto de la base de datos para no afectar a los otros test
        adminService.eliminarAdmin(adminBase.getId_admin());
        adminService.eliminarAdmin(adminBase2.getId_admin());
        adminService.eliminarAdmin(adminBase3.getId_admin());
    }

    @Test
    public void TesteliminarAdmin() {
        // 1. Creo el admin
        Admin adminBase = new Admin("test1_nombre", "test_contrasenia");
        adminService.crearAdmin(adminBase);

        // 2. Lo elimino
        adminService.eliminarAdmin(1L);
        Assertions.assertNull(adminService.obtenerAdminPorId(1L));
    }
}
