package com.elissir.proyecto.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonaDTO {

    private Long id_persona;

    private String nombre;

    private String apellido;

    private String correoElectronico;

}

