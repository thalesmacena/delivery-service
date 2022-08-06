package br.com.example.deliveryservice.infra.exception.internal;

public class ImageNotFoundException extends RuntimeException {

    public ImageNotFoundException(String productKey, String fileName) {
        super("productKey: " + productKey + " fileName: " + fileName);
    }
}
