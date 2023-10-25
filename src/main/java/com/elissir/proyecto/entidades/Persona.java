package com.elissir.proyectodockerweb.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "persona")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_persona;

    private String nombre;

    private String apellido;

    private String contrasenia;

    private String correo_electronico;

    @JsonIgnore
    @OneToMany(mappedBy = "persona")
    // Canciones que le gustan a la persona
    private List<Persona_Cancion> listCanciones = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "persona")
    // Canciones que le gustan a la persona
    private List<Persona_Lista> listGenero = new ArrayList<>();

    public Persona(String nombre, String apellido, String correo_electronico , String contrasenia) {
        this.nombre = nombre;
        this.apellido = apellido;
        setContrasenia(contrasenia);
        this.correo_electronico = correo_electronico;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = DigestUtils.sha256Hex(contrasenia);
    }

    public void agregarCancion(Persona_Cancion cancion) {
        listCanciones.add(cancion);
    }

    public void agregarLista(Persona_Lista lista) {
        listGenero.add(lista);
    }
}
