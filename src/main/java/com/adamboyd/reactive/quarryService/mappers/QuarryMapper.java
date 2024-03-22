package com.adamboyd.reactive.quarryService.mappers;

import com.adamboyd.reactive.authService.restmodels.UserDTO;
import com.adamboyd.reactive.quarryService.models.businessobjects.QuarryBO;
import com.adamboyd.reactive.quarryService.models.businessobjects.UserDetailsBO;
import com.adamboyd.reactive.quarryService.models.restmodels.rocks.Quarry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuarryMapper {
    QuarryMapper INSTANCE = Mappers.getMapper(QuarryMapper.class);

    @Mapping(target = "quarryId", source = "id")
    @Mapping(target = "establishedDate", source = "establisheddate")
    Quarry toQuarry(QuarryBO quarryBO);
}
