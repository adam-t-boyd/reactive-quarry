package com.adamboyd.reactive.authservice.restmodels;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class AuthenticationResponse {
    @NonNull String username;
    @NonNull String token;
    @NonNull String message;
}
