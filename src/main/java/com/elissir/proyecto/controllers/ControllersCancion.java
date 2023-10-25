package com.elissir.proyectodockerweb.controllers;

import com.elissir.proyecto.dto.CancionDTO;
import com.elissir.proyecto.dto.CancionMapper;
import com.elissir.proyecto.entidades.Cancion;
import com.elissir.proyecto.services.CancionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cancion")
public class ControllersCancion {

    private final CancionService cancionService;

    @Autowired
    public ControllersCancion(CancionService cancionService) {
        this.cancionService = cancionService;
    }


    // Peticiones

    // Crear un usuario recibiendo un body de cancion al estilo Json >: )
    @PostMapping("/create")
    public Cancion crearCancion(@RequestBody Cancion cancion) {
        return cancionService.crearCancion(cancion);
    }

    // Recibir todos los elementos de la tabla cancion
    @GetMapping("/list")
    public List<CancionDTO> listarCanciones() {
        return cancionService.listarCanciones().stream().map(
                cancion -> CancionMapper.mapper.cancionToCancionDTO(cancion)).collect(Collectors.toList()
        );
    }

    // Recibir un elemento de la tabla cancion por id
    @GetMapping("/{id}")
    public CancionDTO obtenerCancionPorId(@PathVariable Long id) {
        return CancionMapper.mapper.cancionToCancionDTO(cancionService.obtenerCancionPorId(id));
    }

    // Actualizar un elemento de la tabla cancion por id
    @PutMapping("/{id}")
    public Cancion actualizarCancion(@PathVariable Long id, @RequestBody Cancion cancion) {
        return cancionService.actualizarCancion(id, cancion);
    }

    // Borrar un elemento de la tabla cancion por id
    @DeleteMapping("/{id}")
    public void eliminarCancion(@PathVariable Long id) {
        cancionService.eliminarCancion(id);
    }

}
