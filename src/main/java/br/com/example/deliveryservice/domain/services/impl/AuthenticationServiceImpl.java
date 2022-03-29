package br.com.example.deliveryservice.domain.services.impl;

import br.com.example.deliveryservice.domain.external.authenticationservice.AuthPayload;
import br.com.example.deliveryservice.domain.external.authenticationservice.AuthResponse;
import br.com.example.deliveryservice.domain.services.AuthenticationService;
import br.com.example.deliveryservice.infra.api.AuthenticationServiceAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationServiceAPI authenticationServiceAPI;

    @Override
    public AuthResponse authenticate(AuthPayload payload) {
        return authenticationServiceAPI.auth(payload);
    }
}
