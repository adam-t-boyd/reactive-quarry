package com.adamboyd.reactive.quarryservice.mappers;

import com.adamboyd.reactive.common.mappers.GenericMapper;
import com.adamboyd.reactive.quarryservice.models.businessobjects.QuarryBO;
import com.adamboyd.reactive.quarryservice.models.restmodels.rocks.Location;
import com.adamboyd.reactive.quarryservice.models.restmodels.rocks.Quarry;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = GenericMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuarryMapper {
    QuarryMapper INSTANCE = Mappers.getMapper(QuarryMapper.class);

    @Mapping(target = "id", source = "quarryId")
    @Mapping(target = "placeid", source = "location", qualifiedByName = "toPlaceid")
    @Mapping(target = "establisheddate", source = "establishedDate", qualifiedByName = "toLocalDateTime")
    QuarryBO toQuarryBO(Quarry quarry);

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

    @Named("toPlaceid")
    default String toPlaceId(Location location){
        return location.getGoogleAPIPlaceId();
    }
}
