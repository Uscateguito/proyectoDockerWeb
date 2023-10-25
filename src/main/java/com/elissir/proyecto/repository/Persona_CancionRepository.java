package com.elissir.proyecto.repository;

import com.elissir.proyecto.entidades.Cancion;
import com.elissir.proyecto.entidades.Persona;
import com.elissir.proyecto.entidades.Persona_Cancion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Persona_CancionRepository extends JpaRepository<Persona_Cancion, Long> {

    Persona_Cancion findFirstByCancionAndPersona(Cancion cancion, Persona persona);
    boolean existsByCancionAndPersona(Cancion cancion, Persona persona);

    Persona_Cancion deleteByCancionAndPersona(Cancion cancion, Persona persona);
}
