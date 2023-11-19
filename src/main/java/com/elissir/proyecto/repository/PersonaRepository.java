package com.elissir.proyecto.repository;

import com.elissir.proyecto.entidades.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {

    Persona findFirstByCorreoElectronico(String correo_electronico);
    Persona findFirstByNombre(String nombre);
}
