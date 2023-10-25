package com.elissir.proyecto.repository;

import com.elissir.proyecto.entidades.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findFirstByNombre(String nombre);
}
