package com.adamboyd.reactive.authService.repositories;

import com.adamboyd.reactive.authService.restmodels.Role;
import com.adamboyd.reactive.quarryService.models.businessobjects.UserDetailsBO;

public class UserData {
    public static UserDetailsBO getUserDetailsBO(int id, String email, String username, String password, Role role) {

        return UserDetailsBO.builder().id(id)
                .email(email)
                .username(username)
                .password(password)
                .role(role)
                .firstname("test")
                .lastname("test")
                .build();
    }
}
