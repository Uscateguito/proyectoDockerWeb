package com.elissir.proyectodockerweb.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "cancion")
public class Cancion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cancion;

    private String nombre;

    private String artista;

    private String album;

    private String duracion;

    private int numLikes;


    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "id_lista", referencedColumnName = "id_lista")
    @JsonIgnore
    private Lista lista;

    @JsonIgnore
    // El mapeo está con el objeto "cancion" porque es lo que recibe
    @OneToMany(mappedBy = "cancion")
    private List<Persona_Cancion> listPersonas = new ArrayList<>();


    public Cancion(String nombre, String artista, String album, String duracion) {
        this.nombre = nombre;
        this.artista = artista;
        this.album = album;
        this.duracion = duracion;
        this.numLikes = 0;
    }

    public void agregarGenero(Lista lista) {
        this.lista = lista;
    }

    public void setNumLikes() {
        int numLikes = 0;
        for (Persona_Cancion persona : listPersonas) {
            numLikes += persona.getLike();
        }
        this.numLikes = numLikes;
    }

    public void agregarPersona(Persona_Cancion personaCancion) {
        listPersonas.add(personaCancion);
    }
}
