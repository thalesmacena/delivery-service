package br.com.example.deliveryservice.application.controller;

import br.com.example.deliveryservice.domain.external.authenticationservice.LoginPayload;
import br.com.example.deliveryservice.domain.external.authenticationservice.LoginResponse;
import br.com.example.deliveryservice.domain.services.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<LoginResponse> Login(@RequestBody @Valid LoginPayload payload) {

        return ResponseEntity.ok(loginService.login(payload));
    }
}
