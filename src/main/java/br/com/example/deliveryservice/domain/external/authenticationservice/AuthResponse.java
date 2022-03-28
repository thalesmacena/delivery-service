package br.com.example.deliveryservice.domain.external.authenticationservice;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class AuthResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String user;
    private List<ResourcePermission> resources;
}
