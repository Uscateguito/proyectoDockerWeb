package com.elissir.proyecto.services;

import com.elissir.proyecto.entidades.Lista;
import com.elissir.proyecto.repositoryJPA.ListaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListaService {
    private final ListaRepository listaRepository;

    @Autowired
    public ListaService(ListaRepository listaRepository) {
        this.listaRepository = listaRepository;
    }

    // Aquí funciona el .save de JPA
    public Lista crearLista(Lista lista) {
        return listaRepository.save(lista);
    }

    // Aquí findAll encuentra todos los elementos, sería interesante buscar la query con la que se hace esto
    public List<Lista> listarListas() {
        return listaRepository.findAll();
    }

    public Lista obtenerListaPorId(Long id) {
        return listaRepository.findById(id).orElse(null);
    }


    // Recibo un objeto y con base a lo que hay ahí, simplemente actualizo el que ya existe
    public Lista actualizarLista(Long id, Lista lista) {
        if (listaRepository.existsById(id)) {
            // Busco a la lista en la base de datos con toda su info
            Lista modificada = listaRepository.findById(id).orElse(null);
            // La modifico y la devuelvo
            modificada.setGenero(lista.getGenero());
            return listaRepository.save(modificada);
        }
        return null;
    }

    // Recibo un id y elimino el objeto que tenga ese id
    public void eliminarLista(Long id) {
        listaRepository.deleteById(id);
    }
}
