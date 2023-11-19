package com.elissir.proyecto.entidades;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity(name = "persona_cancion")
public class Persona_Cancion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_persona_cancion;

    private int like;

    // name es la foreign key de este objeto y referencedColumnName es la primary key del objeto al que se hace referencia

    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    private Persona persona;

    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_cancion", referencedColumnName = "id_cancion")
    private Cancion cancion;

    public Persona_Cancion() {
        this.like = 1;
    }

    public void agregarCancion(Cancion cancion) {
        this.cancion = cancion;
    }

    public void agregarPersona(Persona persona) {
        this.persona = persona;
    }
}
