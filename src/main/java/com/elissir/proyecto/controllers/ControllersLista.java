package com.elissir.proyectodockerweb.controllers;

import com.elissir.proyecto.entidades.Lista;
import com.elissir.proyecto.services.CancionService;
import com.elissir.proyecto.services.ListaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genero")
public class ControllersLista {


    private final ListaService listaService;

    private final CancionService cancionService;

    @Autowired
    public ControllersLista(ListaService listaService, CancionService cancionService) {
        this.listaService = listaService;
        this.cancionService = cancionService;
    }

    // Peticiones

    // Crear un usuario recibiendo un body de lista al estilo Json >: )
    @PostMapping("/create")
    public Lista crearLista(@RequestBody Lista lista) {
        return listaService.crearLista(lista);
    }

    // Recibir todos los elementos de la tabla lista
    @Transactional
    @GetMapping("/list")
    public List<Lista> listarListaes() {
        return listaService.listarListas();
    }

    // Recibir un elemento de la tabla lista por id
    @GetMapping("/{id}")
    public Lista obtenerListaPorId(@PathVariable Long id) {
        return listaService.obtenerListaPorId(id);
    }

    // Actualizar un elemento de la tabla lista por id
    @PutMapping("/{id}")
    public Lista actualizarLista(@PathVariable Long id, @RequestBody Lista lista) {
        return listaService.actualizarLista(id, lista);
    }

    // Borrar un elemento de la tabla lista por id
    @DeleteMapping("/{id}")
    public void eliminarLista(@PathVariable Long id) {
        listaService.eliminarLista(id);
    }



}
