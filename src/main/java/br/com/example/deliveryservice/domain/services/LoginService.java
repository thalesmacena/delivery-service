package br.com.example.deliveryservice.domain.services;

import br.com.example.deliveryservice.domain.external.authenticationservice.LoginPayload;
import br.com.example.deliveryservice.domain.external.authenticationservice.LoginResponse;

public interface LoginService {

    LoginResponse login(LoginPayload loginPayload);
}
