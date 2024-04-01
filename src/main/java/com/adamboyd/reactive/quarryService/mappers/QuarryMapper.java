package com.adamboyd.reactive.quarryService.mappers;

import com.adamboyd.reactive.common.mappers.GenericMapper;
import com.adamboyd.reactive.quarryService.models.businessobjects.QuarryBO;
import com.adamboyd.reactive.quarryService.models.restmodels.rocks.Location;
import com.adamboyd.reactive.quarryService.models.restmodels.rocks.Quarry;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = GenericMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuarryMapper {
    QuarryMapper INSTANCE = Mappers.getMapper(QuarryMapper.class);

    @Mapping(target = "quarryId", source = "id")
    @Mapping(target = "location", source = "placeid", qualifiedByName = "toLocation")
    @Mapping(target = "establishedDate", source = "establisheddate", qualifiedByName = "toZonedDateTime")
    Quarry toQuarry(QuarryBO quarryBO);

    @Mapping(target = "googleAPIPlaceId", source = "placeId")
    @Mapping(target = "latitude", ignore = true)
    @Mapping(target = "longitude",ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "country", ignore = true)
    @Named("toLocation")
    Location toLocation(String placeId);
}
