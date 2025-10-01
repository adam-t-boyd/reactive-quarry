package com.adamboyd.reactive.authservice.utils;

import com.adamboyd.reactive.authservice.restmodels.BearerToken;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * convert header values into authentication token
 **/
@Component
public class AuthConverter implements ServerAuthenticationConverter {
    @Override
    public Mono<Authentication> convert(final ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION)
                ).filter(s -> s.startsWith("Bearer "))
                .map(s -> s.substring(7))
                .map(BearerToken::new);
    }
}
