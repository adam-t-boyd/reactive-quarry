package com.adamboyd.reactive.authservice.restmodels;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

@NoArgsConstructor
@Data
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String password;
    private Set<GrantedAuthority> authorities;

    public RegisterRequest(
            String email,
            String username,
            String password,
            String firstname,
            String lastname
    ) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.authorities = Set.of(new SimpleGrantedAuthority(Role.ADMIN.name()));
    }
}
