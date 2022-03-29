package br.com.example.deliveryservice.infra.security;

import br.com.example.deliveryservice.domain.external.authenticationservice.AuthPayload;
import br.com.example.deliveryservice.domain.external.authenticationservice.AuthResponse;
import br.com.example.deliveryservice.domain.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!isLoginRequest(getPath(request))) {
            String token = getToken(request);

            AuthResponse authResponse = authenticationService.authenticate(new AuthPayload(token));

            Authentication authentication = new UsernamePasswordAuthenticationToken(authResponse.getUser(), null, null);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {

        return request.getHeader("Authorization");
    }

    private String getPath(HttpServletRequest request) {
        return request.getRequestURI();
    }

    private Boolean isLoginRequest(String path) {
        return path.equals(contextPath + "/login");
    }
}
