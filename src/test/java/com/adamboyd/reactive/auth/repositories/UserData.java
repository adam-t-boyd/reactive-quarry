package com.adamboyd.reactive.auth.repositories;

import com.adamboyd.reactive.auth.restmodels.Role;
import com.adamboyd.reactive.models.businessobjects.UserDetailsBO;

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
