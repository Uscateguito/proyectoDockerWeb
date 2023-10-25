package com.elissir.proyectodockerweb.repository;

import com.elissir.proyecto.entidades.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
}
