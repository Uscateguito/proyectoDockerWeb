package com.elissir.proyecto.services;

import com.elissir.proyecto.entidades.Cancion;
import com.elissir.proyecto.repositoryJPA.CancionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CancionService {

    private final CancionRepository cancionRepository;

    @Autowired
    public CancionService(CancionRepository cancionRepository) {
        this.cancionRepository = cancionRepository;
    }

    // Aquí funciona el .save de JPA
    public Cancion crearCancion(Cancion cancion) {
        return cancionRepository.save(cancion);
    }

    // Aquí findAll encuentra todos los elementos, sería interesante buscar la query con la que se hace esto
    public List<Cancion> listarCanciones() {
        return cancionRepository.findAll();
    }

    public Cancion obtenerCancionPorId(Long id) {
        return cancionRepository.findById(id).orElse(null);
    }


    // Recibo un objeto y con base a lo que hay ahí, simplemente actualizo el que ya existe
    public Cancion actualizarCancion(Long id, Cancion cancion) {
        if (cancionRepository.existsById(id)) {
            // Busco a la cancion en la base de datos con toda su info
            Cancion modificada = cancionRepository.findById(id).orElse(null);
            // La modifico y la devuelvo
            modificada.setArtista(cancion.getArtista());
            modificada.setAlbum(cancion.getAlbum());
            modificada.setDuracion(cancion.getDuracion());
            modificada.setNombre(cancion.getNombre());
            return cancionRepository.save(modificada);
        }
        return null;
    }

    // Recibo un id y elimino el objeto que tenga ese id
    public void eliminarCancion(Long id) {
        cancionRepository.deleteById(id);
    }
}
