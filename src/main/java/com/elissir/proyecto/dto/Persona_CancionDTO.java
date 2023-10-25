package com.elissir.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Este objeto se mappea manualmente en el controlador que lo necesita (Relaciones)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Persona_CancionDTO {

    private Long id_persona_cancion;
    private int like;
    private PersonaDTO personaDTO;
    private CancionDTO cancionDTO;
}
