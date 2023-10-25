package com.elissir.proyecto.entidades;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "lista")
public class Lista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_lista;

    private String genero;

    private int numLikes;

    @OneToMany(mappedBy = "lista")
    private List<Cancion> listCanciones = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "lista")
    private List<Persona_Lista> listPersonas = new ArrayList<>();

    public Lista(String genero) {
        this.genero = genero;
        this.numLikes = 0;
    }

    public void agregarCancion(Cancion cancion) {
        listCanciones.add(cancion);
    }

    public void agregarPersona(Persona_Lista persona) {
        listPersonas.add(persona);
    }

    // Esto debería actualizar el número de likes que tiene la lista
    public void setNumLikes() {
        int numLikes = 0;
        for (Persona_Lista persona : listPersonas) {
            numLikes += persona.getLike();
        }
        this.numLikes = numLikes;
    }
}
