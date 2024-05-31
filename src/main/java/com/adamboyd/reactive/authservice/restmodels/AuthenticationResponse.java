package com.adamboyd.reactive.authservice.restmodels;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthenticationResponse {
    private String username;
    private String token;
}
