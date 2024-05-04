package com.adamboyd.reactive.authservice.mappers;

import com.adamboyd.reactive.authservice.restmodels.AuthenticationResponse;
import com.adamboyd.reactive.authservice.restmodels.Role;
import com.adamboyd.reactive.authservice.restmodels.UserDetailsBO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuthenticationResponseMapperTest {

    @Test
    void toAuthenticationResponse() {
        UserDetailsBO userDetailsBO = UserDetailsBO.builder().role(Role.ADMIN).username("username").password("password").build();

        AuthenticationResponse underTest = AuthenticationResponseMapper.INSTANCE.toAuthenticationResponse(userDetailsBO);

        assertNotNull(underTest);
        assertEquals("username", underTest.getUsername());
    }
}