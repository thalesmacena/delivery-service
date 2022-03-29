package br.com.example.deliveryservice.domain.services;

import br.com.example.deliveryservice.domain.external.authenticationservice.AuthPayload;
import br.com.example.deliveryservice.domain.external.authenticationservice.AuthResponse;

public interface AuthenticationService {

    AuthResponse authenticate(AuthPayload payload);
}
