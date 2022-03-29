package br.com.example.deliveryservice.domain.services.impl;

import br.com.example.deliveryservice.domain.external.authenticationservice.LoginPayload;
import br.com.example.deliveryservice.domain.external.authenticationservice.LoginResponse;
import br.com.example.deliveryservice.domain.services.LoginService;
import br.com.example.deliveryservice.infra.api.AuthenticationServiceAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final AuthenticationServiceAPI authenticationServiceAPI;

    @Override
    public LoginResponse login(LoginPayload loginPayload) {
        return authenticationServiceAPI.login(loginPayload);
    }
}
