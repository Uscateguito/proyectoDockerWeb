package com.elissir.proyecto.repositoryJPA;

import com.elissir.proyecto.entidades.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Persona_ListaRepository extends JpaRepository<Persona_Lista, Long> {

    Persona_Lista findFirstByListaAndPersona(Lista lista, Persona persona);
    boolean existsByListaAndPersona(Lista lista, Persona persona);

    Persona_Lista deleteByListaAndPersona(Lista lista, Persona persona);
}
