package com.adamboyd.reactive.authService.mappers;

import com.adamboyd.reactive.authService.restmodels.AuthenticationResponse;
import com.adamboyd.reactive.authService.restmodels.Role;
import com.adamboyd.reactive.quarryService.models.businessobjects.UserDetailsBO;
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