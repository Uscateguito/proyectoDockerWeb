package com.elissir.proyecto.services;

import com.elissir.proyecto.entidades.Cancion;
import com.elissir.proyecto.entidades.Persona;
import com.elissir.proyecto.entidades.Persona_Cancion;
import com.elissir.proyecto.repository.CancionRepository;
import com.elissir.proyecto.repository.PersonaRepository;
import com.elissir.proyecto.repository.Persona_CancionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Persona_CancionService {

    private final Persona_CancionRepository persona_cancionRepository;

    private final PersonaRepository personaRepository;

    private final CancionRepository cancionRepository;

    @Autowired
    public Persona_CancionService(Persona_CancionRepository personaCancionRepository, PersonaRepository personaRepository, CancionRepository cancionRepository) {
        persona_cancionRepository = personaCancionRepository;
        this.personaRepository = personaRepository;
        this.cancionRepository = cancionRepository;
    }


    // Aquí funciona el .save de JPA
    public Persona_Cancion crearpersona_cancion(Persona_Cancion persona_Cancion) {
        return persona_cancionRepository.save(persona_Cancion);
    }

    // Aquí findAll encuentra todos los elementos, sería interesante buscar la query con la que se hace esto
    public List<Persona_Cancion> listarpersona_cancion() {
        return persona_cancionRepository.findAll();
    }

    public Persona_Cancion obtenerpersona_cancionPorObjetos(Cancion cancion, Persona persona) {
        if (persona_cancionRepository.findFirstByCancionAndPersona(cancion, persona) != null) {
            return persona_cancionRepository.findFirstByCancionAndPersona(cancion, persona);
        }
        return null;
    }


    // Cada vez que se modifica un like, se actualiza el número de likes de la canción
    // Además al utilizar este método, se crea una nueva relación entre persona y canción
    public Persona_Cancion actualizarpersona_cancion(Cancion cancion, Persona persona, Persona_Cancion persona_cancion) {
        if (persona_cancionRepository.existsByCancionAndPersona(cancion, persona)) {
            Persona_Cancion modificada = persona_cancionRepository.findFirstByCancionAndPersona(cancion, persona);
            // La modifico y la devuelvo
            modificada.setPersona(persona_cancion.getPersona());
            modificada.setCancion(persona_cancion.getCancion());
            // Le quito y le pongo el like
            if(modificada.getLike() == 0) {
                modificada.setLike(1);
            } else {
                modificada.setLike(0);
            }
            modificada = persona_cancionRepository.save(modificada);
            cancion.setNumLikes();
            // Ejecuto un método para actualizar el número de likes de la canción
            cancionRepository.save(cancion);
            return persona_cancionRepository.save(modificada);
        }
        else {
            return crearpersona_cancion(persona_cancion);
        }
    }

    // Recibo un id y elimino el objeto que tenga ese id
    public void eliminarpersona_cancion(Cancion cancion, Persona persona) {
        Persona_Cancion persona_cancion = persona_cancionRepository.findFirstByCancionAndPersona(cancion, persona);
        persona_cancionRepository.delete(persona_cancion);
    }
}
