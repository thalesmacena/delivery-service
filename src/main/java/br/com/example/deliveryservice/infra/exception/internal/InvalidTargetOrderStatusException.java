package br.com.example.deliveryservice.infra.exception.internal;

import br.com.example.deliveryservice.domain.internal.Order;
import br.com.example.deliveryservice.domain.internal.OrderStatus;

public class InvalidTargetOrderStatusException extends RuntimeException {

    public InvalidTargetOrderStatusException(Order order, OrderStatus targetStatus) {
        super(String.format("%s to %s", order.getStatus().toString(), targetStatus.toString()));
    }
}
