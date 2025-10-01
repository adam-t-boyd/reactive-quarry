package com.adamboyd.reactive.authservice.mappers;

import com.adamboyd.reactive.authservice.restmodels.RegisterRequest;
import com.adamboyd.reactive.authservice.restmodels.Role;
import com.adamboyd.reactive.authservice.restmodels.UserDetailsBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = PasswordResolver.class)
public interface UserDetailsMapper {
    UserDetailsMapper INSTANCE = Mappers.getMapper(UserDetailsMapper.class);

    @Mapping(target = "password", source = "registerRequest.password", qualifiedByName = "encodePassword")
    UserDetailsBO toUserDetailsBO(RegisterRequest registerRequest, Role role);
}
