package com.adamboyd.reactive.authservice.mappers;

import com.adamboyd.reactive.authservice.restmodels.Role;
import com.adamboyd.reactive.authservice.restmodels.UserDTO;
import com.adamboyd.reactive.authservice.restmodels.UserDetailsBO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserDTOMapperTest {

    @Test
    void toUserDTO() {
        UserDetailsBO userDetailsBO = UserDetailsBO.builder().role(Role.ADMIN).password("password").build();

        UserDTO underTest = UserDTOMapper.INSTANCE.toUserDTO(userDetailsBO);

        assertNotNull(underTest);
        assertEquals("*****", underTest.getPassword());
    }
}