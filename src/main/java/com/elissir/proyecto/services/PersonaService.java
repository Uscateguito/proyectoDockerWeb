package com.elissir.proyecto.services;

import com.elissir.proyecto.entidades.Persona;
import com.elissir.proyecto.repositoryJPA.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaService {

    private final PersonaRepository personaRepository;

    @Autowired
    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    // Aquí funciona el .save de JPA
    public Persona crearPersona(Persona persona) {
        return personaRepository.save(persona);
    }

    // Aquí findAll encuentra todos los elementos, sería interesante buscar la query con la que se hace esto
    public List<Persona> listarPersonas() {
        return personaRepository.findAll();
    }

    public Persona obtenerPersonaPorId(Long id) {
        return personaRepository.findById(id).orElse(null);
    }


    // Recibo un objeto y con base a lo que hay ahí, simplemente actualizo el que ya existe
    public Persona actualizarPersona(Long id, Persona persona) {
        if (personaRepository.existsById(id)) {
            // Busco a la persona en la base de datos con toda su info
            Persona modificada = personaRepository.findById(id).orElse(null);
            // La modifico y la devuelvo
            modificada.setNombre(persona.getNombre());
            modificada.setApellido(persona.getApellido());
            modificada.setCorreoElectronico(persona.getCorreoElectronico());
            modificada.setContrasenia(persona.getContrasenia());
            return personaRepository.save(modificada);
        }
        return null;
    }

    // Recibo un id y elimino el objeto que tenga ese id
    public void eliminarPersona(Long id) {
        personaRepository.deleteById(id);
    }

}
