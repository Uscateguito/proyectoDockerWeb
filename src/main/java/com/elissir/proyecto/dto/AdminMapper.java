package com.elissir.proyecto.dto;

import com.elissir.proyecto.entidades.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdminMapper {

    AdminMapper mapper = Mappers.getMapper(AdminMapper.class);

    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "id_admin", target = "id_admin")
    AdminDTO adminToAdminDTO(Admin admin);


}
