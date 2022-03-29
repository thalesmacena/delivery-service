package br.com.example.deliveryservice.infra.security;

import br.com.example.deliveryservice.domain.external.authenticationservice.AuthPayload;
import br.com.example.deliveryservice.domain.external.authenticationservice.AuthResponse;
import br.com.example.deliveryservice.domain.external.authenticationservice.ResourcePermission;
import br.com.example.deliveryservice.domain.services.AuthenticationService;
import br.com.example.deliveryservice.domain.services.MessageContextService;
import br.com.example.deliveryservice.infra.exception.UnauthorizedException;
import org.apache.camel.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
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
import java.util.List;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private MessageContextService messageContextService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = getPath(request);

        if (!isLoginRequest(path)) {
            String token = getToken(request);

            AuthResponse authResponse = authenticationService.authenticate(new AuthPayload(token));

            if (!isAuthorized(path, authResponse.getResources())) {
                throw new UnauthorizedException(messageContextService.getMessage("delivery.service.unauthorized.error"), path);
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(authResponse.getUser(), null, null);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {

        return request.getHeader("Authorization");
    }

    private String getPath(HttpServletRequest request) {
        return request.getRequestURI().substring(contextPath.length());
    }

    private Boolean isLoginRequest(String path) {
        return path.equals("/login");
    }

    private Boolean isAuthorized(String path, List<ResourcePermission> resourcesPermissions) {
        return resourcesPermissions.stream().anyMatch(resourcePermission -> resourcePermission.getPath().equals(path));
    }
}
