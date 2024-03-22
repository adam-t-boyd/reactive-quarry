package com.adamboyd.reactive.repositories;

import com.adamboyd.reactive.quarryService.models.businessobjects.QuarryBO;

import java.time.LocalDateTime;

class QuarryData {

    static QuarryBO getQuarryBO() {
        return QuarryBO.builder()
                .id(1)
                .disused(true)
                .establisheddate(LocalDateTime.now().plusMinutes(0))
                .build();
    }

    static QuarryBO getQuarryBO(int id, boolean disused, LocalDateTime establishedDate) {
        return QuarryBO.builder()
                .id(id)
                .disused(disused)
                .establisheddate(establishedDate)
                .build();
    }
}
