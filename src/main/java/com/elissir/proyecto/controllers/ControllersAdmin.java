package com.elissir.proyecto.controllers;

import com.elissir.proyecto.dto.AdminDTO;
import com.elissir.proyecto.dto.AdminMapper;
import com.elissir.proyecto.entidades.Admin;
import com.elissir.proyecto.services.AdminService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class ControllersAdmin {

    private final AdminService adminService;


    @Autowired
    public ControllersAdmin(AdminService adminService) {
        this.adminService = adminService;
    }

    // Peticiones

    // Crear un usuario recibiendo un body de persona al estilo Json >: )
    @PostMapping("/create")
    public Admin crearAdmin(@RequestBody Admin admin) {
        return adminService.crearAdmin(admin);
    }

    // Recibir todos los elementos de la tabla persona
    @Transactional
    @GetMapping("/list")
    public List<AdminDTO> listarAdmins() {
        return adminService.listarAdmins().stream().map(admin -> AdminMapper.mapper.adminToAdminDTO(admin)).collect(Collectors.toList());
    }

    // Recibir un elemento de la tabla persona por id
    @GetMapping("/{id}")
    public AdminDTO obtenerAdminPorId(@PathVariable Long id) {
        return AdminMapper.mapper.adminToAdminDTO(adminService.obtenerAdminPorId(id));
    }

    // Actualizar un elemento de la tabla persona por id
    @PutMapping("/{id}")
    public Admin actualizarAdmin(@PathVariable Long id, @RequestBody Admin persona) {
        return adminService.actualizarAdmin(id, persona);
    }

    // Borrar un elemento de la tabla persona por id
    @DeleteMapping("/{id}")
    public void eliminarAdmin(@PathVariable Long id) {
        adminService.eliminarAdmin(id);
    }

}
