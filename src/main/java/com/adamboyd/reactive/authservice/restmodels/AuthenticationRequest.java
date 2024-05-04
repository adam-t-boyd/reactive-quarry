package com.adamboyd.reactive.authservice.restmodels;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {
    String email;
    String username;
    String password;
}
