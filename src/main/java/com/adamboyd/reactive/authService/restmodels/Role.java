package com.adamboyd.reactive.authService.restmodels;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("User"),
    ADMIN("ADMIN");

    private final String name;

}
