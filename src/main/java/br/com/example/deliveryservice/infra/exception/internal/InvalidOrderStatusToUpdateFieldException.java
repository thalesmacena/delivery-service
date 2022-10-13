package br.com.example.deliveryservice.infra.exception.internal;

import br.com.example.deliveryservice.domain.internal.Order;

public class InvalidOrderStatusToUpdateFieldException extends RuntimeException {

    public InvalidOrderStatusToUpdateFieldException(String param, Order order) {
        super(String.format("Attribute: %s, order status: %s", param, order.getStatus().toString()));
    }
}
