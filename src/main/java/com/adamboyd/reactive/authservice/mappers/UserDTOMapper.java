package com.adamboyd.reactive.authservice.mappers;

import com.adamboyd.reactive.authservice.restmodels.UserDTO;
import com.adamboyd.reactive.authservice.restmodels.UserDetailsBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDTOMapper {
    UserDTOMapper INSTANCE = Mappers.getMapper(UserDTOMapper.class);

    UserDTO toUserDtoWithEncodedPassword(UserDetailsBO userDetailsBO);

    @Mapping(target = "password", ignore = true)
    UserDTO toUserDtoWithoutPassword(UserDetailsBO userDetailsBO);
}
