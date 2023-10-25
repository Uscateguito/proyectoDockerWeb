package com.elissir.proyectodockerweb.controllers;

import com.elissir.proyecto.dto.PersonaDTO;
import com.elissir.proyecto.dto.PersonaMapper;
import com.elissir.proyecto.entidades.*;
import com.elissir.proyecto.services.CancionService;
import com.elissir.proyecto.services.ListaService;
import com.elissir.proyecto.services.PersonaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/persona")
public class ControllersPersona {

    private final PersonaService personaService;

    private final CancionService cancionService;

    private final ListaService listaService;

    @Autowired
    public ControllersPersona(PersonaService personaService, CancionService cancionService, ListaService listaService) {
        this.personaService = personaService;
        this.cancionService = cancionService;
        this.listaService = listaService;
    }

    // Peticiones

    // Crear un usuario recibiendo un body de persona al estilo Json >: )
    @PostMapping("/create")
    public Persona crearPersona(@RequestBody Persona persona) {
        return personaService.crearPersona(persona);
    }

    // Recibir todos los elementos de la tabla persona
    @Transactional
    @GetMapping("/list")
    public List<PersonaDTO> listarPersonas() {
        return personaService.listarPersonas().stream().map(
                persona -> PersonaMapper.mapper.personaToPersonaDTO(persona)).collect(Collectors.toList());
    }

    // Recibir un elemento de la tabla persona por id
    @GetMapping("/{id}")
    public PersonaDTO obtenerPersonaPorId(@PathVariable Long id) {
        return PersonaMapper.mapper.personaToPersonaDTO(personaService.obtenerPersonaPorId(id));
    }

    // Actualizar un elemento de la tabla persona por id
    @PutMapping("/{id}")
    public Persona actualizarPersona(@PathVariable Long id, @RequestBody Persona persona) {
        return personaService.actualizarPersona(id, persona);
    }

    // Borrar un elemento de la tabla persona por id
    @DeleteMapping("/{id}")
    public void eliminarPersona(@PathVariable Long id) {
        personaService.eliminarPersona(id);
    }


}
