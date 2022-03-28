package br.com.example.deliveryservice.domain.external.authenticationservice;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class LoginResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String token;
    private String expiration;
}
