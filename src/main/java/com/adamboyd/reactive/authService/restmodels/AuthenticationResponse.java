package com.adamboyd.reactive.authService.restmodels;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthenticationResponse {
    private String username;
    private String token;
}
