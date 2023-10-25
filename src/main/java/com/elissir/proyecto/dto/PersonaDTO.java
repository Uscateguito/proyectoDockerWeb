package com.elissir.proyectodockerweb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonaDTO {

    private Long id_persona;

    private String nombre;

    private String apellido;

    private String correo_electronico;

}

