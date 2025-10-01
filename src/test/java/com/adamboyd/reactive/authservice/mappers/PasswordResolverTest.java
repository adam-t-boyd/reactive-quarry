package com.adamboyd.reactive.authservice.mappers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PasswordResolverTest {
    @Mock private BCryptPasswordEncoder bCryptPasswordEncoder;
    @InjectMocks PasswordResolver passwordResolver;

    @Test
    void toEncodePassword() {
        final String rawPassword = "rawPassword";
        final String encodedPassword = "encodedPassword";

        when(bCryptPasswordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        String underTest = passwordResolver.encodePassword(rawPassword);

        assertNotNull(underTest);
        assertEquals(encodedPassword, underTest);
    }
}