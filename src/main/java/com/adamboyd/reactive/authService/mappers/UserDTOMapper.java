package com.adamboyd.reactive.authService.mappers;

import com.adamboyd.reactive.authService.restmodels.UserDTO;
import com.adamboyd.reactive.quarryService.models.businessobjects.UserDetailsBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDTOMapper {
    UserDTOMapper INSTANCE = Mappers.getMapper(UserDTOMapper.class);

    @Mapping(target = "password", constant = "*****")
    UserDTO toUserDTO(UserDetailsBO userDetailsBO);
}
