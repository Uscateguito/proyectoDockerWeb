package com.elissir.proyecto.controllers;

import com.elissir.proyecto.services.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/login")
public class ControllersLogin {

    private final PersonaService personaService;

    @Autowired
    public ControllersLogin(PersonaService personaService) {
        this.personaService = personaService;
    }

//    @PostMapping("/persona")
//    public Persona loginPersona(@RequestParam ) {
//
//    }
}
