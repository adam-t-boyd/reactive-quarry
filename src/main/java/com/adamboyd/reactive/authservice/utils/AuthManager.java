package com.adamboyd.reactive.authservice.utils;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
/*
@RequiredArgsConstructor
*/
public class AuthManager implements ReactiveAuthenticationManager {
    // @NonNull private final JwtService jwtService;
    // @NonNull private final UserServiceImpl userService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
//        return Mono.justOrEmpty(
//                        authentication
//                )
//                .cast(BearerToken.class)
//                .flatMap(bearerToken -> {
//                    final String username = jwtService.extractUsername(bearerToken.getCredentials());
//                    final Mono<UserDetails> foundUser = users.getUserDetailsByUsername(username).defaultIfEmpty(null);
////                    final Mono<UserDetails> foundUser = users.findByUsername(username).defaultIfEmpty(null);
//                    // get rid of default if empty
//                    if (foundUser.equals(Mono.empty())) {
//                        Mono.error(new IllegalArgumentException(String.format(
//                                "Username: %s not found in Auth Manager.", username)));
//                    }
//
//                    return foundUser.flatMap(
//                            userDetails -> {
//                                if (jwtService.isTokenValid(bearerToken.getCredentials(), userDetails)) {
//                                    return Mono.justOrEmpty(new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
//                                            userDetails.getPassword(), userDetails.getAuthorities()));
//                                }
//                                return Mono.error(new IllegalArgumentException("Invalid/expired token (Auth Manager)."));
//                            });
//                });
//    }
        return null;
    }
}
