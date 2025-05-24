package com.adamboyd.reactive.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientErrorException.class)
    public Mono<ResponseEntity<ProblemDetail>> handleClientError(ClientErrorException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, e.getMessage());

        problemDetail.setInstance(URI.create("/errors/client"));
        problemDetail.setTitle("Client Error");
        problemDetail.setType(URI.create("https://geology.com/problem/client-error"));

        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problemDetail));
    }

    @ExceptionHandler(NotFoundException.class)
    public Mono<ResponseEntity<ProblemDetail>> handleNotFoundException(NotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, e.getMessage());

        problemDetail.setInstance(URI.create("/errors/not-found"));
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setType(URI.create("https://geology.com/problem/not-found"));

        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problemDetail));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ProblemDetail>> handleGenericException(Exception e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");

        problemDetail.setInstance(URI.create("/errors/internal"));
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setType(URI.create("https://geology.com/problem/internal-error"));

        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problemDetail));
    }
}