package com.adamboyd.reactive.common.mappers;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Component
public class GenericMapper {

    @Named("toZonedDateTime")
    public ZonedDateTime toZonedDateTime(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.of(ZoneOffset.UTC.getId()));
    }

    @Named("toLocalDateTime")
    public LocalDateTime toLocalDateTime(ZonedDateTime zonedDateTime) {
        return zonedDateTime.toLocalDateTime();
    }
}
