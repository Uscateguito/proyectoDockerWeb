package com.elissir.proyecto.dto;

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
    @Mapping(source = "correoElectronico", target = "correoElectronico")
    PersonaDTO personaToPersonaDTO(Persona persona);

}
