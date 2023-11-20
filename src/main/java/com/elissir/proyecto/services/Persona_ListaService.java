package com.elissir.proyecto.services;

import com.elissir.proyecto.entidades.*;
import com.elissir.proyecto.repositoryJPA.ListaRepository;
import com.elissir.proyecto.repositoryJPA.PersonaRepository;
import com.elissir.proyecto.repositoryJPA.Persona_ListaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Persona_ListaService {

    private final Persona_ListaRepository persona_listaRepository;

    private final PersonaRepository personaRepository;

    private final ListaRepository listaRepository;

    @Autowired
    public Persona_ListaService(Persona_ListaRepository personaListaRepository, PersonaRepository personaRepository, ListaRepository listaRepository) {
        persona_listaRepository = personaListaRepository;
        this.personaRepository = personaRepository;
        this.listaRepository = listaRepository;
    }


    // Aquí funciona el .save de JPA
    public Persona_Lista crearpersona_lista(Persona_Lista persona_lista) {
        return persona_listaRepository.save(persona_lista);
    }

    // Aquí findAll encuentra todos los elementos, sería interesante buscar la query con la que se hace esto
    public List<Persona_Lista> listarpersona_lista() {
        return persona_listaRepository.findAll();
    }

    public Persona_Lista obtenerpersona_listaPorObjetos(Lista lista, Persona persona) {
        if (persona_listaRepository.findFirstByListaAndPersona(lista, persona) != null) {
            return persona_listaRepository.findFirstByListaAndPersona(lista, persona);
        }
        return null;
    }


    // Recibo un objeto y con base a lo que hay ahí, simplemente actualizo el que ya existe
    public Persona_Lista actualizarpersona_lista(Lista lista, Persona persona, Persona_Lista persona_Lista) {
        if (persona_listaRepository.existsByListaAndPersona(lista, persona)) {
            Persona_Lista modificada = persona_listaRepository.findFirstByListaAndPersona(lista, persona);
            // La modifico y la devuelvo
            modificada.setPersona(persona_Lista.getPersona());
            modificada.setLista(persona_Lista.getLista());
            // Le quito y le pongo el like
            if(modificada.getLike() == 0) {
                modificada.setLike(1);
            } else {
                modificada.setLike(0);
            }

            modificada = persona_listaRepository.save(modificada);
            lista.setNumLikes();
            // Ejecuto un método para actualizar el número de likes de la canción
            listaRepository.save(lista);
            return modificada;
        }
        else {
            return crearpersona_lista(persona_Lista);
        }
    }

    // Recibo un id y elimino el objeto que tenga ese id
    public void eliminarpersona_lista(Lista lista, Persona persona) {
        Persona_Lista persona_lista = persona_listaRepository.findFirstByListaAndPersona(lista, persona);
        persona_listaRepository.delete(persona_lista);
    }
}
