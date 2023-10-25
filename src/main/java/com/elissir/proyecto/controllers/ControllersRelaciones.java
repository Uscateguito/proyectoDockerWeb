package com.elissir.proyectodockerweb.controllers;

import com.elissir.proyecto.entidades.*;
import com.elissir.proyecto.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/relaciones")
public class ControllersRelaciones {

    private final PersonaService personaService;

    private final CancionService cancionService;

    private final ListaService listaService;

    private final Persona_CancionService persona_CancionService;

    private final Persona_ListaService persona_ListaService;

    @Autowired
    public ControllersRelaciones(PersonaService personaService, CancionService cancionService, ListaService listaService, Persona_CancionService personaCancionService, Persona_ListaService personaListaService) {
        this.personaService = personaService;
        this.cancionService = cancionService;
        this.listaService = listaService;
        persona_CancionService = personaCancionService;
        persona_ListaService = personaListaService;
    }

    @GetMapping("/persona/{idPersona}/cancion/{idCancion}")
    Persona_Cancion obtenerPersona_CancionPorId(@PathVariable Long idPersona, @PathVariable Long idCancion) {
        Persona persona = personaService.obtenerPersonaPorId(idPersona);
        Cancion cancion = cancionService.obtenerCancionPorId(idCancion);
        if(persona == null || cancion == null) {
            return null;
        }
        return persona_CancionService.obtenerpersona_cancionPorObjetos(cancion, persona);
    }

    @GetMapping("/persona/{idPersona}/genero/{idGenero}")
    Persona_Lista obtenerPersona_ListaPorId(@PathVariable Long idPersona, @PathVariable Long idGenero) {
        Persona persona = personaService.obtenerPersonaPorId(idPersona);
        Lista lista = listaService.obtenerListaPorId(idGenero);
        if(persona == null || lista == null) {
            return null;
        }
        return persona_ListaService.obtenerpersona_listaPorObjetos(lista, persona);
    }

    @PutMapping("/persona/{idPersona}/cancion/{idCancion}")
    Persona_Cancion agregarCancion(@PathVariable Long idPersona, @PathVariable Long idCancion) {

        Cancion cancion = cancionService.obtenerCancionPorId(idCancion);
        Persona persona = personaService.obtenerPersonaPorId(idPersona);
        // control de errores
        if (cancion == null || persona == null) {
            return null;
        }
        Persona_Cancion persona_cancion = new Persona_Cancion();
        persona_cancion.setPersona(persona);
        persona_cancion.setCancion(cancion);
        persona.agregarCancion(persona_cancion);
        cancion.agregarPersona(persona_cancion);
        return persona_CancionService.actualizarpersona_cancion(cancion, persona, persona_cancion);
    }

    @PutMapping("/persona/{idPersona}/genero/{idGenero}")
    Persona_Lista agregarLista(@PathVariable Long idPersona, @PathVariable Long idGenero) {

        Lista lista = listaService.obtenerListaPorId(idGenero);
        Persona persona = personaService.obtenerPersonaPorId(idPersona);
        // control de errores
        if (lista == null || persona == null) {
            return null;
        }
        Persona_Lista persona_lista = new Persona_Lista();
        persona_lista.setPersona(persona);
        persona_lista.setLista(lista);
        persona.agregarLista(persona_lista);
        lista.agregarPersona(persona_lista);
        return persona_ListaService.actualizarpersona_lista(lista, persona, persona_lista);
    }

    @PutMapping("/genero/{idLista}/cancion/{idCancion}")
    Lista agregarCancionALista(@PathVariable Long idLista, @PathVariable Long idCancion) {

        Cancion cancion = cancionService.obtenerCancionPorId(idCancion);
        Lista lista = listaService.obtenerListaPorId(idLista);
        if(cancion == null || lista == null) {
            return null;
        }
        lista.agregarCancion(cancion);
        cancion.agregarGenero(lista);
        return listaService.actualizarLista(idLista, lista);
    }
}
