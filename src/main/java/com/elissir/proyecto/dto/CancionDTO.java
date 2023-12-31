package com.elissir.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CancionDTO {

    private Long id_cancion;
    private String nombre;
    private String artista;
    private String album;
    private String duracion;
    private int numLikes;
}
