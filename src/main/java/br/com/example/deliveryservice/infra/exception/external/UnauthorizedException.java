package br.com.example.deliveryservice.infra.exception.external;

public class UnauthorizedException extends RuntimeException {
    private String resource;

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, String resource) {
        super(message);
        this.resource = resource;
    }
}
