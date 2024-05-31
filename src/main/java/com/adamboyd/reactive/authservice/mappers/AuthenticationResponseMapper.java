package com.adamboyd.reactive.authservice.mappers;

import com.adamboyd.reactive.authservice.restmodels.AuthenticationResponse;
import com.adamboyd.reactive.authservice.restmodels.UserDetailsBO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthenticationResponseMapper {
    AuthenticationResponseMapper INSTANCE = Mappers.getMapper(AuthenticationResponseMapper.class);

    AuthenticationResponse toAuthenticationResponse(UserDetailsBO userDetailsBO);

}
