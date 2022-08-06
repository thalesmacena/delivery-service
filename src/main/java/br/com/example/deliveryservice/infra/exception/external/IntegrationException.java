package br.com.example.deliveryservice.infra.exception.external;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

public class IntegrationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    private HttpStatus httpStatus;

    public IntegrationException(String message) {
        super(message);
    }

    public IntegrationException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
