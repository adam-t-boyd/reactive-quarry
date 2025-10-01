package com.adamboyd.reactive.authservice.restmodels;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {
    @Schema(description = "User's email address", example = "user@example.com")
    @NotBlank
    String email;

    @Schema(description = "User's username", example = "myusername123")
    @NotBlank
    String username;

    @Schema(description = "User's password (should meet security requirements)", example = "P@ssw0rd")
    @NotBlank
    String password;
}
