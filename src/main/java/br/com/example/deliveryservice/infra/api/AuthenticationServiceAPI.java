package br.com.example.deliveryservice.infra.api;

import static br.com.example.deliveryservice.infra.util.HttpUtils.getHttpStatus;

import br.com.example.deliveryservice.infra.api.pool.AuthenticationServicePoolConfig;
import br.com.example.deliveryservice.infra.exception.AuthenticationServiceException;
import br.com.example.deliveryservice.infra.exception.UnauthenticatedException;
import feign.Response;
import org.hibernate.secure.spi.IntegrationException;
import org.springframework.http.HttpStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import feign.codec.ErrorDecoder;

import br.com.example.deliveryservice.domain.external.authenticationservice.AuthPayload;
import br.com.example.deliveryservice.domain.external.authenticationservice.AuthResponse;
import br.com.example.deliveryservice.domain.external.authenticationservice.LoginPayload;
import br.com.example.deliveryservice.domain.external.authenticationservice.LoginResponse;


@FeignClient(value = "authentication-api", url="${application.authentication-service.url}", configuration = {
        AuthenticationServiceAPI.AuthenticationServiceDecoder.class, AuthenticationServicePoolConfig.class
})
public interface AuthenticationServiceAPI {

    @PostMapping("/login")
    LoginResponse login(@RequestBody LoginPayload payload);

    @PostMapping("/auth")
    AuthResponse auth(@RequestBody AuthPayload payload);

    class AuthenticationServiceDecoder implements ErrorDecoder {

        @Override
        public IntegrationException decode(String methodKey, Response response) {
            final HttpStatus statusCode = getHttpStatus(response);

            if (statusCode == HttpStatus.UNAUTHORIZED) {
                throw new UnauthenticatedException();
            }

            throw new AuthenticationServiceException(statusCode.getReasonPhrase());
        }
    }
}
