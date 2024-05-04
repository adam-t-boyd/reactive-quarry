package com.adamboyd.reactive.authservice.services;

import com.adamboyd.reactive.authservice.mappers.AuthenticationResponseMapper;
import com.adamboyd.reactive.authservice.mappers.UserDTOMapper;
import com.adamboyd.reactive.authservice.mappers.UserDetailsMapper;
import com.adamboyd.reactive.authservice.repositories.UserDetailsRepository;
import com.adamboyd.reactive.authservice.restmodels.*;
import com.adamboyd.reactive.authservice.utils.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDetailsRepository userDetailsRepository;
    private final AuthValidator authValidator;
    private final static UserDTOMapper USER_DTO_MAPPER = UserDTOMapper.INSTANCE;
    private final static AuthenticationResponseMapper AUTHENTICATION_RESPONSE_MAPPER = AuthenticationResponseMapper.INSTANCE;
    private final static UserDetailsMapper USER_DETAILS_MAPPER = UserDetailsMapper.INSTANCE;

    public Mono<UserDTO> getUserByUsername(String username) {
        return userDetailsRepository.findByUsername(username)
                .map(USER_DTO_MAPPER::toUserDTO);
    }

    @Override
    public Flux<UserDTO> getUser() {
        return userDetailsRepository.findAll()
                .map(USER_DTO_MAPPER::toUserDTO);
    }

    @Override
    public Mono<UserDTO> getUser(BigDecimal userId) {
        return userDetailsRepository.findById(userId)
                .map(USER_DTO_MAPPER::toUserDTO);
    }

    public Mono<UserDTO> getUserByEmail(String email) {
        return userDetailsRepository.findOneByEmail(email)
                .map(USER_DTO_MAPPER::toUserDTO);
    }

    @Override
    public Mono<AuthenticationResponse> createUser(RegisterRequest registerRequest) {
        return Mono.just(registerRequest)
                .filterWhen(authValidator::newUserCredentialsAreValid)
                .flatMap(regReq -> {
                    UserDetailsBO userDetailsBO = UserDetailsMapper.INSTANCE.toUserDetailsBO(
                            regReq, Role.ADMIN);
                    return userDetailsRepository.save(userDetailsBO);
                })
                .map(AUTHENTICATION_RESPONSE_MAPPER::toAuthenticationResponse);
    }

    @Override
    public Mono<UserDTO> updateUser(BigDecimal userId, RegisterRequest updateRequest) {
        return userDetailsRepository.existsById(userId)
                .filterWhen(aBoolean -> Mono.just(Boolean.TRUE))
                .flatMap(regReq -> {
                    UserDetailsBO userDetailsBO = USER_DETAILS_MAPPER.toUserDetailsBO(updateRequest, Role.ADMIN);
                    return userDetailsRepository.save(userDetailsBO);
                })
                .map(USER_DTO_MAPPER::toUserDTO);
    }

    @Override
    public Mono<Void> deleteUser(BigDecimal userId, RegisterRequest deleteRequest) {
        return userDetailsRepository.existsById(userId)
                .filterWhen(aBoolean -> Mono.just(Boolean.TRUE))
                .flatMap(regReq -> {
                    UserDetailsBO userDetailsBO = USER_DETAILS_MAPPER.toUserDetailsBO(deleteRequest, Role.ADMIN);
                    return userDetailsRepository.save(userDetailsBO);
                })
                .flatMap(userDetailsBO -> Mono.empty());
    }


}
