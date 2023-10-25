package com.elissir.proyecto.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"listCanciones", "listGenero"})
@Entity(name = "persona")
public class Persona implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_persona;

    private String nombre;

    private String apellido;

    private String contrasenia;

    private String correoElectronico;

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
        this.correoElectronico = correo_electronico;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("PERSONA"));
    }

    @Override
    public String getPassword() {
        return this.contrasenia;
    }

    @Override
    public String getUsername() {
        return this.correoElectronico;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
