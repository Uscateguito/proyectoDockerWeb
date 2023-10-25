package com.elissir.proyectodockerweb.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

@Data
@NoArgsConstructor
@Entity(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_admin;

    private String nombre;

    private String contrasenia;

    public Admin(String nombre, String contrasenia) {
        this.nombre = nombre;
        setContrasenia(contrasenia);
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = DigestUtils.sha256Hex(contrasenia);
    }
}
