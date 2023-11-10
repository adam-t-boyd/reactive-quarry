package com.adamboyd.reactive.authService.restmodels;


import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Data
@Builder
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String password;
//    private String authority;
//    Set<GrantedAuthority> authorities;


    public RegisterRequest(
            String email,
                           String username,
                           String password,
            String firstname,
            String lastname
//                           String authority,
    ) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
//        List.of(new SimpleGrantedAuthority(Role.ADMIN.getName()));
    }
}
