package com.elissir.proyecto.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
//    El request contiene, nombre, contraseña y tipo de usuario
//    El usuario debe guardar la info en la cookie

    @PostMapping(value = "login")
    public String login() {
        return "login";
    }

    @PostMapping(value = "register")
    public String register() {
        return "register";
    }
}



