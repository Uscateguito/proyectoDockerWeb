package com.elissir.proyectodockerweb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CancionDTO {

    private Long id_cancion;

    private String nombre;

    private String artista;

    private String album;

    private String duracion;

    private int numLikes;
}
