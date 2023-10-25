package com.elissir.proyectodockerweb.dto;

import com.elissir.proyecto.entidades.Lista;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ListaMapper {

    ListaMapper mapper = Mappers.getMapper(ListaMapper.class);


    @Mapping(source = "genero", target = "genero")
    @Mapping(source = "numLikes", target = "numLikes")
    ListaDTO listaToListaDTO(Lista lista);

}
