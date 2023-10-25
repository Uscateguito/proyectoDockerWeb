package com.elissir.proyecto;

import com.elissir.proyecto.repository.AdminRepository;
import com.elissir.proyecto.repository.PersonaRepository;
import com.elissir.proyecto.services.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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
