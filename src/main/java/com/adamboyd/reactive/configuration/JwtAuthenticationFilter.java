package com.adamboyd.reactive.configuration;

/*
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

    private final ReactiveUserDetailsService reactiveUserDetailsService;

    @Override
    public @NotNull Mono<Void> filter(ServerWebExchange exchange, @NotNull WebFilterChain webFilterChain) {
       final String authHeader = exchange.getRequest().getHeaders().get(AUTHORIZATION).getFirst();
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            webFilterChain.filter(exchange);
            return null;
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Mono<UserDetails> userDetails = reactiveUserDetailsService.findByUsername(userEmail);
            // TODO blocking call
            if (jwtService.isTokenValid(jwt, userDetails.block())) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.block().getAuthorities());

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(exchange.getRequest()));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filter(exchange, webFilterChain);
    }
        return null;
    }
}*/
