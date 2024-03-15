package com.adamboyd.reactive.authService.restmodels;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequest {
    String email;
    String username;
    String password;
}
