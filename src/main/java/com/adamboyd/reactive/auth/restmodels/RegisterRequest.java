package com.adamboyd.reactive.auth.restmodels;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
@Setter
public class RegisterRequest extends User {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String authority;

    public RegisterRequest(Integer id,
                           String username,
                           String password,
                           String authority,
                           String email) {
        super(username, password,
                List.of(new SimpleGrantedAuthority(authority)));
        this.email = email;
        this.id = id;
    }
}
