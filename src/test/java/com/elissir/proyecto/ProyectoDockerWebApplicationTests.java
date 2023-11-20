package com.elissir.proyecto;

import com.elissir.proyecto.repositoryJPA.AdminRepository;
import com.elissir.proyecto.repositoryJPA.PersonaRepository;
import com.elissir.proyecto.services.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProyectoDockerWebApplicationTests {

    @Autowired
    AdminService adminService;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PersonaRepository personaRepository;

    @Test
    void contextLoads() {
        System.out.println(personaRepository.findFirstByCorreoElectronico("correo_electronico"));
    }

}
