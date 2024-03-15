package com.adamboyd.reactive.authService.mappers;

import com.adamboyd.reactive.authService.restmodels.Role;
import com.adamboyd.reactive.authService.restmodels.UserDTO;
import com.adamboyd.reactive.models.businessobjects.UserDetailsBO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDTOMapperTest {

    @Test
    void toUserDTO() {
        UserDetailsBO userDetailsBO = UserDetailsBO.builder().role(Role.ADMIN).password("password").build();

        UserDTO underTest = UserDTOMapper.INSTANCE.toUserDTO(userDetailsBO);

        assertNotNull(underTest);
        assertEquals("*****", underTest.getPassword());
    }
}