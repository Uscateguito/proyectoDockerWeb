package com.elissir.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Persona_ListaDTO {

    private Long id_persona_lista;
    private int like;
    private PersonaDTO personaDTO;
    private ListaDTO listaDTO;
}
