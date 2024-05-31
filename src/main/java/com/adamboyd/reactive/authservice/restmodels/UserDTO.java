package com.adamboyd.reactive.authservice.restmodels;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Data
@Builder
public class UserDTO {
    private String username;
    private String password;
    private String email;

    private String firstname;
    private String lastname;

    private Set<GrantedAuthority> authorities;
    private Role role;
}
