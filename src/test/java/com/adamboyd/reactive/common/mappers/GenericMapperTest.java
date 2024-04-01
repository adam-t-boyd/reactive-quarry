package com.adamboyd.reactive.common.mappers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GenericMapperTest {
   @InjectMocks private GenericMapper genericMapper;

    @Test
    void toZonedDateTime() {
        final LocalDateTime localDateTime = LocalDateTime.now();
        final ZonedDateTime result = genericMapper.toZonedDateTime(localDateTime);
        assertEquals(ZonedDateTime.of(localDateTime, ZoneOffset.UTC), result);
    }
}