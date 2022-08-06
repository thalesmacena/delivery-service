package br.com.example.deliveryservice.infra.exception.external;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class AuthenticationServiceException extends IntegrationException {

    @Serial
    private static final long serialVersionUID = 1L;

    public AuthenticationServiceException(String message) {
        super(message);
    }

    public AuthenticationServiceException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
