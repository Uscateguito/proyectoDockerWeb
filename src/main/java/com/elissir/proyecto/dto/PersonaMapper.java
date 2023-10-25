package com.elissir.proyectodockerweb.dto;

import com.elissir.proyecto.entidades.Persona;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonaMapper {

    PersonaMapper mapper = Mappers.getMapper(PersonaMapper.class);

    @Mapping(source = "id_persona", target = "id_persona")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "apellido", target = "apellido")
    @Mapping(source = "correo_electronico", target = "correo_electronico")
    PersonaDTO personaToPersonaDTO(Persona persona);

}
