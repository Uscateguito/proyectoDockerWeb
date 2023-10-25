package com.elissir.proyecto.dto;

import com.elissir.proyecto.entidades.Cancion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CancionMapper {

        CancionMapper mapper = Mappers.getMapper(CancionMapper.class);

        @Mapping(source = "id_cancion", target = "id_cancion")
        @Mapping(source = "nombre", target = "nombre")
        @Mapping(source = "artista", target = "artista")
        @Mapping(source = "album", target = "album")
        @Mapping(source = "duracion", target = "duracion")
        @Mapping(source = "numLikes", target = "numLikes")
        CancionDTO cancionToCancionDTO(Cancion cancion);
}
